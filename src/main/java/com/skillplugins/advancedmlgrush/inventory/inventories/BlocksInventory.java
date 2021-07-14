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
import com.skillplugins.advancedmlgrush.config.configs.InventoryNameConfig;
import com.skillplugins.advancedmlgrush.config.configs.SoundConfig;
import com.skillplugins.advancedmlgrush.game.gadgets.Gadget;
import com.skillplugins.advancedmlgrush.game.gadgets.GadgetManager;
import com.skillplugins.advancedmlgrush.inventory.multipage.MultiPageInventory;
import com.skillplugins.advancedmlgrush.item.builder.ItemBuilder;
import com.skillplugins.advancedmlgrush.libs.xseries.XEnchantment;
import com.skillplugins.advancedmlgrush.sql.data.CachedSQLData;
import com.skillplugins.advancedmlgrush.sql.data.SQLDataCache;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Optional;

@Singleton
public class BlocksInventory extends MultiPageInventory {


    private final GadgetManager gadgetManager;
    private final SQLDataCache sqlDataCache;

    @Inject
    public BlocksInventory(final @NotNull GadgetManager gadgetManager,
                           final @NotNull SQLDataCache sqlDataCache) {
        this.gadgetManager = gadgetManager;
        this.sqlDataCache = sqlDataCache;
    }

    @Override
    protected boolean playSound() {
        return false;
    }

    @Override
    protected String title() {
        return inventoryUtils.getInventoryName(InventoryNameConfig.BLOCKS);
    }

    @Override
    protected LinkedHashMap<ItemStack, Object> onOpen(final @NotNull Player player) {
        final LinkedHashMap<ItemStack, Object> map = new LinkedHashMap<>();

        for (int i = 0; i < gadgetManager.getBlocks().size(); i++) {
            final Gadget gadget = gadgetManager.getBlocks().get(i);
            final ItemBuilder itemBuilder = gadgetManager.getGadgetAsBuilder(player, gadget);

            if (gadgetManager.getBlock(player).equals(gadget)) {
                itemBuilder.enchantment(XEnchantment.LUCK.parseEnchantment(), 1);
            }

            map.put(itemBuilder.build(), gadget);
        }
        return map;
    }

    @Override
    protected void onElementClick(final Player player, final @NotNull Optional<Object> optional) {
        if (optional.isPresent() && optional.get() instanceof Gadget) {
            final Gadget gadget = (Gadget) optional.get();
            final int index = gadgetManager.getBlocks().indexOf(gadget);

            if (player.hasPermission(gadget.getPermission())
                    || index == 0) {
                final CachedSQLData cachedSQLData = sqlDataCache.getSQLData(player);
                cachedSQLData.setGadgetsBlocks(index);
                soundUtil.playSound(player, SoundConfig.INVENTORY_CLICK);
                player.closeInventory();
            } else {
                soundUtil.playSound(player, SoundConfig.ERROR);
            }
        }
    }

}
