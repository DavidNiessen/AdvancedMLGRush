/*
 * Copyright (c) 2021 SkillCode
 *
 * This file is a part of the source code of the
 * AdvancedMLGRush plugin by SkillCode.
 *
 * This class may only be used in compliance with the
 * LICENSE.txt (https://github.com/SkillC0de/AdvancedMLGRush/blob/master/LICENSE.txt).
 *
 * Support: https://discord.skillplugins.com
 */

package net.skillcode.advancedmlgrush.listener.listeners;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.event.EventManager;
import net.skillcode.advancedmlgrush.game.buildmode.BuildModeManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@Singleton
public class BlockBreakListener implements Listener {

    private final EventManager eventManager;
    private final BuildModeManager buildModeManager;

    @Inject
    public BlockBreakListener(final @NotNull EventManager eventManager,
                              final @NotNull BuildModeManager buildModeManager) {
        this.eventManager = eventManager;
        this.buildModeManager = buildModeManager;
    }

    @EventHandler
    public void onBreak(final @NotNull BlockBreakEvent event) throws IOException {
        final Player player = event.getPlayer();
        event.setCancelled(!buildModeManager.isInBuildMode(player));

        eventManager.callEvent(event);
    }

}