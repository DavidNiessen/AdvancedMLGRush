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

package net.skillcode.advancedmlgrush.game;

import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.miscellaneous.registrable.Registrable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class GameStateManager implements Registrable {

    private final Map<Player, GameState> gameStateMap = new ConcurrentHashMap<>();

    public GameState getGameState(final @NotNull Player player) {
        return gameStateMap.getOrDefault(player, GameState.UNKNOWN);
    }

    public void setGameState(final @NotNull Player player, final @NotNull GameState gameState) {
        gameStateMap.put(player, gameState);
    }

    @Override
    public void unregister(final @NotNull Player player) {
        gameStateMap.remove(player);
    }
}
