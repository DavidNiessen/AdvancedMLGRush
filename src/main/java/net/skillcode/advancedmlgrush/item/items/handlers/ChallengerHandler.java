/*
 * Copyright (c) 2021 SkillCode
 *
 * This file is a part of the source code of the
 * AdvancedMLGRush plugin by SkillCode.
 *
 * This file may only be used in compliance with the
 * LICENSE.txt (https://github.com/SkillC0de/AdvancedMLGRush/blob/master/LICENSE.txt).
 *
 * Support: https://discord.skillplugins.com
 */

package net.skillcode.advancedmlgrush.item.items.handlers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.config.configs.MessageConfig;
import net.skillcode.advancedmlgrush.event.EventHandler;
import net.skillcode.advancedmlgrush.event.EventListener;
import net.skillcode.advancedmlgrush.game.challenge.ChallengeManager;
import net.skillcode.advancedmlgrush.inventory.inventories.QueueInventory;
import net.skillcode.advancedmlgrush.item.EnumItem;
import net.skillcode.advancedmlgrush.item.ItemUtils;
import net.skillcode.advancedmlgrush.sql.data.SQLDataCache;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

@Singleton
public class ChallengerHandler implements EventHandler {

    @Inject
    private ItemUtils itemUtils;
    @Inject
    private QueueInventory queueInventory;
    @Inject
    private MessageConfig messageConfig;
    @Inject
    private SQLDataCache sqlDataCache;
    @Inject
    private ChallengeManager challengeManager;

    @Override
    public void registerListeners(final @NotNull List<EventListener<?>> eventListeners) {
        eventListeners.add(onRightClick());
        eventListeners.add(onLeftClick());
    }

    private EventListener<EntityDamageByEntityEvent> onLeftClick() {
        return new EventListener<EntityDamageByEntityEvent>(EntityDamageByEntityEvent.class) {
            @Override
            protected void onEvent(final @NotNull EntityDamageByEntityEvent event) {
                if (event.getDamager() instanceof Player
                        && event.getEntity() instanceof Player) {
                    final Player attacker = (Player) event.getDamager();
                    final Player target = (Player) event.getEntity();

                    if (itemUtils.compare(attacker.getItemInHand(), EnumItem.CHALLENGER, Optional.of(attacker))) {
                        if (!sqlDataCache.isLoaded(attacker)
                                || !sqlDataCache.isLoaded(target)) {
                            attacker.sendMessage(messageConfig.getWithPrefix(Optional.of(attacker), MessageConfig.LOADING_DATA));
                        } else {
                            challengeManager.challengePlayer(attacker, target);
                        }
                    }
                }
            }
        };
    }

    private EventListener<PlayerInteractEvent> onRightClick() {
        return new EventListener<PlayerInteractEvent>(PlayerInteractEvent.class) {
            @Override
            protected void onEvent(final @NotNull PlayerInteractEvent event) {
                if (event.getAction() == Action.RIGHT_CLICK_AIR
                        || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

                    final Player player = event.getPlayer();
                    final ItemStack clickedItem = player.getItemInHand();

                    if (itemUtils.compare(clickedItem, EnumItem.CHALLENGER, Optional.of(player))) {
                        if (!sqlDataCache.isLoaded(player)) {
                            player.sendMessage(messageConfig.getWithPrefix(Optional.of(player), MessageConfig.LOADING_DATA));
                        } else {
                            queueInventory.open(player);
                        }
                    }
                }
            }
        };
    }
}
