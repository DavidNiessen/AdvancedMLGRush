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

package net.skillcode.advancedmlgrush.config.configs;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.annotations.PostConstruct;
import net.skillcode.advancedmlgrush.config.Configurable;
import net.skillcode.advancedmlgrush.item.EnumItem;
import net.skillcode.advancedmlgrush.item.parser.MaterialParser;
import net.skillcode.advancedmlgrush.libs.xseries.XMaterial;
import net.skillcode.advancedmlgrush.miscellaneous.Constants;
import net.skillcode.advancedmlgrush.util.Pair;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Singleton
public class ItemMaterialConfig extends Configurable {

    private final MaterialParser materialParser;

    @Inject
    public ItemMaterialConfig(final MaterialParser materialParser) {
        this.materialParser = materialParser;
    }

    @PostConstruct
    public void initConfig() {
        super.init();
    }

    @Override
    protected String filePath() {
        return Constants.ITEM_MATERIAL_CONFIG_PATH;
    }

    @Override
    protected void configure(final @NotNull List<Pair<String, Object>> list) {
        list.add(new Pair<>(EnumItem.INVENTORY_BACKGROUND.getConfigPath(), XMaterial.GRAY_STAINED_GLASS_PANE.name()));
        list.add(new Pair<>(EnumItem.CHALLENGER.getConfigPath(), XMaterial.IRON_SWORD.name()));
        list.add(new Pair<>(EnumItem.SETTINGS.getConfigPath(), XMaterial.REPEATER.name()));
        list.add(new Pair<>(EnumItem.SPECTATE.getConfigPath(), XMaterial.COMPASS.name()));
        list.add(new Pair<>(EnumItem.GADGETS.getConfigPath(), XMaterial.CHEST.name()));
        list.add(new Pair<>(EnumItem.STATS.getConfigPath(), XMaterial.PLAYER_HEAD.name()));
        list.add(new Pair<>(EnumItem.PICKAXE.getConfigPath(), XMaterial.STONE_PICKAXE.name()));
        list.add(new Pair<>(EnumItem.QUEUE_LEAVE.getConfigPath(), XMaterial.BARRIER.name()));
        list.add(new Pair<>(EnumItem.SETTINGS_INVENTORY_SORTING.getConfigPath(), XMaterial.REPEATER.name()));
        list.add(new Pair<>(EnumItem.SETTINGS_MAP.getConfigPath(), XMaterial.MAP.name()));
        list.add(new Pair<>(EnumItem.SETTINGS_ROUNDS.getConfigPath(), XMaterial.SLIME_BALL.name()));
        list.add(new Pair<>(EnumItem.GADGETS_STICK.getConfigPath(), XMaterial.STICK.name()));
        list.add(new Pair<>(EnumItem.GADGETS_BLOCKS.getConfigPath(), XMaterial.SANDSTONE.name()));
        list.add(new Pair<>(EnumItem.SORTING_SAVE.getConfigPath(), XMaterial.LIME_DYE.name()));
        list.add(new Pair<>(EnumItem.SORTING_RESET.getConfigPath(), XMaterial.RED_DYE.name()));
        list.add(new Pair<>(EnumItem.ROUNDS.getConfigPath(), XMaterial.SLIME_BALL.name()));
        list.add(new Pair<>(EnumItem.STATS_WINS.getConfigPath(), XMaterial.IRON_SWORD.name()));
        list.add(new Pair<>(EnumItem.STATS_LOSES.getConfigPath(), XMaterial.COAL.name()));
        list.add(new Pair<>(EnumItem.STATS_WIN_RATE.getConfigPath(), XMaterial.DIAMOND.name()));
        list.add(new Pair<>(EnumItem.STATS_BEDS.getConfigPath(), XMaterial.BED.name()));
        list.add(new Pair<>(EnumItem.STATS_RANKING.getConfigPath(), XMaterial.GOLD_BLOCK.name()));
        list.add(new Pair<>(EnumItem.QUEUE_2X1.getConfigPath(), XMaterial.NETHER_STAR.name()));
        list.add(new Pair<>(EnumItem.QUEUE_4X1.getConfigPath(), XMaterial.NETHER_STAR.name()));
        list.add(new Pair<>(EnumItem.SPECTATE_LEAVE.getConfigPath(), XMaterial.MAGMA_CREAM.name()));
    }

    public Pair<Material, Integer> getMaterial(final @NotNull String path) {
        return materialParser.parse(super.getString(path));
    }

    public Pair<Material, Integer> getMaterial(final @NotNull EnumItem enumItem) {
        return getMaterial(enumItem.getConfigPath());
    }
}
