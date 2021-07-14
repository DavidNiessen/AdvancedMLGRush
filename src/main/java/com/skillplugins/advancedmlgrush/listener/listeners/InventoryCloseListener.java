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
import com.skillplugins.advancedmlgrush.inventory.InventoryManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.jetbrains.annotations.NotNull;

@Singleton
public class InventoryCloseListener implements Listener {

    private final EventManager eventManager;
    private final InventoryManager inventoryManager;

    @Inject
    public InventoryCloseListener(final @NotNull EventManager eventManager,
                                  final @NotNull InventoryManager inventoryManager) {
        this.eventManager = eventManager;
        this.inventoryManager = inventoryManager;
    }

    @EventHandler
    public void onClose(final @NotNull InventoryCloseEvent event) {
        eventManager.callEvent(event);

        inventoryManager.unregister((Player) event.getPlayer());
    }

}
