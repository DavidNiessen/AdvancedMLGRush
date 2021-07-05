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
import net.skillcode.advancedmlgrush.game.queue.Queue2x1;
import net.skillcode.advancedmlgrush.game.queue.Queue4x1;
import net.skillcode.advancedmlgrush.inventory.AbstractInventory;
import net.skillcode.advancedmlgrush.item.EnumItem;
import net.skillcode.advancedmlgrush.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

@Singleton
public class QueueInventory extends AbstractInventory {

    private static final int QUEUE_2X1_SLOT = 11;
    private static final int QUEUE_4X1_SLOT = 15;

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

        inventory.setItem(QUEUE_2X1_SLOT, itemManager.getItem(optionalPlayer, EnumItem.QUEUE_2X1));
        inventory.setItem(QUEUE_4X1_SLOT, itemManager.getItem(optionalPlayer, EnumItem.QUEUE_4x1));
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

                    final int slot = event.getSlot();
                    if (slot == QUEUE_2X1_SLOT) {
                        soundUtil.playSound(player, SoundConfig.INVENTORY_CLICK);
                        player.closeInventory();
                        queue2X1.register(player);
                    } else if (slot == QUEUE_4X1_SLOT) {
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
