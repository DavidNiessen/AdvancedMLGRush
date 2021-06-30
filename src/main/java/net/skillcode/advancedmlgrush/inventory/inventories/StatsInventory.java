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

package net.skillcode.advancedmlgrush.inventory.inventories;

import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.annotations.PostConstruct;
import net.skillcode.advancedmlgrush.config.configs.InventoryNameConfig;
import net.skillcode.advancedmlgrush.event.EventListener;
import net.skillcode.advancedmlgrush.inventory.AbstractInventory;
import net.skillcode.advancedmlgrush.item.EnumItem;
import net.skillcode.advancedmlgrush.util.Pair;
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
        final Inventory inventory = Bukkit.createInventory(null, 3 * 9, title);

        inventoryUtils.fill(inventory);
        return new Pair<>(inventory, title);
    }

    @Override
    protected Inventory onOpen(final @NotNull Inventory inventory, final @NotNull Player player) {
        final Optional<Player> optionalPlayer = Optional.of(player);

        inventory.setItem(10, itemManager.getItem(optionalPlayer, EnumItem.STATS_WINS));
        inventory.setItem(12, itemManager.getItem(optionalPlayer, EnumItem.STATS_BEDS));
        inventory.setItem(13, itemManager.getItem(optionalPlayer, EnumItem.STATS_RANKING));
        inventory.setItem(14, itemManager.getItem(optionalPlayer, EnumItem.STATS_WIN_RATE));
        inventory.setItem(16, itemManager.getItem(optionalPlayer, EnumItem.STATS_LOSES));
        return inventory;
    }

    @Override
    protected List<EventListener<?>> listeners(final @NotNull List<EventListener<?>> eventListeners) {
        return eventListeners;
    }
}
