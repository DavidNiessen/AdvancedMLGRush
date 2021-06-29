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

package net.skillcode.advancedmlgrush.inventory.inventories;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.annotations.PostConstruct;
import net.skillcode.advancedmlgrush.config.configs.InventoryNameConfig;
import net.skillcode.advancedmlgrush.config.configs.MainConfig;
import net.skillcode.advancedmlgrush.config.configs.SoundConfig;
import net.skillcode.advancedmlgrush.game.map.MapData;
import net.skillcode.advancedmlgrush.game.map.MapManager;
import net.skillcode.advancedmlgrush.game.map.MapTemplate;
import net.skillcode.advancedmlgrush.inventory.multipage.MultiPageInventory;
import net.skillcode.advancedmlgrush.item.builder.MetaType;
import net.skillcode.advancedmlgrush.placeholder.Placeholders;
import net.skillcode.advancedmlgrush.sql.data.CachedSQLData;
import net.skillcode.advancedmlgrush.sql.data.SQLDataCache;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

@Singleton
public class MapInventory extends MultiPageInventory {

    @Inject
    private MapManager mapManager;
    @Inject
    private MainConfig mainConfig;
    @Inject
    private Placeholders placeholders;
    @Inject
    private SQLDataCache sqlDataCache;

    @PostConstruct
    public void initInventory() {
        super.init();
    }

    @Override
    protected boolean playSound() {
        return false;
    }

    @Override
    protected String title() {
        return inventoryUtils.getInventoryName(InventoryNameConfig.MAP);
    }

    @Override
    protected LinkedHashMap<ItemStack, Object> onOpen(final @NotNull Player player) {
        final LinkedHashMap<ItemStack, Object> map = new LinkedHashMap<>();
        final String itemPrefix = placeholders.replace(Optional.of(player), mainConfig.getString(MainConfig.MAP_ITEM_PREFIX));

        mapManager.getMapTemplates1x1().forEach(mapTemplate -> {
            final MapData mapData = mapTemplate.getMapData();
            map.put(ibFactory.create(MetaType.ITEM_META, mapData.getIconData()).name(itemPrefix + mapData.getName())
                    .material(mapData.getIcon()).build(), mapTemplate);
        });

        return map;
    }

    @Override
    protected void onElementClick(final Player player, final @NotNull Optional<Object> optional) {
        if (optional.isPresent() && optional.get() instanceof MapTemplate) {
            final MapTemplate mapTemplate = (MapTemplate) optional.get();
            final CachedSQLData cachedSQLData = sqlDataCache.getSQLData(player);

            final List<MapTemplate> mapTemplates = mapManager.getMapTemplates1x1();
            if (mapTemplates.contains(mapTemplate)) {
                cachedSQLData.setSettingsMap(mapTemplates.indexOf(mapTemplate));
            }

            soundUtil.playSound(player, SoundConfig.INVENTORY_CLICK);
            player.closeInventory();
        }
    }
}
