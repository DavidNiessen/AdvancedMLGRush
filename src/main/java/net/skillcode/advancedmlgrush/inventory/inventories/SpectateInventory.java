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
import net.skillcode.advancedmlgrush.config.configs.MainConfig;
import net.skillcode.advancedmlgrush.game.map.MapInstance;
import net.skillcode.advancedmlgrush.game.map.MapInstanceManager;
import net.skillcode.advancedmlgrush.game.queue.Queue1x1;
import net.skillcode.advancedmlgrush.game.queue.Queue1x4;
import net.skillcode.advancedmlgrush.inventory.multipage.MultiPageInventory;
import net.skillcode.advancedmlgrush.item.EnumItem;
import net.skillcode.advancedmlgrush.item.builder.MetaType;
import net.skillcode.advancedmlgrush.placeholder.Placeholders;
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
    private Queue1x1 queue1x1;
    @Inject
    private Queue1x4 queue1x4;
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
            placeholders.replace(mapInstance.getPlayer(0), lore);

            map.put(ibFactory.create(MetaType.ITEM_META, mapInstance.getMapData().getIconData())
                    .material(mapInstance.getMapData().getIcon()).name(itemManager
                            .getItemName(mapInstance.getPlayer(0), EnumItem.MAP))
                    .lore(lore).build(), mapInstance);
        });

        return map;
    }

    @Override
    protected void onElementClick(final Player player, final @NotNull Optional<Object> optional) {
        if (optional.isPresent() && optional.get() instanceof MapInstance) {
            final MapInstance mapInstance = (MapInstance) optional.get();

            queue1x1.unregister(player);
            queue1x4.unregister(player);

            mapInstance.addSpectator(player);
        }
    }
}
