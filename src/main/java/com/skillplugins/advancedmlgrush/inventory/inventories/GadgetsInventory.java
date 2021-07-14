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

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.annotations.PostConstruct;
import com.skillplugins.advancedmlgrush.config.configs.InventoryNameConfig;
import com.skillplugins.advancedmlgrush.config.configs.SoundConfig;
import com.skillplugins.advancedmlgrush.event.EventListener;
import com.skillplugins.advancedmlgrush.event.EventListenerPriority;
import com.skillplugins.advancedmlgrush.inventory.AbstractInventory;
import com.skillplugins.advancedmlgrush.item.EnumItem;
import com.skillplugins.advancedmlgrush.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

@Singleton
public class GadgetsInventory extends AbstractInventory {


    private final StickInventory stickInventory;
    private final BlocksInventory blocksInventory;

    @Inject
    public GadgetsInventory(final @NotNull StickInventory stickInventory,
                            final @NotNull BlocksInventory blocksInventory) {
        this.stickInventory = stickInventory;
        this.blocksInventory = blocksInventory;
    }

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
        final String title = inventoryUtils.getInventoryName(InventoryNameConfig.GADGETS);
        final Inventory inventory = Bukkit.createInventory(null, 3 * 9, title);
        inventoryUtils.fill(inventory);

        itemManager.setItem(inventory, Optional.empty(), EnumItem.GADGETS_STICK);
        itemManager.setItem(inventory, Optional.empty(), EnumItem.GADGETS_BLOCKS);

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
                    final ItemStack currentItem = event.getCurrentItem();

                    if (itemUtils.compare(currentItem, EnumItem.GADGETS_STICK, Optional.of(player))) {
                        stickInventory.open(player);
                        soundUtil.playSound(player, SoundConfig.INVENTORY_CLICK);
                    } else if (itemUtils.compare(currentItem, EnumItem.GADGETS_BLOCKS, Optional.of(player))) {
                        blocksInventory.open(player);
                        soundUtil.playSound(player, SoundConfig.INVENTORY_CLICK);
                    }
                }
            }
        });
        return eventListeners;
    }

}
