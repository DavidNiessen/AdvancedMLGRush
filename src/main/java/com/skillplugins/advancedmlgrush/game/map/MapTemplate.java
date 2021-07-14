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

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.skillplugins.advancedmlgrush.game.challenge.ChallengeManager;
import com.skillplugins.advancedmlgrush.game.queue.Queue2x1;
import com.skillplugins.advancedmlgrush.game.queue.Queue4x1;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MapTemplate {

    @Getter
    private final MapData mapData;

    @Inject
    private MapInstanceManager mapInstanceManager;
    @Inject
    private ChallengeManager challengeManager;
    @Inject
    private Queue2x1 queue2X1;
    @Inject
    private Queue4x1 queue4X1;

    @Inject
    public MapTemplate(final @Assisted @NotNull MapData mapData) {
        this.mapData = mapData;
    }

    public void createInstance(final @NotNull List<Player> players, final int rounds) {
        players.forEach(player -> {
            challengeManager.unregister(player);
            queue2X1.unregister(player);
            queue4X1.unregister(player);
            player.getInventory().clear();
        });
        mapInstanceManager.createInstance(players, this, rounds);
    }
}
