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

package com.skillplugins.advancedmlgrush.sql.datasavers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.annotations.PostConstruct;
import com.skillplugins.advancedmlgrush.config.configs.DataConfig;
import com.skillplugins.advancedmlgrush.config.configs.MainConfig;
import com.skillplugins.advancedmlgrush.miscellaneous.Constants;
import com.skillplugins.advancedmlgrush.sql.DataSaver;
import com.skillplugins.advancedmlgrush.sql.DataSaverParams;
import com.skillplugins.advancedmlgrush.sql.data.CachedSQLData;
import com.skillplugins.advancedmlgrush.util.Pair;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Singleton
public class MLGDataSaver extends DataSaver {


    private final DataConfig dataConfig;
    private final MainConfig mainConfig;

    private boolean isOfflineMode = false;

    @Inject
    public MLGDataSaver(final @NotNull DataConfig dataConfig,
                        final @NotNull MainConfig mainConfig) {
        this.dataConfig = dataConfig;
        this.mainConfig = mainConfig;
    }

    @PostConstruct
    public void initDataSaver() {
        isOfflineMode = mainConfig.getBoolean(MainConfig.OFFLINE_MODE);
    }


    public CompletableFuture<CachedSQLData> getPlayerData(final @NotNull Player player) {
        return CompletableFuture.supplyAsync(() -> getPlayerDataSync(player), threadPoolManager.getThreadPool());
    }

    public void updatePlayer(final @NotNull Player player, final @NotNull CachedSQLData cachedSQLData) {
        if (isConnected()
                && !cachedSQLData.isDefaultData()) {
            executeUpdateAsync(String.format(
                    "UPDATE {name} " +
                            "SET " +
                            "settings_stick_slot = '%1$s', " +
                            "settings_block_slot = '%2$s', " +
                            "settings_pickaxe_slot = '%3$s', " +
                            "settings_map = '%4$s', " +
                            "settings_rounds = '%5$s', " +
                            "gadgets_stick = '%6$s', " +
                            "gadgets_blocks = '%7$s', " +
                            "stats_wins = '%8$s', " +
                            "stats_loses = '%9$s', " +
                            "stats_beds = '%10$s', " +
                            "stats_kills = '%11$s', " +
                            "stats_deaths = '%12$s' " +
                            (isOfflineMode
                                    ? "WHERE player_name = '%13$s';"
                                    : "WHERE player_uuid = '%13$s';"),
                    cachedSQLData.getSettingsStickSlot(), cachedSQLData.getSettingsBlockSlot(),
                    cachedSQLData.getSettingsPickaxeSlot(), cachedSQLData.getSettingsMap(),
                    cachedSQLData.getSettingsRounds(), cachedSQLData.getGadgetsStick(),
                    cachedSQLData.getGadgetsBlocks(), cachedSQLData.getStatsWins(),
                    cachedSQLData.getStatsLoses(), cachedSQLData.getStatsBeds(),
                    cachedSQLData.getStatsKills(), cachedSQLData.getStatsDeaths(),
                    isOfflineMode ? player.getName() : player.getUniqueId().toString()));
        }
    }

    public void resetStats(final @NotNull String playerName, final @NotNull Consumer<StatsResetState> consumer) {
        if (isConnected()) {
            super.executeQueryAsync(String.format(
                    "SELECT player_name " +
                            "FROM {name} " +
                            "WHERE player_name = '%s';", playerName), new Callback() {
                @Override
                public void onSuccess(final @NotNull ResultSet resultSet) throws SQLException {
                    if (!resultSet.next()) {
                        consumer.accept(StatsResetState.UNKNOWN_PLAYER);
                    } else {
                        executeUpdateAsync(String.format(
                                "UPDATE {name} " +
                                        "SET " +
                                        "stats_wins = 0, " +
                                        "stats_loses = 0, " +
                                        "stats_beds = 0, " +
                                        "stats_kills = 0, " +
                                        "stats_deaths = 0 " +
                                        "WHERE player_name = '%s';", playerName));

                        consumer.accept(StatsResetState.SUCCEED);
                    }
                }

                @Override
                public void onFailure(final @NotNull Optional<Exception> optional) {
                    consumer.accept(StatsResetState.ERROR);
                }
            });
        }
    }

    public void updateRanking(final @NotNull BiConsumer<Map<String, Integer>, Map<Integer, Integer>> biConsumer) {
        if (isConnected()) {
            final Callback callback = new Callback() {
                @Override
                public void onSuccess(final @NotNull ResultSet resultSet) throws SQLException {
                    final Map<String, Integer> map = new HashMap<>();
                    final Map<Integer, Integer> wins = new HashMap<>();
                    int count = 0;
                    while (resultSet.next()) {
                        map.put(resultSet.getString("player_name"), ++count);
                        wins.put(count, resultSet.getInt("stats_wins"));
                    }

                    biConsumer.accept(map, wins);
                }

                @Override
                public void onFailure(final @NotNull Optional<Exception> optional) {
                    //empty
                }
            };

            executeQueryAsync(
                    "SELECT player_name, stats_wins " +
                            "FROM {name} " +
                            "ORDER BY stats_wins DESC;", callback);
        }
    }

    @Override
    protected DataSaverParams initParams() {
        return new DataSaverParams(
                dataConfig.getBoolean(DataConfig.USE_MYSQL), dataConfig.getString(DataConfig.MYSQL_HOST),
                dataConfig.getString(DataConfig.MYSQL_PORT), dataConfig.getString(DataConfig.MYSQL_DATABASE),
                dataConfig.getString(DataConfig.MYSQL_USER), dataConfig.getString(DataConfig.MYSQL_PASSWORD),
                Constants.TABLE_NAME, Constants.DATABASE_PATH);
    }

    @Override
    protected String creationQuery() {
        return "CREATE TABLE IF NOT EXISTS {name} (" +
                "player_uuid VARCHAR(36) NOT NULL, " +
                "player_name VARCHAR(100) NOT NULL, " +
                "settings_stick_slot TINYINT NOT NULL DEFAULT 0, " +
                "settings_block_slot TINYINT NOT NULL DEFAULT 1, " +
                "settings_pickaxe_slot TINYINT NOT NULL DEFAULT 8, " +
                "settings_map SMALLINT NOT NULL DEFAULT -1, " +
                "settings_rounds TINYINT NOT NULL DEFAULT 5, " +
                "gadgets_stick SMALLINT NOT NULL DEFAULT 0, " +
                "gadgets_blocks SMALLINT NOT NULL DEFAULT 0, " +
                "stats_wins INT NOT NULL DEFAULT 0, " +
                "stats_loses INT NOT NULL DEFAULT 0, " +
                "stats_beds INT NOT NULL DEFAULT 0, " +
                "stats_kills INT NOT NULL DEFAULT 0, " +
                "stats_deaths INT NOT NULL DEFAULT 0, " +
                "PRIMARY KEY (player_uuid, player_name));";
    }

    @Override
    protected List<Pair<String, String>> columns(final @NotNull List<Pair<String, String>> columns) {
        columns.add(new Pair<>("player_uuid", "VARCHAR(36) NOT NULL"));
        columns.add(new Pair<>("player_name", "VARCHAR(100) NOT NULL"));
        columns.add(new Pair<>("settings_stick_slot", "TINYINT NOT NULL DEFAULT 0"));
        columns.add(new Pair<>("settings_block_slot", "TINYINT NOT NULL DEFAULT 1"));
        columns.add(new Pair<>("settings_pickaxe_slot", "TINYINT NOT NULL DEFAULT 8"));
        columns.add(new Pair<>("settings_map", "SMALLINT NOT NULL DEFAULT -1"));
        columns.add(new Pair<>("settings_rounds", "TINYINT NOT NULL DEFAULT 5"));
        columns.add(new Pair<>("gadgets_stick", "SMALLINT NOT NULL DEFAULT 0"));
        columns.add(new Pair<>("gadgets_blocks", "SMALLINT NOT NULL DEFAULT 0"));
        columns.add(new Pair<>("stats_wins", "INT NOT NULL DEFAULT 0"));
        columns.add(new Pair<>("stats_loses", "INT NOT NULL DEFAULT 0"));
        columns.add(new Pair<>("stats_beds", "INT NOT NULL DEFAULT 0"));
        columns.add(new Pair<>("stats_kills", "INT NOT NULL DEFAULT 0"));
        columns.add(new Pair<>("stats_deaths", "INT NOT NULL DEFAULT 0"));
        return columns;
    }

    private CachedSQLData getPlayerDataSync(final @NotNull Player player) {
        final CachedSQLData cachedSQLData = new CachedSQLData();
        if (isConnected()) {
            if (!isInDatabase(player)) {
                insertPlayer(player);
            }
            checkName(player);
            getData(player, cachedSQLData);
            cachedSQLData.setDefaultData(false);
        }
        return cachedSQLData;
    }

    private boolean isInDatabase(final @NotNull Player player) {
        if (isConnected()) {
            final Optional<ResultSet> optional = executeQuerySync(String.format(
                    "SELECT player_name " +
                            "FROM {name} " +
                            (isOfflineMode ?
                                    "WHERE player_name = '%s';"
                                    : "WHERE player_uuid = '%s';"),
                    isOfflineMode ? player.getName() : player.getUniqueId().toString()));

            if (optional.isPresent()) {
                final ResultSet resultSet = optional.get();
                try {
                    return resultSet.next();
                } catch (SQLException throwables) {
                    exceptionHandler.handleUnexpected(throwables);
                }
            }
        }
        return false;
    }

    private void checkName(final @NotNull Player player) {
        if (isConnected()
                && !isOfflineMode) {
            final Optional<ResultSet> optional = executeQuerySync(String.format(
                    "SELECT player_name " +
                            "FROM {name} " +
                            "WHERE player_uuid = '%s';", player.getUniqueId().toString()));

            if (optional.isPresent()) {
                final ResultSet resultSet = optional.get();
                try {
                    if (resultSet.next()
                            && !resultSet.getString("player_name").equals(player.getName())) {
                        executeUpdateSync(String.format(
                                "UPDATE {name} " +
                                        "SET player_name = '%1$s'" +
                                        "WHERE player_uuid = '%2$s';", player.getName(), player.getUniqueId().toString()));
                    }
                } catch (SQLException throwables) {
                    exceptionHandler.handleUnexpected(throwables);
                }
            }
        }
    }

    private void getData(final @NotNull Player player, final @NotNull CachedSQLData cachedSQLData) {
        if (isConnected()) {
            final Optional<ResultSet> optional = executeQuerySync(String.format(
                    "SELECT * " +
                            "FROM {name} " +
                            (isOfflineMode ?
                                    "WHERE player_name = '%s';"
                                    : "WHERE player_uuid = '%s';"),
                    isOfflineMode ? player.getName() : player.getUniqueId().toString()));

            if (optional.isPresent()) {
                final ResultSet resultSet = optional.get();
                try {
                    if (resultSet.next()) {
                        cachedSQLData.setSettingsStickSlot(resultSet.getInt("settings_stick_slot"));
                        cachedSQLData.setSettingsBlockSlot(resultSet.getInt("settings_block_slot"));
                        cachedSQLData.setSettingsPickaxeSlot(resultSet.getInt("settings_pickaxe_slot"));
                        cachedSQLData.setSettingsMap(resultSet.getInt("settings_map"));
                        cachedSQLData.setSettingsRounds(resultSet.getInt("settings_rounds"));
                        cachedSQLData.setGadgetsStick(resultSet.getInt("gadgets_stick"));
                        cachedSQLData.setGadgetsBlocks(resultSet.getInt("gadgets_blocks"));
                        cachedSQLData.setStatsWins(resultSet.getInt("stats_wins"));
                        cachedSQLData.setStatsLoses(resultSet.getInt("stats_loses"));
                        cachedSQLData.setStatsBeds(resultSet.getInt("stats_beds"));
                        cachedSQLData.setStatsKills(resultSet.getInt("stats_kills"));
                        cachedSQLData.setStatsDeaths(resultSet.getInt("stats_deaths"));
                    }
                } catch (SQLException throwables) {
                    exceptionHandler.handleUnexpected(throwables);
                }
            }
        }
    }

    private void insertPlayer(final @NotNull Player player) {
        if (isConnected()) {
            executeUpdateSync(String.format(
                    "INSERT INTO {name} (player_uuid, player_name) " +
                            "VALUES ('%1$s', '%2$s');", player.getUniqueId().toString(), player.getName()));
        }
    }

    public enum StatsResetState {
        SUCCEED,
        UNKNOWN_PLAYER,
        ERROR;
    }
}
