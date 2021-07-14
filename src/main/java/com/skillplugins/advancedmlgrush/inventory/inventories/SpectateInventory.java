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
import com.skillplugins.advancedmlgrush.config.configs.MainConfig;
import com.skillplugins.advancedmlgrush.game.map.MapInstance;
import com.skillplugins.advancedmlgrush.game.map.MapInstanceManager;
import com.skillplugins.advancedmlgrush.game.queue.Queue2x1;
import com.skillplugins.advancedmlgrush.game.queue.Queue4x1;
import com.skillplugins.advancedmlgrush.inventory.multipage.MultiPageInventory;
import com.skillplugins.advancedmlgrush.item.EnumItem;
import com.skillplugins.advancedmlgrush.item.builder.MetaType;
import com.skillplugins.advancedmlgrush.libs.xseries.XMaterial;
import com.skillplugins.advancedmlgrush.placeholder.Placeholders;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

@Singleton
public class SpectateInventory extends MultiPageInventory {

    @Inject
    private MapInstanceManager mapInstanceManager;
    @Inject
    private Queue2x1 queue2X1;
    @Inject
    private Queue4x1 queue4X1;
    @Inject
    private MainConfig mainConfig;
    @Inject
    private Placeholders placeholders;

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
        final LinkedHashMap<ItemStack, Object> map = new LinkedHashMap<>();

        mapInstanceManager.getMapInstances().stream().filter(MapInstance::isLoaded).forEach(mapInstance -> {
            final List<String> lore = mainConfig.getArrayList(MainConfig.MAP_ITEM_LORE);
            placeholders.replace(mapInstance.getPlayerByListIndex(0), lore);

            final XMaterial xMaterial = mapInstance.getMapData().getIcon();
            final Material material = xMaterial.parseMaterial();
            if (material != null) {
                map.put(ibFactory.create(MetaType.ITEM_META, XMaterial.isNewVersion() ? 0 : mapInstance.getMapData().getIconData())
                        .material(material).name(itemManager
                                .getItemName(mapInstance.getPlayer(0), EnumItem.MAP))
                        .lore(lore).build(), mapInstance);
            }
        });

        return map;
    }

    @Override
    protected void onElementClick(final Player player, final @NotNull Optional<Object> optional) {
        if (optional.isPresent() && optional.get() instanceof MapInstance) {
            final MapInstance mapInstance = (MapInstance) optional.get();

            queue2X1.unregister(player);
            queue4X1.unregister(player);

            mapInstance.addSpectator(player);
        }
    }
}
