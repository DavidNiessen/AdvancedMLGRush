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

package net.skillcode.advancedmlgrush.item;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.config.configs.ItemMaterialConfig;
import net.skillcode.advancedmlgrush.config.configs.ItemNameConfig;
import net.skillcode.advancedmlgrush.item.builder.IBFactory;
import net.skillcode.advancedmlgrush.item.builder.MetaType;
import net.skillcode.advancedmlgrush.item.overwriter.ItemOWManager;
import net.skillcode.advancedmlgrush.util.Pair;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Singleton
public class ItemManager {

    @Inject
    private ItemNameConfig itemNameConfig;
    @Inject
    private ItemMaterialConfig itemMaterialConfig;
    @Inject
    private ItemOWManager itemOWManager;
    @Inject
    private IBFactory ibFactory;


    public ItemStack getItem(final @NotNull Optional<Player> optionalPlayer, final @NotNull EnumItem enumItem) {

        final Optional<ItemStack> optional = itemOWManager.getItem(optionalPlayer, enumItem);
        final Pair<Material, Integer> pair = getConfigMaterial(enumItem);

        return optional.orElseGet(() -> ibFactory.create(MetaType.ITEM_META, pair.getValue())
                .name(itemNameConfig.getString(optionalPlayer, enumItem.getConfigPath()))
                .material(pair.getKey())
                .unbreakable()
                .hideAttributes()
                .hideEnchants()
                .hideUnbreakable()
                .build());
    }

    private Pair<Material, Integer> getConfigMaterial(final @NotNull EnumItem enumItem) {
        return itemMaterialConfig.getMaterial(enumItem);
    }

}
