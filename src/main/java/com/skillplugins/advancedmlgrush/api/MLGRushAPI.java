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

import com.skillplugins.advancedmlgrush.event.events.PlayerDataLoadEvent;
import com.skillplugins.advancedmlgrush.game.GameState;
import com.skillplugins.advancedmlgrush.game.map.MapInstanceManager;
import com.skillplugins.advancedmlgrush.game.map.MapManager;
import com.skillplugins.advancedmlgrush.game.stats.Ranking;
import com.skillplugins.advancedmlgrush.item.overwriter.ItemOW;
import com.skillplugins.advancedmlgrush.sql.data.CachedSQLData;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

public interface MLGRushAPI {

    /**
     * Registers an ItemOverwriter.
     * <p>
     * ItemOverwriters allow you to overwrite items.
     *
     * @param itemOW the overwriter you want to register.
     */
    void registerItemOW(final @NotNull ItemOW itemOW);

    /**
     * Updates the scoreboard for all players.
     */
    void updateScoreboard();

    /**
     * Updates the scoreboard for some players
     *
     * @param iterable the iterable
     */
    void updateScoreboard(final @NotNull Iterable<Player> iterable);

    /**
     * Updates the scoreboard for a specific player
     *
     * @param player the player
     */
    void updateScoreboard(final @NotNull Player player);

    /**
     * Returns the SQL data of a player
     * <p>
     * Remember to check with {@link #isLoaded(Player)} if the player has been loaded.
     * If this is not the case, default SQL data will be returned.
     * <p>
     * Use the {@link  PlayerDataLoadEvent} instead of the {@link PlayerJoinEvent}.
     *
     * @param player The player you want to get the data from.
     * @return The SQL data of the player
     */
    CachedSQLData getSQLData(final @NotNull Player player);

    /**
     * The Ranking class allows you to get a player by a ranking,
     * ranking by player or the wins by ranking.
     *
     * @return the instance of Ranking
     */
    Ranking getRanking();

    /**
     * @param player the player.
     * @return whether the SQL data of the player has been loaded.
     */
    boolean isLoaded(final @NotNull Player player);

    /**
     * The MapManager stores all map templates.
     *
     * @return the MapManager.
     */
    MapManager getMapManager();

    /**
     * The MapInstanceManager allows you to get a players current map instance
     * or to create a new one.
     *
     * @return the MapInstanceManager.
     */
    MapInstanceManager getMapInstanceManager();

    /**
     * Get the current GameState of a player
     *
     * @param player the player
     * @return the GameState
     */
    GameState getGameState(final @NotNull Player player);


}
