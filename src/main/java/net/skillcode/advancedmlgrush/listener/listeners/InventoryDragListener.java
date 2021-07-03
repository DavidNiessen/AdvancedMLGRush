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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.jetbrains.annotations.NotNull;

@Singleton
public class InventoryDragListener implements Listener {

    private final EventManager eventManager;

    @Inject
    public InventoryDragListener(final @NotNull EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @EventHandler
    public void onClick(final @NotNull InventoryDragEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            eventManager.callEvent(event);
        }

    }

}
