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

package net.skillcode.advancedmlgrush.placeholder.placeholders.ranking;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.game.stats.Ranking;
import net.skillcode.advancedmlgrush.placeholder.Placeholder;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Singleton
public class Ranking6Placeholder extends Placeholder {

    private final Ranking ranking;

    @Inject
    public Ranking6Placeholder(final @NotNull Ranking ranking) {
        this.ranking = ranking;
    }

    @Override
    public String identifier() {
        return "%stats_ranking_6%";
    }

    @Override
    public String onRequest(final @NotNull Optional<Player> optionalPlayer) {
        return ranking.getPlayerByRanking(6).orElse(getNullValue());
    }
}
