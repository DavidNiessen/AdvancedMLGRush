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
import net.skillcode.advancedmlgrush.game.stats.Ranking;
import net.skillcode.advancedmlgrush.placeholder.Placeholder;
import net.skillcode.advancedmlgrush.sql.data.SQLDataCache;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class RankingPlaceholder extends Placeholder {

    private final SQLDataCache sqlDataCache;
    private final Ranking ranking;

    @Inject
    public RankingPlaceholder(final @NotNull SQLDataCache sqlDataCache, final @NotNull Ranking ranking) {
        this.sqlDataCache = sqlDataCache;
        this.ranking = ranking;
    }

    @Override
    public String identifier() {
        return "%ranking%";
    }

    @Override
    public String onRequest(final @NotNull Optional<Player> optionalPlayer) {
        if (!optionalPlayer.isPresent()) {
            return getNullValue();
        }
        final Player player = optionalPlayer.get();

        if (!sqlDataCache.isLoaded(player)) {
            return getLoadingValue();
        }
        return String.valueOf(ranking.getRankingByName(player));
    }

}
