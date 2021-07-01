/*
 * Copyright (c) 2021 SkillCode
 *
 * This file is a part of the source code of the
 * AdvancedMLGRush plugin by SkillCode.
 *
 * This file may only be used in compliance with the
 * LICENSE.txt (https://github.com/SkillC0de/AdvancedMLGRush/blob/master/LICENSE.txt).
 *
 * Support: https://discord.skillplugins.com
 */

package net.skillcode.advancedmlgrush.game.scoreboard;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.config.configs.ScoreboardConfig;
import net.skillcode.advancedmlgrush.event.events.ScoreboardUpdateEvent;
import net.skillcode.advancedmlgrush.game.map.MapInstance;
import net.skillcode.advancedmlgrush.game.map.MapInstanceManager;
import net.skillcode.advancedmlgrush.libs.fastboard.FastBoard;
import net.skillcode.advancedmlgrush.miscellaneous.registrable.Registrable;
import net.skillcode.advancedmlgrush.placeholder.Placeholders;
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

    @Inject
    private ScoreboardConfig scoreboardConfig;
    @Inject
    private Placeholders placeholders;
    @Inject
    private MapInstanceManager mapInstanceManager;

    private final Map<Player, FastBoard> map = new ConcurrentHashMap<>();

    @Override
    public void unregister(final @NotNull Player player) {
        map.remove(player);
    }

    public void updateScoreboard() {
        Bukkit.getOnlinePlayers().forEach(this::updateScoreboard);
    }

    public void updateScoreboard(final @NotNull List<Player> players) {
        players.forEach(this::updateScoreboard);
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
                case M1x1:
                    content.addAll(scoreboardConfig.getArrayList(ScoreboardConfig.MAP_1X1_SCOREBOARD));
                    break;
                case M1x4:
                    content.addAll(scoreboardConfig.getArrayList(ScoreboardConfig.MAP_1X4_SCOREBOARD));
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
