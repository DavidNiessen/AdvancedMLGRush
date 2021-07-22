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

package com.skillplugins.advancedmlgrush.game.scoreboard;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.config.configs.ScoreboardConfig;
import com.skillplugins.advancedmlgrush.event.events.ScoreboardUpdateEvent;
import com.skillplugins.advancedmlgrush.game.map.MapInstance;
import com.skillplugins.advancedmlgrush.game.map.MapInstanceManager;
import com.skillplugins.advancedmlgrush.lib.fastboard.FastBoard;
import com.skillplugins.advancedmlgrush.miscellaneous.registrable.Registrable;
import com.skillplugins.advancedmlgrush.placeholder.Placeholders;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class ScoreboardManager implements Registrable {

    private final Map<Player, FastBoard> map = new ConcurrentHashMap<>();

    @Inject
    private ScoreboardConfig scoreboardConfig;
    @Inject
    private Placeholders placeholders;
    @Inject
    private MapInstanceManager mapInstanceManager;

    @Override
    public void unregister(final @NotNull Player player) {
        map.remove(player);
    }

    public void updateScoreboard() {
        Bukkit.getOnlinePlayers().forEach(this::updateScoreboard);
    }

    public void updateScoreboard(final @NotNull Iterable<Player> iterable) {
        iterable.forEach(this::updateScoreboard);
    }

    public void updateScoreboard(final @NotNull Player player) {
        if (!map.containsKey(player)) {
            map.put(player, new FastBoard(player));
        }

        final FastBoard scoreboard = map.get(player);
        final Optional<Player> optionalPlayer = Optional.of(player);
        final Optional<MapInstance> optionalMapInstance = mapInstanceManager.getMapInstance(player);
        final List<String> content = new ArrayList<>();

        if (optionalMapInstance.isPresent()) {
            final MapInstance mapInstance = optionalMapInstance.get();
            switch (mapInstance.getMapData().getMapType()) {
                case M2x1:
                    content.addAll(scoreboardConfig.getArrayList(ScoreboardConfig.MAP_2X1_SCOREBOARD));
                    break;
                case M4x1:
                    content.addAll(scoreboardConfig.getArrayList(ScoreboardConfig.MAP_4X1_SCOREBOARD));
                    break;
            }
        } else {
            content.addAll(scoreboardConfig.getArrayList(ScoreboardConfig.LOBBY_SCOREBOARD));
        }
        placeholders.replace(optionalPlayer, content);

        final ScoreboardUpdateEvent scoreboardUpdateEvent = new ScoreboardUpdateEvent(player, content);
        Bukkit.getPluginManager().callEvent(scoreboardUpdateEvent);

        if (!scoreboardUpdateEvent.isCancelled()) {
            scoreboard.updateTitle(scoreboardConfig.getString(optionalPlayer, ScoreboardConfig.SCOREBOARD_TITLE));
            scoreboard.updateLines(scoreboardUpdateEvent.getScoreboardContent());
        }
    }

}
