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

package com.skillplugins.advancedmlgrush.listener.listeners;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.event.EventManager;
import com.skillplugins.advancedmlgrush.item.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Singleton
public class PlayerInteractListener implements Listener {


    @Inject
    private EventManager eventManager;
    @Inject
    private JavaPlugin javaPlugin;
    @Inject
    private ItemUtils itemUtils;

    private final List<String> cooldowns = new CopyOnWriteArrayList<>();

    @Inject
    public PlayerInteractListener(final @NotNull EventManager eventManager,
                                  final @NotNull JavaPlugin javaPlugin) {
        this.eventManager = eventManager;
        this.javaPlugin = javaPlugin;
    }

    @EventHandler
    public void onInteract(final @NotNull PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final ItemStack itemInHand = player.getItemInHand();
        if (itemUtils.isValidItem(itemInHand)) {
            player.getInventory().setItem(player.getInventory().getHeldItemSlot(), itemInHand.clone());
        }
        if (!cooldowns.contains(player.getName())) {
            eventManager.callEvent(event);
            cooldowns.add(player.getName());
            Bukkit.getScheduler().scheduleSyncDelayedTask(javaPlugin, () -> cooldowns.remove(player.getName()), 1);
        }
    }

}
