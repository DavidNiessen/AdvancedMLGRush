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
import com.skillplugins.advancedmlgrush.game.spawn.SpawnFile;
import com.skillplugins.advancedmlgrush.game.spawn.SpawnFileLoader;
import com.skillplugins.advancedmlgrush.util.LocationConverter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Singleton
public class PlayerMoveListener implements Listener {

    @Inject
    private EventManager eventManager;
    @Inject
    private SpawnFileLoader spawnFileLoader;
    @Inject
    private LocationConverter locationConverter;

    @EventHandler
    public void onMove(final @NotNull PlayerMoveEvent event) {
        eventManager.callEvent(event);
        if (event.getTo().getY() <= 0) {
            final Player player = event.getPlayer();
            final Optional<SpawnFile> spawnFileOptional = spawnFileLoader.getSpawnFileOptional();
            if (spawnFileOptional.isPresent()) {
                final Location location = locationConverter.toLocation(spawnFileOptional.get().getJsonLocation());
                if (player.getWorld().equals(location.getWorld())) {
                    player.teleport(location);
                }
            }

        }
    }
}
