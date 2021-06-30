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

import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.config.configs.InventoryNameConfig;
import net.skillcode.advancedmlgrush.inventory.multipage.MultiPageInventory;
import net.skillcode.advancedmlgrush.item.builder.MetaType;
import net.skillcode.advancedmlgrush.libs.xseries.XMaterial;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Optional;

@Singleton
public class SpectateInventory extends MultiPageInventory {


    @Override
    protected boolean playSound() {
        return true;
    }

    @Override
    protected String title() {
        return inventoryUtils.getInventoryName(InventoryNameConfig.SPECTATE);
    }

    @Override
    protected LinkedHashMap<ItemStack, Object> onOpen(final @NotNull Player player) {
        final LinkedHashMap<ItemStack, Object> map = new LinkedHashMap<>(); // TODO: 26.06.21
        map.put(ibFactory.create(MetaType.ITEM_META, 0)
                .material(XMaterial.DIRT.parseMaterial()).build(), null);
        return map;
    }

    @Override
    protected void onElementClick(final Player player, final @NotNull Optional<Object> optional) {
        // TODO: 26.06.21
    }
}
