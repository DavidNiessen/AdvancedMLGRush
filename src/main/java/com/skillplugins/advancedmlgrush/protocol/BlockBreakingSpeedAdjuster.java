/*
 * Copyright (c) 2021 SkillCode
 *
 * This file is a part of the source code of the
 * AdvancedMLGRush plugin by SkillCode.
 *
 * This file may only be used in compliance with the
 * LICENSE.txt (https://github.com/SkillC0de/AdvancedMLGRush/blob/master/LICENSE.txt).
 *
 * Support: http://discord.skillplugins.com
 */

package com.skillplugins.advancedmlgrush.protocol;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.skillplugins.advancedmlgrush.annotations.PostConstruct;
import com.skillplugins.advancedmlgrush.config.configs.MainConfig;
import com.skillplugins.advancedmlgrush.lib.xseries.XMaterial;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class BlockBreakingSpeedAdjuster extends PacketAdapter {

    private final Map<Player, Integer> players = new ConcurrentHashMap<>();
    private final Random random = new Random();

    @Inject
    private PacketUtils packetUtils;
    @Inject
    private MainConfig mainConfig;

    @Inject
    public BlockBreakingSpeedAdjuster(final @NotNull JavaPlugin plugin) {
        super(plugin, PacketType.Play.Client.BLOCK_DIG);
    }

    @PostConstruct
    public void init() {
        ProtocolLibrary.getProtocolManager().addPacketListener(this);
    }

    private void stopDigging(final @NotNull BlockPosition blockPosition, final @NotNull Player player) {
        if (!players.containsKey(player)) {
            return;
        }

        Bukkit.getScheduler().cancelTask(players.remove(player));
        Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, () -> packetUtils.broadcastBlockBreakAnimationPacket(blockPosition, -1), 1L);
    }

    private void breakBlock(final @NotNull Block block,
                            final @NotNull Player player) {
        final BlockBreakEvent blockBreakEvent = new BlockBreakEvent(block, player);
        Bukkit.getPluginManager().callEvent(blockBreakEvent);
        if (blockBreakEvent.isCancelled()) {
            return;
        }

        final Material blockType = block.getType();
        if (!block.getDrops().isEmpty()) {
            block.breakNaturally(player.getInventory().getItemInHand());
        } else {
            block.setType(Material.AIR);
        }

        int damage = 1;
        final Map<Enchantment, Integer> enchants = player.getItemInHand().getEnchantments();
        if (enchants.containsKey(Enchantment.DURABILITY)) {
            final int level = enchants.get(Enchantment.DURABILITY);
            final int unbreaking_reduction = 100 / (level + 1);
            final int chance = random.nextInt(101);
            if (chance > unbreaking_reduction) {
                damage = 0;
            }
        }

        player.getItemInHand().setDurability((short) (player.getItemInHand().getDurability() + damage));
        if (player.getItemInHand().getDurability() >= player.getItemInHand().getType().getMaxDurability()) {
            player.setItemInHand(new ItemStack(XMaterial.AIR.parseMaterial()));
        }

        player.updateInventory();
        player.playEffect(block.getLocation(), Effect.STEP_SOUND, (Object) blockType);
        block.setType(Material.AIR);
    }

    public boolean isTool(final @Nullable ItemStack itemStack) {
        return itemStack != null && itemStack.getType().name().endsWith("PICKAXE");
    }

    @Override
    public void onPacketReceiving(final @NotNull PacketEvent packetEvent) {
        final Player player = packetEvent.getPlayer();
        if (player == null || !player.isOnline()) {
            return;
        }

        if (player.getGameMode().equals(GameMode.CREATIVE)) {
            return;
        }

        final BlockPosition position = packetEvent.getPacket().getBlockPositionModifier().read(0);
        final EnumWrappers.PlayerDigType type = packetEvent.getPacket().getPlayerDigTypes().read(0);

        switch (type) {
            case ABORT_DESTROY_BLOCK:
            case STOP_DESTROY_BLOCK: {
                stopDigging(position, player);
                break;
            }
            case START_DESTROY_BLOCK: {
                final Location location = position.toLocation(player.getWorld());
                if (!location.getWorld().isChunkLoaded(location.getBlockX() >> 4, location.getBlockZ() >> 4)) {
                    return;
                }
                players.put(player, Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                    int ticks = 0;

                    @Override
                    public void run() {
                        final ItemStack inHand = player.getItemInHand();
                        if (!player.isOnline() || !isTool(inHand)) {
                            stopDigging(position, player);
                            return;
                        }
                        this.ticks += 5;
                        final Block block = location.getBlock();
                        final long ticksPerStage = Math.round(16 / Math.pow(1.3, inHand.getEnchantmentLevel(Enchantment.DIG_SPEED)) / 9.0);
                        if (player.hasPotionEffect(PotionEffectType.FAST_DIGGING)) {
                            for (PotionEffect haste : player.getActivePotionEffects()) {
                                if (haste.getType() == PotionEffectType.FAST_DIGGING) {
                                    this.ticks += haste.getAmplifier() + 1;
                                }
                            }
                        }
                        final int stage;
                        if (ticksPerStage != 0L && (stage = (int) (this.ticks / ticksPerStage)) <= 9) {
                            packetUtils.broadcastBlockBreakAnimationPacket(position, stage);
                        } else {
                            stopDigging(position, player);
                            breakBlock(block, player);
                        }
                    }
                }, 0L, 5L));
                break;
            }
        }
    }
}

