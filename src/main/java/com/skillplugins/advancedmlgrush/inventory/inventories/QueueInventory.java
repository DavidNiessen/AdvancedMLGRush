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
import com.skillplugins.advancedmlgrush.game.queue.Queue2x1;
import com.skillplugins.advancedmlgrush.game.queue.Queue4x1;
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
public class QueueInventory extends AbstractInventory {

    private final Queue2x1 queue2X1;
    private final Queue4x1 queue4X1;

    @Inject
    public QueueInventory(final @NotNull Queue2x1 queue2X1,
                          final @NotNull Queue4x1 queue4X1) {
        this.queue2X1 = queue2X1;
        this.queue4X1 = queue4X1;
    }

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
        final String title = inventoryUtils.getInventoryName(InventoryNameConfig.QUEUE);
        final Inventory inventory = Bukkit.createInventory(null, 3 * 9, title);

        inventoryUtils.fill(inventory);
        return new Pair<>(inventory, title);
    }

    @Override
    protected Inventory onOpen(@NotNull Inventory inventory, @NotNull Player player) {
        final Optional<Player> optionalPlayer = Optional.of(player);

        itemManager.setItem(inventory, optionalPlayer, EnumItem.QUEUE_2X1);
        itemManager.setItem(inventory, optionalPlayer, EnumItem.QUEUE_4X1);
        return inventory;
    }

    @Override
    protected List<EventListener<?>> listeners(@NotNull List<EventListener<?>> eventListeners) {
        final Class<? extends AbstractInventory> clazz = this.getClass();
        eventListeners.add(new EventListener<InventoryClickEvent>(InventoryClickEvent.class, EventListenerPriority.MEDIUM) {
            @Override
            protected void onEvent(final @NotNull InventoryClickEvent event) {
                final Player player = (Player) event.getWhoClicked();
                if (inventoryUtils.isOpenInventory(player, clazz)) {
                    final Optional<Player> optionalPlayer = Optional.of(player);
                    final ItemStack currentItem = event.getCurrentItem();

                    if (itemUtils.compare(currentItem, EnumItem.QUEUE_2X1, optionalPlayer)) {
                        soundUtil.playSound(player, SoundConfig.INVENTORY_CLICK);
                        player.closeInventory();
                        queue2X1.register(player);
                    } else if (itemUtils.compare(currentItem, EnumItem.QUEUE_4X1, optionalPlayer)) {
                        soundUtil.playSound(player, SoundConfig.INVENTORY_CLICK);
                        player.closeInventory();
                        queue4X1.register(player);
                    }
                }
            }
        });
        return eventListeners;
    }
}
