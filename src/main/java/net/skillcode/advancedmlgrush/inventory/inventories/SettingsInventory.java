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

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.annotations.PostConstruct;
import net.skillcode.advancedmlgrush.config.configs.InventoryNameConfig;
import net.skillcode.advancedmlgrush.config.configs.SoundConfig;
import net.skillcode.advancedmlgrush.event.EventListener;
import net.skillcode.advancedmlgrush.event.EventListenerPriority;
import net.skillcode.advancedmlgrush.inventory.AbstractInventory;
import net.skillcode.advancedmlgrush.item.EnumItem;
import net.skillcode.advancedmlgrush.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

@Singleton
public class SettingsInventory extends AbstractInventory {

    @Inject
    private SortingInventory sortingInventory;
    @Inject
    private RoundsInventory roundsInventory;
    @Inject
    private MapInventory mapInventory;

    @PostConstruct
    public void initInventory() {
        super.init();
    }

    @Override
    protected boolean cloneOnOpen() {
        return false;
    }

    @Override
    protected boolean playSound() {
        return true;
    }

    @Override
    protected Pair<Inventory, String> onCreate() {
        final String title = inventoryUtils.getInventoryName(InventoryNameConfig.SETTINGS);
        final Inventory inventory = Bukkit.createInventory(null, 3 * 9, title);
        inventoryUtils.fill(inventory);

        inventory.setItem(11, itemManager.getItem(Optional.empty(), EnumItem.SETTINGS_INVENTORY_SORTING));
        inventory.setItem(13, itemManager.getItem(Optional.empty(), EnumItem.SETTINGS_MAP));
        inventory.setItem(15, itemManager.getItem(Optional.empty(), EnumItem.SETTINGS_ROUNDS));

        return new Pair<>(inventory, title);
    }

    @Override
    protected Inventory onOpen(final @NotNull Inventory inventory, final @NotNull Player player) {
        return inventory;
    }

    @Override
    protected List<EventListener<?>> listeners(final @NotNull List<EventListener<?>> eventListeners) {
        final Class<? extends AbstractInventory> clazz = this.getClass();
        eventListeners.add(new EventListener<InventoryClickEvent>(InventoryClickEvent.class, EventListenerPriority.MEDIUM) {
            @Override
            protected void onEvent(final @NotNull InventoryClickEvent event) {
                final Player player = (Player) event.getWhoClicked();
                if (inventoryUtils.isOpenInventory(player, clazz)) {
                    final Optional<Player> optionalPlayer = Optional.of(player);
                    final ItemStack currentItem = event.getCurrentItem();
                    if (itemUtils.compare(currentItem, EnumItem.SETTINGS_INVENTORY_SORTING, optionalPlayer)) {
                        sortingInventory.open(player);
                        soundUtil.playSound(player, SoundConfig.INVENTORY_CLICK);
                    } else if (itemUtils.compare(currentItem, EnumItem.SETTINGS_MAP, optionalPlayer)) {
                        mapInventory.open(player);
                        soundUtil.playSound(player, SoundConfig.INVENTORY_CLICK);
                    } else if (itemUtils.compare(currentItem, EnumItem.SETTINGS_ROUNDS, optionalPlayer)) {
                        roundsInventory.open(player);
                        soundUtil.playSound(player, SoundConfig.INVENTORY_CLICK);
                    }
                }
            }
        });
        return eventListeners;
    }
}
