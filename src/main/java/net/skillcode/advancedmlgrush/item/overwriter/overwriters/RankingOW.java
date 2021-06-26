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

package net.skillcode.advancedmlgrush.item.overwriter.overwriters;

import com.google.inject.Inject;
import net.skillcode.advancedmlgrush.config.configs.ItemMaterialConfig;
import net.skillcode.advancedmlgrush.config.configs.MainConfig;
import net.skillcode.advancedmlgrush.item.EnumItem;
import net.skillcode.advancedmlgrush.item.builder.IBFactory;
import net.skillcode.advancedmlgrush.item.builder.MetaType;
import net.skillcode.advancedmlgrush.item.overwriter.ItemOW;
import net.skillcode.advancedmlgrush.item.overwriter.ItemOWPriority;
import net.skillcode.advancedmlgrush.placeholder.Placeholders;
import net.skillcode.advancedmlgrush.util.Pair;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

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
