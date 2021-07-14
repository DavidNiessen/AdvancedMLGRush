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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.jetbrains.annotations.NotNull;

@Singleton
public class PlayerDropItemListener implements Listener {

    private final EventManager eventManager;
    private final BuildModeManager buildModeManager;

    @Inject
    public PlayerDropItemListener(final @NotNull EventManager eventManager,
                                  final @NotNull BuildModeManager buildModeManager) {
        this.eventManager = eventManager;
        this.buildModeManager = buildModeManager;
    }

    @EventHandler
    public void onClick(final @NotNull PlayerDropItemEvent event) {
        final Player player = event.getPlayer();
        event.setCancelled(!buildModeManager.isInBuildMode(player));

        eventManager.callEvent(event);
    }

}
