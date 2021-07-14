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
import com.skillplugins.advancedmlgrush.annotations.PostConstruct;
import com.skillplugins.advancedmlgrush.config.configs.InventoryNameConfig;
import com.skillplugins.advancedmlgrush.config.configs.MainConfig;
import com.skillplugins.advancedmlgrush.config.configs.SoundConfig;
import com.skillplugins.advancedmlgrush.event.EventListener;
import com.skillplugins.advancedmlgrush.event.EventListenerPriority;
import com.skillplugins.advancedmlgrush.game.map.MapData;
import com.skillplugins.advancedmlgrush.game.map.MapManager;
import com.skillplugins.advancedmlgrush.game.map.MapTemplate;
import com.skillplugins.advancedmlgrush.inventory.AbstractInventory;
import com.skillplugins.advancedmlgrush.inventory.multipage.MultiPageInventory;
import com.skillplugins.advancedmlgrush.item.EnumItem;
import com.skillplugins.advancedmlgrush.item.builder.ItemBuilder;
import com.skillplugins.advancedmlgrush.item.builder.MetaType;
import com.skillplugins.advancedmlgrush.libs.xseries.XMaterial;
import com.skillplugins.advancedmlgrush.miscellaneous.Constants;
import com.skillplugins.advancedmlgrush.placeholder.Placeholders;
import com.skillplugins.advancedmlgrush.sql.data.CachedSQLData;
import com.skillplugins.advancedmlgrush.sql.data.SQLDataCache;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
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

        mapManager.getMapTemplates2x1().forEach(mapTemplate -> {
            final MapData mapData = mapTemplate.getMapData();
            final Material material = mapData.getIcon().parseMaterial();
            if (material != null) {
                final ItemBuilder itemBuilder = ibFactory.create(MetaType.ITEM_META, XMaterial.isNewVersion() ? 0 : mapData.getIconData())
                        .name(itemPrefix + mapData.getName())
                        .material(material);

                if (mapData.getDescription().stream().anyMatch(s -> !s.trim().isEmpty())) {
                    final List<String> lore = mapData.getDescription();
                    placeholders.replace(Optional.of(player), lore);
                    itemBuilder.lore(lore);
                }
                map.put(itemBuilder.build(), mapTemplate);
            }
        });

        return map;
    }

    @Override
    protected Inventory modifyInventory(final @NotNull Inventory inventory) {
        inventory.setItem(itemManager.getItemSlot(EnumItem.RANDOM_ITEM), skullUtils.getSkull(Constants.RANDOM_ITEM_VALUE,
                itemNameConfig.getString(Optional.empty(), EnumItem.RANDOM_ITEM)).build());
        return inventory;
    }

    @Override
    protected void onElementClick(final Player player, final @NotNull Optional<Object> optional) {
        if (optional.isPresent() && optional.get() instanceof MapTemplate) {
            final MapTemplate mapTemplate = (MapTemplate) optional.get();
            final CachedSQLData cachedSQLData = sqlDataCache.getSQLData(player);

            final List<MapTemplate> mapTemplates = mapManager.getMapTemplates2x1();
            if (mapTemplates.contains(mapTemplate)) {
                cachedSQLData.setSettingsMap(mapTemplates.indexOf(mapTemplate));
            }

            soundUtil.playSound(player, SoundConfig.INVENTORY_CLICK);
            player.closeInventory();
        }
    }

    @Override
    protected List<EventListener<?>> registerCustomListeners(final @NotNull List<EventListener<?>> listeners) {
        final Class<? extends AbstractInventory> clazz = this.getClass();
        listeners.add(new EventListener<InventoryClickEvent>(InventoryClickEvent.class, EventListenerPriority.MEDIUM) {
            @Override
            protected void onEvent(final @NotNull InventoryClickEvent event) {
                final Player player = (Player) event.getWhoClicked();
                if (inventoryUtils.isOpenInventory(player, clazz)) {
                    final ItemStack currentItem = event.getCurrentItem();
                    if (itemUtils.compare(currentItem, EnumItem.RANDOM_ITEM, Optional.empty())) {
                        soundUtil.playSound(player, SoundConfig.INVENTORY_CLICK);
                        player.closeInventory();
                        sqlDataCache.getSQLData(player).setSettingsMap(-1);
                    }
                }
            }
        });
        return listeners;
    }
}
