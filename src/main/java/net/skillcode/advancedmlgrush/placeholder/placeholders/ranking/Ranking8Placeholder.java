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

package net.skillcode.advancedmlgrush.placeholder.placeholders.ranking;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.game.stats.Ranking;
import net.skillcode.advancedmlgrush.placeholder.Placeholder;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Singleton
public class Ranking8Placeholder extends Placeholder {

    private final Ranking ranking;

    @Inject
    public Ranking8Placeholder(final @NotNull Ranking ranking) {
        this.ranking = ranking;
    }

    @Override
    public String identifier() {
        return "%stats_ranking_8%";
    }

    @Override
    public String onRequest(final @NotNull Optional<Player> optionalPlayer) {
        return ranking.getPlayerByRanking(8).orElse(getNullValue());
    }
}
