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

package net.skillcode.advancedmlgrush.game.challenge;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.config.configs.MessageConfig;
import net.skillcode.advancedmlgrush.game.map.MapManager;
import net.skillcode.advancedmlgrush.game.map.MapTemplate;
import net.skillcode.advancedmlgrush.miscellaneous.registrable.Registrable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class ChallengeManager implements Registrable {

    private final MessageConfig messageConfig;
    private final MapManager mapManager;

    //<challenger, challenged>
    private final Map<Player, List<Player>> challengeMap = new ConcurrentHashMap<>();

    @Inject
    public ChallengeManager(final @NotNull MessageConfig messageConfig,
                            final @NotNull MapManager mapManager) {
        this.messageConfig = messageConfig;
        this.mapManager = mapManager;
    }

    public void challengePlayer(final @NotNull Player challenger, final @NotNull Player challenged) {
        final Set<Map.Entry<Player, List<Player>>> entries = challengeMap.entrySet();
        for (final Map.Entry<Player, List<Player>> entry : entries) {
            final List<Player> list = entry.getValue();
            if (list.contains(challenger)) {
                challenger.getInventory().clear();
                challenged.getInventory().clear();

                unregister(challenger);
                unregister(challenged);

                final Optional<MapTemplate> optional = mapManager.getPlayerMap(challenger);
                if (!optional.isPresent()) {
                    challenger.sendMessage(messageConfig.getWithPrefix(Optional.of(challenger), MessageConfig.ERROR));
                    challenged.sendMessage(messageConfig.getWithPrefix(Optional.of(challenged), MessageConfig.ERROR));
                } else {
                    optional.get().createInstance(new ArrayList<>(Arrays.asList(challenger, challenged)));
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
