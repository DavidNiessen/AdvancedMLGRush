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

package com.skillplugins.advancedmlgrush.inventory.inventories;

import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.annotations.PostConstruct;
import com.skillplugins.advancedmlgrush.config.configs.InventoryNameConfig;
import com.skillplugins.advancedmlgrush.event.EventListener;
import com.skillplugins.advancedmlgrush.inventory.AbstractInventory;
import com.skillplugins.advancedmlgrush.item.EnumItem;
import com.skillplugins.advancedmlgrush.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

@Singleton
public class StatsInventory extends AbstractInventory {

    @PostConstruct
    public void initInventory() {
        super.init();
    }

    @Override
    protected boolean cloneOnOpen() {
        return true;
    }

    @Override
    protected boolean playSound() {
        return true;
    }

    @Override
    protected Pair<Inventory, String> onCreate() {
        final String title = inventoryUtils.getInventoryName(InventoryNameConfig.STATS);
        final Inventory inventory = Bukkit.createInventory(null, 4 * 9, title);

        inventoryUtils.fill(inventory);
        return new Pair<>(inventory, title);
    }

    @Override
    protected Inventory onOpen(final @NotNull Inventory inventory, final @NotNull Player player) {
        final Optional<Player> optionalPlayer = Optional.of(player);

        itemManager.setItem(inventory, optionalPlayer, EnumItem.STATS_WINS);
        itemManager.setItem(inventory, optionalPlayer, EnumItem.STATS_BEDS);
        itemManager.setItem(inventory, optionalPlayer, EnumItem.STATS_RANKING);
        itemManager.setItem(inventory, optionalPlayer, EnumItem.STATS_WIN_RATE);
        itemManager.setItem(inventory, optionalPlayer, EnumItem.STATS_LOSES);
        itemManager.setItem(inventory, optionalPlayer, EnumItem.STATS_KILLS);
        itemManager.setItem(inventory, optionalPlayer, EnumItem.STATS_DEATHS);
        return inventory;
    }

    @Override
    protected List<EventListener<?>> listeners(final @NotNull List<EventListener<?>> eventListeners) {
        return eventListeners;
    }
}
