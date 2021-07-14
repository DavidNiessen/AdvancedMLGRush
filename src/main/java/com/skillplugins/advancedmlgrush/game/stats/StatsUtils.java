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

package com.skillplugins.advancedmlgrush.game.stats;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.sql.data.CachedSQLData;
import com.skillplugins.advancedmlgrush.sql.data.SQLDataCache;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

@Singleton
public class StatsUtils {

    private final DecimalFormat decimalFormat = new DecimalFormat("###.##");

    @Inject
    private SQLDataCache sqlDataCache;

    public String getWinRate(final @NotNull Player player) {
        final CachedSQLData cachedSQLData = sqlDataCache.getSQLData(player);

        final double wins = cachedSQLData.getStatsWins();
        final double loses = cachedSQLData.getStatsLoses();

        final double winRate = loses == 0 ? wins : wins / loses;

        return decimalFormat.format(winRate);
    }

}
