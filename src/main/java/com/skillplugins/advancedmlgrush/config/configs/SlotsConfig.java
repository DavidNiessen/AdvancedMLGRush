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

package com.skillplugins.advancedmlgrush.config.configs;

import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.annotations.PostConstruct;
import com.skillplugins.advancedmlgrush.config.Configurable;
import com.skillplugins.advancedmlgrush.item.EnumItem;
import com.skillplugins.advancedmlgrush.miscellaneous.Constants;
import com.skillplugins.advancedmlgrush.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Singleton
public class SlotsConfig extends Configurable {

    @PostConstruct
    public void initConfig() {
        super.init();
    }

    @Override
    protected String filePath() {
        return Constants.SLOTS_CONFIG_PATH;
    }

    @Override
    protected void configure(final @NotNull List<Pair<String, Object>> list) {
        list.add(new Pair<>(EnumItem.CHALLENGER.getConfigPath(), 0));
        list.add(new Pair<>(EnumItem.SETTINGS.getConfigPath(), 2));
        list.add(new Pair<>(EnumItem.SPECTATE.getConfigPath(), 4));
        list.add(new Pair<>(EnumItem.GADGETS.getConfigPath(), 6));
        list.add(new Pair<>(EnumItem.STATS.getConfigPath(), 8));
        list.add(new Pair<>(EnumItem.QUEUE_LEAVE.getConfigPath(), 4));
        list.add(new Pair<>(EnumItem.SETTINGS_INVENTORY_SORTING.getConfigPath(), 11));
        list.add(new Pair<>(EnumItem.SETTINGS_MAP.getConfigPath(), 13));
        list.add(new Pair<>(EnumItem.SETTINGS_ROUNDS.getConfigPath(), 15));
        list.add(new Pair<>(EnumItem.GADGETS_STICK.getConfigPath(), 11));
        list.add(new Pair<>(EnumItem.GADGETS_BLOCKS.getConfigPath(), 15));
        list.add(new Pair<>(EnumItem.SORTING_SAVE.getConfigPath(), 21));
        list.add(new Pair<>(EnumItem.SORTING_RESET.getConfigPath(), 23));
        list.add(new Pair<>(EnumItem.ROUNDS.getConfigPath(), 13));
        list.add(new Pair<>(EnumItem.STATS_WINS.getConfigPath(), 19));
        list.add(new Pair<>(EnumItem.STATS_LOSES.getConfigPath(), 24));
        list.add(new Pair<>(EnumItem.STATS_WIN_RATE.getConfigPath(), 14));
        list.add(new Pair<>(EnumItem.STATS_BEDS.getConfigPath(), 12));
        list.add(new Pair<>(EnumItem.STATS_RANKING.getConfigPath(), 13));
        list.add(new Pair<>(EnumItem.STATS_KILLS.getConfigPath(), 20));
        list.add(new Pair<>(EnumItem.STATS_DEATHS.getConfigPath(), 25));
        list.add(new Pair<>(EnumItem.QUEUE_2X1.getConfigPath(), 11));
        list.add(new Pair<>(EnumItem.QUEUE_4X1.getConfigPath(), 15));
        list.add(new Pair<>(EnumItem.SPECTATE_LEAVE.getConfigPath(), 4));
        list.add(new Pair<>(EnumItem.RANDOM_ITEM.getConfigPath(), 49));
        list.add(new Pair<>(EnumItem.ROUNDS_DECREASE.getConfigPath(), 12));
        list.add(new Pair<>(EnumItem.ROUNDS_INCREASE.getConfigPath(), 14));
        list.add(new Pair<>(EnumItem.ARROW_LEFT.getConfigPath(), 45));
        list.add(new Pair<>(EnumItem.ARROW_RIGHT.getConfigPath(), 53));
    }

    public int getSlot(final @NotNull EnumItem enumItem) {
        return getInt(enumItem.getConfigPath());
    }


}
