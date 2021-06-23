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

package net.skillcode.advancedmlgrush.api;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.item.overwriter.ItemOW;
import net.skillcode.advancedmlgrush.item.overwriter.ItemOWInitializer;
import net.skillcode.advancedmlgrush.item.overwriter.ItemOWManager;
import net.skillcode.advancedmlgrush.placeholder.Placeholder;
import net.skillcode.advancedmlgrush.placeholder.PlaceholderManager;
import net.skillcode.advancedmlgrush.sql.data.CachedSQLData;
import net.skillcode.advancedmlgrush.sql.data.SQLDataCache;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Singleton
public class APIImplementation implements MLGRushAPI {

    @Inject
    private ItemOWManager itemOWManager;
    @Inject
    private SQLDataCache sqlDataCache;
    @Inject
    private PlaceholderManager placeholderManager;

    @Override
    public void registerItemOW(final @NotNull ItemOW itemOW) {
        itemOWManager.registerItemOW(itemOW);
    }

    @Override
    public void registerPlaceholder(final @NotNull Placeholder placeholder) {
        placeholderManager.registerPlaceholder(placeholder);
    }

    @Override
    public CachedSQLData getSQLData(final @NotNull Player player) {
        return sqlDataCache.getSQLData(player);
    }

    @Override
    public boolean isLoaded(final @NotNull Player player) {
        return sqlDataCache.isLoaded(player);
    }
}
