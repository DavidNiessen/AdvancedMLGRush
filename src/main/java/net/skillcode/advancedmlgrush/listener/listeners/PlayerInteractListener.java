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
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

@Singleton
public class PlayerInteractListener implements Listener {


    private final EventManager eventManager;

    @Inject
    public PlayerInteractListener(final @NotNull EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @EventHandler
    public void onInteract(final @NotNull PlayerInteractEvent event) {
        eventManager.callEvent(event);
        event.getPlayer().updateInventory();
    }

}
