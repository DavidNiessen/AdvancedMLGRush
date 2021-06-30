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
import net.skillcode.advancedmlgrush.game.queue.Queue1x1;
import net.skillcode.advancedmlgrush.game.queue.Queue1x4;
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

    private static final int QUEUE_1X1_SLOT = 11;
    private static final int QUEUE_1X4_SLOT = 15;

    @Inject
    private Queue1x1 queue1x1;
    @Inject
    private Queue1x4 queue1x4;

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
        queue1x1.unregister(player);
        queue1x4.unregister(player);
        inventory.setItem(QUEUE_1X1_SLOT, itemManager.getItem(optionalPlayer, EnumItem.QUEUE_1x1));
        inventory.setItem(QUEUE_1X4_SLOT, itemManager.getItem(optionalPlayer, EnumItem.QUEUE_1x4));
        return inventory;
    }

    @Override
    protected List<EventListener<?>> listeners(@NotNull List<EventListener<?>> eventListeners) {
        final Class<? extends AbstractInventory> clazz = this.getClass();
        eventListeners.add(new EventListener<InventoryClickEvent>(InventoryClickEvent.class) {
            @Override
            protected void onEvent(final @NotNull InventoryClickEvent event) {
                final Player player = (Player) event.getWhoClicked();
                if (inventoryUtils.isOpenInventory(player, clazz)) {

                    final int slot = event.getSlot();
                    if (slot == QUEUE_1X1_SLOT) {
                        soundUtil.playSound(player, SoundConfig.INVENTORY_CLICK);
                        player.closeInventory();
                        queue1x1.register(player);
                    } else if (slot == QUEUE_1X4_SLOT) {
                        soundUtil.playSound(player, SoundConfig.INVENTORY_CLICK);
                        player.closeInventory();
                        queue1x4.register(player);
                    }
                }
            }
        });
        return eventListeners;
    }
}
