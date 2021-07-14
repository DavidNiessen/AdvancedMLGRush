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

package com.skillplugins.advancedmlgrush.game.map;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.config.configs.MessageConfig;
import com.skillplugins.advancedmlgrush.game.GameState;
import com.skillplugins.advancedmlgrush.game.GameStateManager;
import com.skillplugins.advancedmlgrush.game.map.world.MapWorldGenerator;
import com.skillplugins.advancedmlgrush.game.scoreboard.ScoreboardManager;
import com.skillplugins.advancedmlgrush.miscellaneous.registrable.Registrable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class MapInstanceManager implements Registrable {

    private final Map<Player, MapInstance> mapInstanceMap = new ConcurrentHashMap<>();
    @Inject
    private MapInstanceFactory mapInstanceFactory;
    @Inject
    private MapWorldGenerator mapWorldGenerator;
    @Inject
    private MessageConfig messageConfig;
    @Inject
    private ScoreboardManager scoreboardManager;
    @Inject
    private GameStateManager gameStateManager;

    public Optional<MapInstance> createInstance(final List<Player> players, final @NotNull MapTemplate mapTemplate, final int rounds) {
        if (players.size() != mapTemplate.getMapData().getMapType().getPlayers()) {
            players.forEach(player -> player.sendMessage(messageConfig.getWithPrefix(Optional.of(player), MessageConfig.ERROR)));
            return Optional.empty();
        } else {
            final BiMap<Player, Integer> biMap = HashBiMap.create();
            for (int i = 0; i < players.size(); i++) {
                biMap.put(players.get(i), i);
            }
            final MapInstance mapInstance = mapInstanceFactory.create(mapTemplate, mapTemplate.getMapData(), biMap, rounds);
            players.forEach(player -> {
                mapInstanceMap.put(player, mapInstance);
                gameStateManager.setGameState(player, GameState.INGAME);
            });
            scoreboardManager.updateScoreboard();

            return Optional.of(mapInstance);
        }
    }

    public Collection<MapInstance> getMapInstances() {
        return mapInstanceMap.values();
    }

    public boolean isIngame(final @NotNull Player player) {
        return mapInstanceMap.containsKey(player);
    }

    public Optional<MapInstance> getMapInstance(final @NotNull Player player) {
        return Optional.ofNullable(mapInstanceMap.getOrDefault(player, null));
    }

    @Override
    public void unregister(final @NotNull Player player) {
        mapInstanceMap.remove(player);
        scoreboardManager.updateScoreboard();
    }
}
