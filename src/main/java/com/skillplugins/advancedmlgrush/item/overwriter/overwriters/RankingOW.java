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

package com.skillplugins.advancedmlgrush.item.overwriter.overwriters;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.config.configs.ItemMaterialConfig;
import com.skillplugins.advancedmlgrush.config.configs.MainConfig;
import com.skillplugins.advancedmlgrush.item.EnumItem;
import com.skillplugins.advancedmlgrush.item.builder.IBFactory;
import com.skillplugins.advancedmlgrush.item.builder.MetaType;
import com.skillplugins.advancedmlgrush.item.overwriter.ItemOW;
import com.skillplugins.advancedmlgrush.item.overwriter.ItemOWPriority;
import com.skillplugins.advancedmlgrush.placeholder.Placeholders;
import com.skillplugins.advancedmlgrush.util.Pair;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

@Singleton
public class RankingOW implements ItemOW {

    @Inject
    private ItemMaterialConfig itemMaterialConfig;
    @Inject
    private MainConfig mainConfig;
    @Inject
    private IBFactory ibFactory;
    @Inject
    private Placeholders placeholders;


    @Override
    public EnumItem getEnumItem() {
        return EnumItem.STATS_RANKING;
    }

    @Override
    public ItemOWPriority getPriority() {
        return ItemOWPriority.LOW;
    }

    @Override
    public ItemStack getItemStack(final @NotNull Optional<Player> optionalPlayer, final @NotNull String itemName) {
        final Pair<Material, Integer> material = itemMaterialConfig.getMaterial(EnumItem.STATS_RANKING);
        final List<String> lore = mainConfig.getArrayList(MainConfig.STATS_ITEM_LORE);
        placeholders.replace(optionalPlayer, lore);

        return optionalPlayer.map(player -> ibFactory.create(MetaType.ITEM_META, material.getValue())
                .name(itemName).material(material.getKey()).lore(lore).build()).orElse(ibFactory
                .create(MetaType.ITEM_META, material.getValue()).name(itemName).material(material.getKey()).build());
    }
}
