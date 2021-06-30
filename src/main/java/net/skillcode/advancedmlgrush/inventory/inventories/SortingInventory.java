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
import net.skillcode.advancedmlgrush.config.configs.MessageConfig;
import net.skillcode.advancedmlgrush.config.configs.SoundConfig;
import net.skillcode.advancedmlgrush.event.EventListener;
import net.skillcode.advancedmlgrush.inventory.AbstractInventory;
import net.skillcode.advancedmlgrush.item.EnumItem;
import net.skillcode.advancedmlgrush.item.items.IngameItems;
import net.skillcode.advancedmlgrush.sql.data.CachedSQLData;
import net.skillcode.advancedmlgrush.sql.data.SQLDataCache;
import net.skillcode.advancedmlgrush.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

@Singleton
public class SortingInventory extends AbstractInventory {

    private static final int LOWER_RANGE = 9;
    private static final int HIGHER_RANGE = 18;

    @Inject
    private IngameItems ingameItems;
    @Inject
    private SQLDataCache sqlDataCache;
    @Inject
    private MessageConfig messageConfig;
    @Inject
    private JavaPlugin javaPlugin;

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
        return false;
    }

    @Override
    protected Pair<Inventory, String> onCreate() {
        final String title = inventoryUtils.getInventoryName(InventoryNameConfig.INVENTORY_SORTING);
        final Inventory inventory = Bukkit.createInventory(null, 3 * 9, title);

        inventoryUtils.fill(inventory);

        for (int i = LOWER_RANGE; i < HIGHER_RANGE; i++) {
            inventory.setItem(i, new ItemStack(Material.AIR));
        }
        return new Pair<>(inventory, title);
    }

    @Override
    protected Inventory onOpen(final @NotNull Inventory inventory, final @NotNull Player player) {
        final Pair<ItemStack, Integer> stick = ingameItems.getStick(player);
        final Pair<ItemStack, Integer> block = ingameItems.getBlock(player);
        final Pair<ItemStack, Integer> pickaxe = ingameItems.getPickaxe(player);

        final Optional<Player> optionalPlayer = Optional.of(player);

        inventory.setItem(21, itemManager.getItem(optionalPlayer, EnumItem.SORTING_SAVE));
        inventory.setItem(23, itemManager.getItem(optionalPlayer, EnumItem.SORTING_RESET));

        inventory.setItem(stick.getValue() + 9, stick.getKey());
        inventory.setItem(block.getValue() + 9, block.getKey());
        inventory.setItem(pickaxe.getValue() + 9, pickaxe.getKey());
        return inventory;
    }

    @Override
    protected List<EventListener<?>> listeners(final @NotNull List<EventListener<?>> eventListeners) {
        final Class<? extends AbstractInventory> clazz = this.getClass();
        eventListeners.add(new EventListener<InventoryClickEvent>(InventoryClickEvent.class) {
            @Override
            protected void onEvent(final @NotNull InventoryClickEvent event) {
                final Player player = (Player) event.getWhoClicked();
                final Inventory inventory = event.getInventory();
                if (inventoryUtils.isOpenInventory(player, clazz)) {
                    final int slot = event.getSlot();

                    if (slot >= SortingInventory.LOWER_RANGE
                            && slot < SortingInventory.HIGHER_RANGE
                            && !event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)
                            && !event.getAction().equals(InventoryAction.DROP_ALL_SLOT)
                            && !event.getAction().equals(InventoryAction.DROP_ALL_CURSOR)
                            && !event.getAction().equals(InventoryAction.DROP_ONE_SLOT)
                            && !event.getAction().equals(InventoryAction.DROP_ONE_CURSOR)
                            && !event.getClickedInventory().equals(player.getInventory())
                            && !event.getClickedInventory().getType().equals(InventoryType.PLAYER)) {

                        event.setCancelled(false);
                    } else {

                        final ItemStack currentItem = event.getCurrentItem();
                        final CachedSQLData cachedSQLData = sqlDataCache.getSQLData(player);
                        final CachedSQLData defaultSQLData = CachedSQLData.DEFAULT_SQL_DATA;

                        if (itemUtils.compare(currentItem, EnumItem.SORTING_RESET, Optional.of(player))) {

                            cachedSQLData.setSettingsStickSlot(defaultSQLData.getSettingsStickSlot());
                            cachedSQLData.setSettingsBlockSlot(defaultSQLData.getSettingsBlockSlot());
                            cachedSQLData.setSettingsPickaxeSlot(defaultSQLData.getSettingsPickaxeSlot());

                            soundUtil.playSound(player, SoundConfig.INVENTORY_CLICK);
                            player.closeInventory();

                        } else if (itemUtils.compare(currentItem, EnumItem.SORTING_SAVE, Optional.of(player))) {
                            int stickSlot = defaultSQLData.getSettingsStickSlot();
                            int blockSlot = defaultSQLData.getSettingsBlockSlot();
                            int pickaxeSlot = defaultSQLData.getSettingsPickaxeSlot();

                            final ItemStack stick = ingameItems.getStick(player).getKey();
                            final ItemStack block = ingameItems.getBlock(player).getKey();
                            final ItemStack pickaxe = ingameItems.getPickaxe(player).getKey();

                            int count = 0;
                            for (int i = SortingInventory.LOWER_RANGE; i < SortingInventory.HIGHER_RANGE; i++) {
                                final ItemStack itemStack = inventory.getItem(i);

                                if (itemUtils.isValidItem(itemStack)) {
                                    if (itemStack.equals(stick)) {
                                        stickSlot = i - 9;
                                        count++;
                                    } else if (itemStack.equals(block)) {
                                        blockSlot = i - 9;
                                        count++;
                                    } else if (itemStack.equals(pickaxe)) {
                                        pickaxeSlot = i - 9;
                                        count++;
                                    }
                                }
                            }

                            if (count == 3) {

                                cachedSQLData.setSettingsStickSlot(stickSlot);
                                cachedSQLData.setSettingsBlockSlot(blockSlot);
                                cachedSQLData.setSettingsPickaxeSlot(pickaxeSlot);

                                soundUtil.playSound(player, SoundConfig.INVENTORY_CLICK);
                            } else {
                                cachedSQLData.setSettingsStickSlot(defaultSQLData.getSettingsStickSlot());
                                cachedSQLData.setSettingsBlockSlot(defaultSQLData.getSettingsBlockSlot());
                                cachedSQLData.setSettingsPickaxeSlot(defaultSQLData.getSettingsPickaxeSlot());

                                player.sendMessage(messageConfig.getWithPrefix(Optional.of(player), MessageConfig.ERROR));
                                soundUtil.playSound(player, SoundConfig.ERROR);
                            }
                            player.closeInventory();

                            removeIngameItems(player);
                        }
                    }
                }

            }
        });

        eventListeners.add(new EventListener<InventoryDragEvent>(InventoryDragEvent.class) {
            @Override
            protected void onEvent(final @NotNull InventoryDragEvent event) {
                final Player player = (Player) event.getWhoClicked();
                if (inventoryUtils.isOpenInventory(player, clazz)) {
                    event.setCancelled(true);
                }
            }
        });

        eventListeners.add(new EventListener<InventoryCloseEvent>(InventoryCloseEvent.class) {
            @Override
            protected void onEvent(final @NotNull InventoryCloseEvent event) {
                final Player player = (Player) event.getPlayer();
                if (inventoryUtils.isOpenInventory(player, clazz)) {
                    removeIngameItems(player);
                }
            }
        });
        return eventListeners;

    }

    private void removeIngameItems(final @NotNull Player player) {
        final ItemStack stick = ingameItems.getStick(player).getKey();
        final ItemStack block = ingameItems.getBlock(player).getKey();
        final ItemStack pickaxe = ingameItems.getPickaxe(player).getKey();

        Bukkit.getScheduler().scheduleSyncDelayedTask(javaPlugin, () -> {

            for (final ItemStack itemStack : player.getInventory()) {
                if (itemUtils.isValidItem(itemStack)) {
                    if (itemStack.equals(stick)
                            || itemStack.equals(block)
                            || itemStack.equals(pickaxe)) {
                        player.getInventory().remove(itemStack);
                    }
                }
            }
        }, 5);
    }
}
