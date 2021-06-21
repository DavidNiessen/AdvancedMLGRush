/*
 * Copyright (c) 2021 SkillCode
 *
 * This file is a part of the source code of the
 * AdvancedMLGRush plugin by SkillCode.
 *
 * This class may only be used in compliance with the
 * LICENSE.txt (https://github.com/SkillC0de/AdvancedMLGRush/blob/master/LICENSE.txt).
 *
 * Support: https://discord.skillplugins.com
 */

package net.skillcode.advancedmlgrush.item.items;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.game.gadgets.GadgetManager;
import net.skillcode.advancedmlgrush.item.EnumItem;
import net.skillcode.advancedmlgrush.item.ItemManager;
import net.skillcode.advancedmlgrush.sql.data.SQLDataCache;
import net.skillcode.advancedmlgrush.util.Pair;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Singleton
public class IngameItems {

    @Inject
    private ItemManager itemManager;
    @Inject
    private SQLDataCache sqlDataCache;
    @Inject
    private GadgetManager gadgetManager;

    public Pair<ItemStack, Integer> getStick(final @NotNull Player player) {
        final ItemStack stick = gadgetManager.getStickAsBuilder(player).build();
        return new Pair<>(stick, sqlDataCache.getSQLData(player).getSettingsStickSlot());
    }

    public Pair<ItemStack, Integer> getBlock(final @NotNull Player player) {
        final ItemStack block = gadgetManager.getBlockAsBuilder(player).build();
        return new Pair<>(block, sqlDataCache.getSQLData(player).getSettingsBlockSlot());
    }

    public Pair<ItemStack, Integer> getPickaxe(final @NotNull Player player) {
        final ItemStack pickaxe = itemManager.getItem(Optional.of(player), EnumItem.PICKAXE);
        return new Pair<>(pickaxe, sqlDataCache.getSQLData(player).getSettingsPickaxeSlot());
    }

    public void setIngameItems(final @NotNull Player player) {
        final Pair<ItemStack, Integer> stick = getStick(player);
        final Pair<ItemStack, Integer> block = getBlock(player);
        final Pair<ItemStack, Integer> pickaxe = getPickaxe(player);

        player.getInventory().setItem(stick.getValue(), stick.getKey());
        player.getInventory().setItem(block.getValue(), block.getKey());
        player.getInventory().setItem(pickaxe.getValue(), pickaxe.getKey());
    }
}
