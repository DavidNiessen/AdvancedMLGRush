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
import net.skillcode.advancedmlgrush.config.configs.InventoryNameConfig;
import net.skillcode.advancedmlgrush.config.configs.SoundConfig;
import net.skillcode.advancedmlgrush.game.gadgets.Gadget;
import net.skillcode.advancedmlgrush.game.gadgets.GadgetManager;
import net.skillcode.advancedmlgrush.inventory.multipage.MultiPageInventory;
import net.skillcode.advancedmlgrush.item.builder.ItemBuilder;
import net.skillcode.advancedmlgrush.libs.xseries.XEnchantment;
import net.skillcode.advancedmlgrush.sql.data.CachedSQLData;
import net.skillcode.advancedmlgrush.sql.data.SQLDataCache;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Optional;

@Singleton
public class BlocksInventory extends MultiPageInventory {

    @Inject
    private GadgetManager gadgetManager;
    @Inject
    private SQLDataCache sqlDataCache;

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
