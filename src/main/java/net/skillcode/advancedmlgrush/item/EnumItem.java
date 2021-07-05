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

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public enum
EnumItem {
    INVENTORY_BACKGROUND("inventory_background"),
    CHALLENGER("challenger"),
    SETTINGS("settings"),
    SPECTATE("spectate"),
    GADGETS("gadgets"),
    STATS("stats"),
    PICKAXE("pickaxe"),
    QUEUE_LEAVE("queue_leave"),
    ARROW_LEFT("arrow_left"),
    ARROW_RIGHT("arrow_right"),
    RANDOM_ITEM("random_item"),
    SETTINGS_INVENTORY_SORTING("settings_inventory_sorting"),
    SETTINGS_MAP("settings_map"),
    SETTINGS_ROUNDS("settings_rounds"),
    GADGETS_STICK("gadgets_stick"),
    GADGETS_BLOCKS("gadgets_blocks"),
    SORTING_SAVE("sorting_save"),
    SORTING_RESET("sorting_reset"),
    ROUNDS_INCREASE("rounds_increase"),
    ROUNDS_DECREASE("rounds_decrease"),
    ROUNDS("rounds"),
    STATS_WINS("stats_wins"),
    STATS_LOSES("stats_loses"),
    STATS_WIN_RATE("stats_winrate"),
    STATS_BEDS("stats_beds"),
    STATS_RANKING("stats_ranking"),
    QUEUE_2X1("queue_2x1"),
    QUEUE_4x1("queue_4x1"),
    SPECTATE_LEAVE("spectate_leave"),
    MAP("map");

    private final String configPath;

    EnumItem(final @NotNull String configPath) {
        this.configPath = configPath;
    }

}
