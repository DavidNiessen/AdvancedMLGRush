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

package com.skillplugins.advancedmlgrush.item.overwriter;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.config.configs.ItemNameConfig;
import com.skillplugins.advancedmlgrush.item.EnumItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class ItemOWManager {

    private final ItemNameConfig itemNameConfig;

    private final Map<EnumItem, ItemOW> ows = new ConcurrentHashMap<>();

    @Inject
    public ItemOWManager(final @NotNull ItemNameConfig itemNameConfig) {
        this.itemNameConfig = itemNameConfig;
    }

    public void registerItemOW(final @NotNull ItemOW itemOW) {

        if (ows.containsKey(itemOW.getEnumItem()) && itemOW.getPriority() != ItemOWPriority.HIGH) {
            return;
        }

        ows.put(itemOW.getEnumItem(), itemOW);
    }

    public Optional<ItemStack> getItem(final @NotNull Optional<Player> optionalPlayer, final @NotNull EnumItem enumItem) {
        if (ows.containsKey(enumItem)) {
            return Optional.of(ows.get(enumItem).getItemStack(optionalPlayer,
                    itemNameConfig.getString(optionalPlayer, enumItem)));
        }

        return Optional.empty();
    }

}
