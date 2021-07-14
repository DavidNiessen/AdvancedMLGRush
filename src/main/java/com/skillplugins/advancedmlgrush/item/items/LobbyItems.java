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

package com.skillplugins.advancedmlgrush.item.items;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.item.EnumItem;
import com.skillplugins.advancedmlgrush.item.ItemManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Singleton
public class LobbyItems {

    private final ItemManager itemManager;

    @Inject
    public LobbyItems(final @NotNull ItemManager itemManager) {
        this.itemManager = itemManager;
    }

    public void setLobbyItems(final @NotNull Player player) {
        final Optional<Player> optionalPlayer = Optional.of(player);
        final Inventory inventory = player.getInventory();

        itemManager.setItem(inventory, optionalPlayer, EnumItem.CHALLENGER);
        itemManager.setItem(inventory, optionalPlayer, EnumItem.SETTINGS);
        itemManager.setItem(inventory, optionalPlayer, EnumItem.SPECTATE);
        itemManager.setItem(inventory, optionalPlayer, EnumItem.GADGETS);
        itemManager.setItem(inventory, optionalPlayer, EnumItem.STATS);
    }

    public void setQueueItems(final @NotNull Player player) {
        itemManager.setItem(player.getInventory(), Optional.of(player), EnumItem.QUEUE_LEAVE);
    }
}
