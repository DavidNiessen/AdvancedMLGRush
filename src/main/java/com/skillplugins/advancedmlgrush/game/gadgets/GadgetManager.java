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

package com.skillplugins.advancedmlgrush.game.gadgets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.annotations.PostConstruct;
import com.skillplugins.advancedmlgrush.config.configs.MainConfig;
import com.skillplugins.advancedmlgrush.item.builder.IBFactory;
import com.skillplugins.advancedmlgrush.item.builder.ItemBuilder;
import com.skillplugins.advancedmlgrush.item.builder.MetaType;
import com.skillplugins.advancedmlgrush.item.parser.MaterialParser;
import com.skillplugins.advancedmlgrush.libs.xseries.XEnchantment;
import com.skillplugins.advancedmlgrush.placeholder.Placeholders;
import com.skillplugins.advancedmlgrush.sql.data.CachedSQLData;
import com.skillplugins.advancedmlgrush.sql.data.SQLDataCache;
import com.skillplugins.advancedmlgrush.util.Pair;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Singleton
public class GadgetManager {

    @Getter
    private final List<Gadget> sticks = new CopyOnWriteArrayList<>();
    @Getter
    private final List<Gadget> blocks = new CopyOnWriteArrayList<>();
    @Inject
    private GadgetFileLoader gadgetFileLoader;
    @Inject
    private MaterialParser materialParser;
    @Inject
    private IBFactory ibFactory;
    @Inject
    private Placeholders placeholders;
    @Inject
    private SQLDataCache sqlDataCache;
    @Inject
    private MainConfig mainConfig;

    @PostConstruct
    public void loadGadgets() {
        final GadgetsFile gadgetsFile = gadgetFileLoader.getGadgetsFile();

        sticks.addAll(gadgetsFile.getSticks() == null ? DefaultGadgets.getDefaultSticks() : gadgetsFile.getSticks());
        blocks.addAll(gadgetsFile.getBlocks() == null ? DefaultGadgets.getDefaultBlocks() : gadgetsFile.getBlocks());
    }

    public ItemBuilder getGadgetAsBuilder(final @NotNull Player player, final @NotNull Gadget gadget) {
        final Pair<Material, Integer> pair = materialParser.parse(gadget.getMaterial());
        final int index =
                sticks.contains(gadget) ? sticks.indexOf(gadget) : blocks.contains(gadget) ? blocks.indexOf(gadget) : 0;

        final List<String> lore = new ArrayList<>(Arrays.asList(" ",
                placeholders.replace(Optional.of(player), player.hasPermission(gadget.getPermission())
                        || index == 0 ? gadget.getLoreUnlocked() : gadget.getLoreLocked())));

        return ibFactory.create(MetaType.ITEM_META, pair.getValue()).material(pair.getKey())
                .name(placeholders.replace(Optional.of(player), gadget.getName()))
                .lore(lore).unbreakable().hideEnchants().hideUnbreakable();
    }

    public Gadget getStick(final @NotNull Player player) {
        final CachedSQLData cachedSQLData = sqlDataCache.getSQLData(player);
        return sticks.get(Math.min(sticks.size() - 1, cachedSQLData.getGadgetsStick()));
    }

    public ItemBuilder getStickAsBuilder(final @NotNull Player player) {
        return getGadgetAsBuilder(player, getStick(player)).enchantment(XEnchantment.KNOCKBACK.parseEnchantment(),
                mainConfig.getInt(MainConfig.STICK_KNOCKBACK_LEVEL)).lore(new ArrayList<>());
    }

    public Gadget getBlock(final @NotNull Player player) {
        final CachedSQLData cachedSQLData = sqlDataCache.getSQLData(player);
        return blocks.get(Math.min(blocks.size() - 1, cachedSQLData.getGadgetsBlocks()));
    }

    public ItemBuilder getBlockAsBuilder(final @NotNull Player player) {
        return getGadgetAsBuilder(player, getBlock(player)).lore(new ArrayList<>());
    }
}
