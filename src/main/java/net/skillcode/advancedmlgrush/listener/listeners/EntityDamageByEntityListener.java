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

package net.skillcode.advancedmlgrush.listener.listeners;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.event.EventManager;
import net.skillcode.advancedmlgrush.game.buildmode.BuildModeManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.NotNull;

@Singleton
public class EntityDamageByEntityListener implements Listener {

    private final BuildModeManager buildModeManager;
    private final EventManager eventManager;

    @Inject
    public EntityDamageByEntityListener(final @NotNull BuildModeManager buildModeManager,
                                        final @NotNull EventManager eventManager) {
        this.buildModeManager = buildModeManager;
        this.eventManager = eventManager;
    }

    @EventHandler
    public void onDamage(final @NotNull EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            final Player player = (Player) event.getDamager();
            event.setCancelled(!buildModeManager.isInBuildMode(player));
        }

        eventManager.callEvent(event);
    }

}
