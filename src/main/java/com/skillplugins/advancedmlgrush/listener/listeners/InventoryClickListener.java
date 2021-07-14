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
import com.skillplugins.advancedmlgrush.game.buildmode.BuildModeManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Singleton
public class InventoryClickListener implements Listener {


    @Inject
    private EventManager eventManager;
    @Inject
    private BuildModeManager buildModeManager;
    @Inject
    private JavaPlugin javaPlugin;


    private final List<String> cooldowns = new CopyOnWriteArrayList<>();

    @EventHandler
    public void onClick(final @NotNull InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            final Player player = (Player) event.getWhoClicked();
            event.setCancelled(!buildModeManager.isInBuildMode(player));

            if (!cooldowns.contains(player.getName())) {
                eventManager.callEvent(event);
                cooldowns.add(player.getName());
                Bukkit.getScheduler().scheduleSyncDelayedTask(javaPlugin, () -> cooldowns.remove(player.getName()), 1);
            }
        }

    }

}
