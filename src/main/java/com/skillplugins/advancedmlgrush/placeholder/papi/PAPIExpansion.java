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

package com.skillplugins.advancedmlgrush.placeholder.papi;

import com.skillplugins.advancedmlgrush.placeholder.Placeholders;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Optional;

public class PAPIExpansion extends PlaceholderExpansion {

    private final JavaPlugin javaPlugin;
    private final Placeholders placeholders;


    public PAPIExpansion(final @NotNull JavaPlugin javaPlugin,
                         final @NotNull Placeholders placeholders) {
        this.javaPlugin = javaPlugin;
        this.placeholders = placeholders;
    }


    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public @NotNull String getAuthor() {
        return javaPlugin.getDescription().getAuthors().toString();
    }

    @Override
    public @NotNull String getIdentifier() {
        return javaPlugin.getDescription().getName().toLowerCase(Locale.ROOT);
    }

    @Override
    public @NotNull String getVersion() {
        return javaPlugin.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(final @NotNull Player player, final @NotNull String identifier) {
        return placeholders.replace(Optional.ofNullable(player), "%" + identifier + "%");
    }

}
