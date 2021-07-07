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
import net.skillcode.advancedmlgrush.config.configs.ItemNameConfig;
import net.skillcode.advancedmlgrush.config.configs.SoundConfig;
import net.skillcode.advancedmlgrush.event.EventListener;
import net.skillcode.advancedmlgrush.event.EventListenerPriority;
import net.skillcode.advancedmlgrush.game.rounds.RoundManager;
import net.skillcode.advancedmlgrush.inventory.AbstractInventory;
import net.skillcode.advancedmlgrush.item.EnumItem;
import net.skillcode.advancedmlgrush.sql.data.SQLDataCache;
import net.skillcode.advancedmlgrush.util.Pair;
import net.skillcode.advancedmlgrush.util.SkullUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

@Singleton
public class RoundsInventory extends AbstractInventory {

    @Inject
    private SkullUtils skullUtils;
    @Inject
    private ItemNameConfig itemNameConfig;
    @Inject
    private SQLDataCache sqlDataCache;
    @Inject
    private RoundManager roundManager;


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
        final String title = inventoryUtils.getInventoryName(InventoryNameConfig.ROUNDS);
        final Inventory inventory = Bukkit.createInventory(null, 3 * 9, title);

        inventoryUtils.fill(inventory);
        inventory.setItem(itemManager.getItemSlot(EnumItem.ROUNDS_DECREASE), skullUtils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ4YTk5ZGIyYzM3ZWM3MWQ3MTk5Y2Q1MjYzOTk4MWE3NTEzY2U5Y2NhOTYyNmEzOTM2Zjk2NWIxMzExOTMifX19", itemNameConfig.getString(Optional.empty(), EnumItem.ROUNDS_DECREASE)).build());
        inventory.setItem(itemManager.getItemSlot(EnumItem.ROUNDS_INCREASE), skullUtils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkZDIwYmU5MzUyMDk0OWU2Y2U3ODlkYzRmNDNlZmFlYjI4YzcxN2VlNmJmY2JiZTAyNzgwMTQyZjcxNiJ9fX0=", itemNameConfig.getString(Optional.empty(), EnumItem.ROUNDS_INCREASE)).build());

        return new Pair<>(inventory, title);
    }

    @Override
    protected Inventory onOpen(final @NotNull Inventory inventory, final @NotNull Player player) {
        setRoundsItem(inventory, player);
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
                    if (itemUtils.isValidItem(currentItem)) {

                        if (itemUtils.compare(currentItem, EnumItem.ROUNDS_INCREASE, Optional.empty())
                                && roundManager.increaseRounds(player)) {
                            soundUtil.playSound(player, SoundConfig.INVENTORY_CLICK);
                        } else if (itemUtils.compare(currentItem, EnumItem.ROUNDS_DECREASE, Optional.empty())
                                && roundManager.decreaseRounds(player)) {
                            soundUtil.playSound(player, SoundConfig.INVENTORY_CLICK);
                        }
                        setRoundsItem(event.getInventory(), player);
                    }
                }
            }
        });

        eventListeners.add(new EventListener<InventoryCloseEvent>(InventoryCloseEvent.class, EventListenerPriority.MEDIUM) {
            @Override
            protected void onEvent(final @NotNull InventoryCloseEvent event) {
                final Player player = (Player) event.getPlayer();
                if (inventoryUtils.isOpenInventory(player, clazz)) {
                    roundManager.unregister(player);
                }
            }
        });
        return eventListeners;
    }

    private void setRoundsItem(final @NotNull Inventory inventory, final @NotNull Player player) {
        final ItemStack rounds = itemManager.getItem(Optional.of(player), EnumItem.ROUNDS);
        rounds.setAmount(sqlDataCache.getSQLData(player).getSettingsRounds());
        inventory.setItem(itemManager.getItemSlot(EnumItem.ROUNDS), rounds);
    }
}
