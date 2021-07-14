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

package com.skillplugins.advancedmlgrush.placeholder.placeholders.stats.wins;

import com.google.inject.Inject;
import com.skillplugins.advancedmlgrush.game.stats.Ranking;
import com.skillplugins.advancedmlgrush.placeholder.Placeholder;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class Wins9Placeholder extends Placeholder {

    private final Ranking ranking;

    @Inject
    public Wins9Placeholder(final @NotNull Ranking ranking) {
        this.ranking = ranking;
    }

    @Override
    public String identifier() {
        return "%stats_wins_9%";
    }

    @Override
    public String onRequest(final @NotNull Optional<Player> optionalPlayer) {
        return String.valueOf(ranking.getWinsByRanking(9));
    }
}
