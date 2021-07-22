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
import com.skillplugins.advancedmlgrush.config.configs.MessageConfig;
import com.skillplugins.advancedmlgrush.config.configs.SoundConfig;
import com.skillplugins.advancedmlgrush.game.gadgets.Gadget;
import com.skillplugins.advancedmlgrush.game.gadgets.GadgetManager;
import com.skillplugins.advancedmlgrush.inventory.multipage.MultiPageInventory;
import com.skillplugins.advancedmlgrush.item.builder.ItemBuilder;
import com.skillplugins.advancedmlgrush.lib.xseries.XEnchantment;
import com.skillplugins.advancedmlgrush.placeholder.Placeholders;
import com.skillplugins.advancedmlgrush.sql.data.CachedSQLData;
import com.skillplugins.advancedmlgrush.sql.data.SQLDataCache;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Optional;

@Singleton
public class StickInventory extends MultiPageInventory {

    @Inject
    private GadgetManager gadgetManager;
    @Inject
    private SQLDataCache sqlDataCache;
    @Inject
    private MessageConfig messageConfig;
    @Inject
    private Placeholders placeholders;

    @Override
    protected boolean playSound() {
        return false;
    }

    @Override
    protected String title() {
        return inventoryUtils.getInventoryName(InventoryNameConfig.STICK);
    }

    @Override
    protected LinkedHashMap<ItemStack, Object> onOpen(final @NotNull Player player) {
        final LinkedHashMap<ItemStack, Object> map = new LinkedHashMap<>();

        for (int i = 0; i < gadgetManager.getSticks().size(); i++) {
            final Gadget gadget = gadgetManager.getSticks().get(i);
            final ItemBuilder itemBuilder = gadgetManager.getGadgetAsBuilder(player, gadget);

            if (gadgetManager.getStick(player).equals(gadget)) {
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
            final int index = gadgetManager.getSticks().indexOf(gadget);

            if (player.hasPermission(gadget.getPermission())
                    || index == 0) {
                final CachedSQLData cachedSQLData = sqlDataCache.getSQLData(player);
                cachedSQLData.setGadgetsStick(index);
                soundUtil.playSound(player, SoundConfig.INVENTORY_CLICK);
                player.sendMessage(messageConfig.getWithPrefix(Optional.of(player), MessageConfig.GADGET_SELECT)
                        .replace("%gadget_name%", placeholders.replaceColors(gadget.getName())));
                player.closeInventory();
            } else {
                soundUtil.playSound(player, SoundConfig.ERROR);
            }
        }
    }
}
