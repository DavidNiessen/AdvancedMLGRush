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

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public enum EnumItem {

    CHALLENGER("challenger"),
    SETTINGS("settings"),
    SPECTATE("spectate"),
    GADGETS("gadgets"),
    STATS("stats"),
    QUEUE_LEAVE("queue_leave"),
    INVENTORY_BACKGROUND("inventory_background"),
    ARROW_LEFT("arrow_left"),
    ARROW_RIGHT("arrow_right"),
    SETTINGS_INVENTORY_SORTING("settings_inventory_sorting"),
    SETTINGS_MAP("settings_map"),
    SETTINGS_ROUNDS("settings_rounds"),
    GADGETS_STICK("gadgets_stick"),
    GADGETS_BLOCKS("gadgets_blocks");

    private final String configPath;

    EnumItem(final @NotNull String configPath) {
        this.configPath = configPath;
    }

}
