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

package com.skillplugins.advancedmlgrush.game.challenge;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.config.configs.MessageConfig;
import com.skillplugins.advancedmlgrush.game.map.MapInstanceManager;
import com.skillplugins.advancedmlgrush.game.map.MapManager;
import com.skillplugins.advancedmlgrush.game.map.MapTemplate;
import com.skillplugins.advancedmlgrush.item.items.LobbyItems;
import com.skillplugins.advancedmlgrush.miscellaneous.registrable.Registrable;
import com.skillplugins.advancedmlgrush.sql.data.SQLDataCache;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class ChallengeManager implements Registrable {

    //<challenger, challenged>
    private final Map<Player, List<Player>> challengeMap = new ConcurrentHashMap<>();
    @Inject
    private MessageConfig messageConfig;
    @Inject
    private MapManager mapManager;
    @Inject
    private SQLDataCache sqlDataCache;
    @Inject
    private LobbyItems lobbyItems;
    @Inject
    private MapInstanceManager mapInstanceManager;

    public void challengePlayer(final @NotNull Player challenger, final @NotNull Player challenged) {
        if (mapInstanceManager.isIngame(challenger)) {
            challenger.sendMessage(messageConfig.getWithPrefix(Optional.of(challenger), MessageConfig.CANNOT_CHALLENGE_PLAYERS));
            return;
        }

        final Set<Map.Entry<Player, List<Player>>> entries = challengeMap.entrySet();
        for (final Map.Entry<Player, List<Player>> entry : entries) {
            final List<Player> list = entry.getValue();
            if (list.contains(challenger)) {
                challenger.getInventory().clear();
                challenged.getInventory().clear();

                unregister(challenger);
                unregister(challenged);

                final Optional<MapTemplate> optional = mapManager.getPlayerMap(challenged);
                final int rounds = sqlDataCache.getSQLData(challenged).getSettingsRounds();

                if (!optional.isPresent()) {
                    challenger.sendMessage(messageConfig.getWithPrefix(Optional.of(challenger), MessageConfig.ERROR));
                    challenged.sendMessage(messageConfig.getWithPrefix(Optional.of(challenged), MessageConfig.ERROR));

                    lobbyItems.setLobbyItems(challenger);
                    lobbyItems.setLobbyItems(challenged);
                } else {
                    optional.get().createInstance(Arrays.asList(challenged, challenger), rounds);
                }
                return;
            }
        }

        if (!challengeMap.containsKey(challenger)) {
            challengeMap.put(challenger, new ArrayList<>());
        }

        final List<Player> challengedPlayers = challengeMap.get(challenger);

        final Optional<Player> optionalChallenger = Optional.of(challenger);
        final Optional<Player> optionalChallenged = Optional.of(challenged);

        if (challengedPlayers.contains(challenged)) {
            challenger.sendMessage(messageConfig.getWithPrefix(Optional.of(challenged), MessageConfig.ALREADY_CHALLENGED));
            return;
        }

        challenger.sendMessage(messageConfig
                .getWithPrefix(optionalChallenged, MessageConfig.CHALLENGE_2));
        challenged.sendMessage(messageConfig
                .getWithPrefix(optionalChallenger, MessageConfig.CHALLENGE_1));
        challengedPlayers.add(challenged);
    }

    @Override
    public void unregister(final @NotNull Player player) {
        challengeMap.remove(player);

        challengeMap.values().forEach(list -> list.remove(player));
    }
}
