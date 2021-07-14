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
import com.skillplugins.advancedmlgrush.game.map.MapInstanceManager;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class EntitySpawnListener implements Listener {

    private final MapInstanceManager mapInstanceManager;

    @Inject
    public EntitySpawnListener(final @NotNull MapInstanceManager mapInstanceManager) {
        this.mapInstanceManager = mapInstanceManager;
    }

    @EventHandler
    public void onSpawn(final @NotNull EntitySpawnEvent event) {
        final List<World> worlds = new ArrayList<>();
        mapInstanceManager.getMapInstances().forEach(mapInstance -> worlds.add(mapInstance.getWorld()));

        if (worlds.contains(event.getEntity().getWorld())) {
            event.setCancelled(true);
        }
    }
}
