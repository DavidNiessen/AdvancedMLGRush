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

package com.skillplugins.advancedmlgrush.game.map.schematic;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.annotations.PostConstruct;
import com.skillplugins.advancedmlgrush.config.configs.MainConfig;
import com.skillplugins.advancedmlgrush.config.configs.MessageConfig;
import com.skillplugins.advancedmlgrush.lib.xseries.ActionBar;
import com.skillplugins.advancedmlgrush.lib.xseries.XMaterial;
import com.skillplugins.advancedmlgrush.util.EnumUtils;
import com.skillplugins.advancedmlgrush.util.LocationConverter;
import com.skillplugins.advancedmlgrush.util.NMSUtils;
import com.skillplugins.advancedmlgrush.util.Pair;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.material.Bed;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Singleton
public class SchematicLoader {

    @Inject
    private NMSUtils nmsUtils;
    @Inject
    private EnumUtils enumUtils;
    @Inject
    private JavaPlugin javaPlugin;
    @Inject
    private MainConfig mainConfig;
    @Inject
    private MessageConfig messageConfig;
    @Inject
    private LocationConverter locationConverter;

    private final Set<Material> bedSet = new HashSet<>();

    @PostConstruct
    public void init() {
        bedSet.add(XMaterial.WHITE_BED.parseMaterial());

        final Material orangeBed = XMaterial.ORANGE_BED.parseMaterial();

        if (!bedSet.contains(orangeBed)) {
            bedSet.addAll(Arrays.asList(
                    orangeBed,
                    XMaterial.MAGENTA_BED.parseMaterial(),
                    XMaterial.LIGHT_BLUE_BED.parseMaterial(),
                    XMaterial.YELLOW_BED.parseMaterial(),
                    XMaterial.LIME_BED.parseMaterial(),
                    XMaterial.PINK_BED.parseMaterial(),
                    XMaterial.GRAY_BED.parseMaterial(),
                    XMaterial.LIGHT_GRAY_BED.parseMaterial(),
                    XMaterial.CYAN_BED.parseMaterial(),
                    XMaterial.PURPLE_BED.parseMaterial(),
                    XMaterial.BLUE_BED.parseMaterial(),
                    XMaterial.BROWN_BED.parseMaterial(),
                    XMaterial.GREEN_BED.parseMaterial(),
                    XMaterial.RED_BED.parseMaterial(),
                    XMaterial.BLACK_BED.parseMaterial()
            ));
        }
    }

    public void load(final @NotNull List<StorableBlock> blockList, final @NotNull Iterable<Player> players,
                     final @NotNull World world,
                     final @NotNull List<Pair<Location, Location>> bedLocationList,
                     final @NotNull Runnable onFinish) {

        final int blocksPerTick = mainConfig.getInt(MainConfig.PASTE_BLOCKS_PER_TICK);
        final Queue<StorableBlock> queue = new LinkedList<>(blockList);
        final AtomicReference<String> lastProgress = new AtomicReference<>("");

        new BukkitRunnable() {
            @Override
            public void run() {

                for (int i = 0; i < Math.min(blocksPerTick * 10, queue.size()); i++) {

                    final StorableBlock storableBlock = queue.poll();

                    final Block block = world.getBlockAt(
                            (int) storableBlock.getX(),
                            (int) storableBlock.getY(),
                            (int) storableBlock.getZ());

                    final String materialName = storableBlock.getMaterial();
                    final Material material = enumUtils.isInEnum(Material.class, materialName) ?
                            Material.valueOf(materialName) : XMaterial.STONE.parseMaterial();

                    if (material != null) {

                        if (!bedSet.contains(material)) {

                            block.setType(material);

                            if (!XMaterial.isNewVersion()) {
                                nmsUtils.setBlockData(block, storableBlock.getData());
                            }
                        }

                    }
                }

                final String progress = String.valueOf(100 - (queue.size() * 100) / blockList.size());
                if (!progress.equals(lastProgress.get())) {
                    players.forEach(player -> ActionBar.sendActionBar(player,
                            messageConfig.getString(Optional.of(player), MessageConfig.LOADING_PROGRESS_ACTION_BAR).replace("%progress%", progress)));
                    lastProgress.set(progress);
                }

                if (queue.isEmpty()) {
                    loadBeds(bedLocationList);
                    onFinish.run();

                    cancel();
                }
            }
        }.runTaskTimer(javaPlugin, 1, 10);
    }

    private void loadBeds(final @NotNull List<Pair<Location, Location>> bedLocationList) {
        final Material material = XMaterial.isNewVersion()
                ? XMaterial.RED_BED.parseMaterial() : Material.valueOf("BED_BLOCK");

        if (material != null) {
            bedLocationList.forEach(pair -> {
                final Location bottomLocation = pair.getKey();
                final Location upperLocation = pair.getValue();

                final Block bottomBlock = bottomLocation.getBlock();
                final Block upperBlock = upperLocation.getBlock();

                final BlockFace blockFace = bottomBlock.getFace(upperBlock);

                Arrays.asList(bottomBlock, upperBlock).forEach(block -> {
                    final BlockState blockState = block.getState();
                    blockState.setType(material);

                    final Bed bed = new Bed(material);
                    bed.setHeadOfBed(block.equals(upperBlock));
                    bed.setFacingDirection(blockFace);

                    blockState.setData(bed);
                    blockState.update(true);
                });
            });
        }
    }

}
