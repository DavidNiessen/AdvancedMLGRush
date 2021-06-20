/*
 * Copyright (c) 2021 SkillCode
 *
 * This class is a part of the source code of the
 * AdvancedMLGRush plugin from SkillCode.
 *
 * This class may only be used in compliance with the
 * LICENSE.txt (https://github.com/SkillC0de/AdvancedMLGRush/blob/master/LICENSE.txt).
 *
 * Support: https://discord.skillplugins.com
 */

package net.skillcode.advancedmlgrush.item;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.annotations.PostConstruct;
import net.skillcode.advancedmlgrush.config.configs.ItemMaterialConfig;
import net.skillcode.advancedmlgrush.config.configs.ItemNameConfig;
import net.skillcode.advancedmlgrush.item.builder.IBFactory;
import net.skillcode.advancedmlgrush.item.builder.MetaType;
import net.skillcode.advancedmlgrush.item.overwriter.ItemOWManager;
import net.skillcode.advancedmlgrush.item.overwriter.ItemOWRegistry;
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
    private ItemOWRegistry itemOWRegistry;
    @Inject
    private IBFactory ibFactory;

    @PostConstruct
    public void init() {
        itemOWRegistry.registerOWs();
    }

    public ItemStack getItem(final @NotNull Optional<Player> optionalPlayer, final @NotNull EnumItem enumItem) {

        final Optional<ItemStack> optional = itemOWManager.getItem(optionalPlayer, enumItem);

        return optional.orElseGet(() -> ibFactory.create(MetaType.ITEM_META, getConfigData(enumItem))
                .name(itemNameConfig.getString(optionalPlayer, enumItem.getConfigPath()))
                .material(getConfigMaterial(enumItem))
                .unbreakable()
                .hideAttributes()
                .hideEnchants()
                .hideUnbreakable()
                .build());
    }

    private Material getConfigMaterial(final @NotNull EnumItem enumItem) {
        return itemMaterialConfig.getMaterial(enumItem);
    }

    private int getConfigData(final @NotNull EnumItem enumItem) {
        return itemMaterialConfig.getData(enumItem);
    }

}
