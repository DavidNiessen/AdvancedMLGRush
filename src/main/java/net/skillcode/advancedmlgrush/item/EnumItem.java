package net.skillcode.advancedmlgrush.item;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public enum EnumItem {

    CHALLENGER("challenger"),
    SETTINGS("settings"),
    SPECTATE("spectate"),
    EXTRAS("extras"),
    STATS("stats"),
    QUEUE_LEAVE("queue_leave"),
    INVENTORY_BACKGROUND("inventory_background"),
    ARROW_LEFT("arrow_left"),
    ARROW_RIGHT("arrow_right"),
    SETTINGS_INVENTORY_SORTING("settings_inventory_sorting"),
    SETTINGS_MAP("settings_map"),
    SETTINGS_STICK("settings_stick"),
    SETTINGS_BLOCKS("settings_block");

    private String configPath;

    EnumItem(final @NotNull String configPath) {
        this.configPath = configPath;
    }

}
