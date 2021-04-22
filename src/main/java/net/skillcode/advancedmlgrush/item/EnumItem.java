package net.skillcode.advancedmlgrush.item;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public enum EnumItem {

    CHALLENGER("challenger"),
    SETTINGS("settings"),
    SPECTATE("spectate"),
    EXTRAS("extras"),
    STATS("stats");

    private String configPath;

    EnumItem(final @NotNull String configPath) {
        this.configPath = configPath;
    }

}
