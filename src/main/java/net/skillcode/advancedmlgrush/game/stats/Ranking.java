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

package net.skillcode.advancedmlgrush.game.stats;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.MLGRush;
import net.skillcode.advancedmlgrush.annotations.PostConstruct;
import net.skillcode.advancedmlgrush.config.configs.MainConfig;
import net.skillcode.advancedmlgrush.sql.datasavers.MLGDataSaver;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Singleton
public class Ranking {

    @Inject
    private MLGRush mlgRush;
    @Inject
    private MLGDataSaver mlgDataSaver;
    @Inject
    private MainConfig mainConfig;

    //<name, ranking>
    private final BiMap<String, Integer> biMap = HashBiMap.create();

    @PostConstruct
    public void init() {
        startUpdateTimer();
    }


    public void startUpdateTimer() {
        final UUID uuid = mlgRush.getUuid();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!mlgRush.getUuid().equals(uuid)) {
                    cancel();
                } else {
                    mlgDataSaver.updateRanking(map -> updateRanking(map));
                }
            }
        }.runTaskTimer(mlgRush, 0, mainConfig.getLong(MainConfig.RANKING_UPDATE_PERIOND) * 60 * 20);
    }

    public void updateRanking(final @NotNull Map<String, Integer> map) {
        biMap.clear();
        biMap.putAll(map);
    }

    public int getRankingByName(final @NotNull String playerName) {
        return biMap.getOrDefault(playerName, -1);
    }

    public int getRankingByName(final @NotNull Player player) {
        return getRankingByName(player.getName());
    }

    public Optional<String> getPlayerByRanking(final int ranking) {
        return Optional.ofNullable(biMap.inverse().getOrDefault(ranking, null));
    }


}
