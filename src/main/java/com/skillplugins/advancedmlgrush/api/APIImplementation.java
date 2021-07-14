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

package com.skillplugins.advancedmlgrush.api;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.game.GameState;
import com.skillplugins.advancedmlgrush.game.GameStateManager;
import com.skillplugins.advancedmlgrush.game.map.MapInstanceManager;
import com.skillplugins.advancedmlgrush.game.map.MapManager;
import com.skillplugins.advancedmlgrush.game.scoreboard.ScoreboardManager;
import com.skillplugins.advancedmlgrush.game.stats.Ranking;
import com.skillplugins.advancedmlgrush.item.overwriter.ItemOW;
import com.skillplugins.advancedmlgrush.item.overwriter.ItemOWManager;
import com.skillplugins.advancedmlgrush.sql.data.CachedSQLData;
import com.skillplugins.advancedmlgrush.sql.data.SQLDataCache;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Singleton
public class APIImplementation implements MLGRushAPI {

    @Inject
    private ItemOWManager itemOWManager;
    @Inject
    private SQLDataCache sqlDataCache;
    @Inject
    private MapInstanceManager mapInstanceManager;
    @Inject
    private MapManager mapManager;
    @Inject
    private ScoreboardManager scoreboardManager;
    @Inject
    private Ranking ranking;
    @Inject
    private GameStateManager gameStateManager;

    @Override
    public void registerItemOW(final @NotNull ItemOW itemOW) {
        itemOWManager.registerItemOW(itemOW);
    }

    @Override
    public void updateScoreboard() {
        scoreboardManager.updateScoreboard();
    }

    @Override
    public void updateScoreboard(final @NotNull Iterable<Player> iterable) {
        scoreboardManager.updateScoreboard(iterable);
    }

    @Override
    public void updateScoreboard(final @NotNull Player player) {
        scoreboardManager.updateScoreboard(player);
    }

    @Override
    public CachedSQLData getSQLData(final @NotNull Player player) {
        return sqlDataCache.getSQLData(player);
    }

    @Override
    public Ranking getRanking() {
        return ranking;
    }

    @Override
    public boolean isLoaded(final @NotNull Player player) {
        return sqlDataCache.isLoaded(player);
    }

    @Override
    public MapManager getMapManager() {
        return mapManager;
    }

    @Override
    public MapInstanceManager getMapInstanceManager() {
        return mapInstanceManager;
    }

    @Override
    public GameState getGameState(final @NotNull Player player) {
        return gameStateManager.getGameState(player);
    }
}
