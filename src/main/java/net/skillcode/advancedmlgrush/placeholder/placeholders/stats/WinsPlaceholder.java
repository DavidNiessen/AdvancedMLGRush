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

package net.skillcode.advancedmlgrush.placeholder.placeholders.stats;

import com.google.inject.Inject;
import net.skillcode.advancedmlgrush.placeholder.Placeholder;
import net.skillcode.advancedmlgrush.sql.data.SQLDataCache;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class WinsPlaceholder extends Placeholder {

    private final SQLDataCache sqlDataCache;

    @Inject
    public WinsPlaceholder(final SQLDataCache sqlDataCache) {
        this.sqlDataCache = sqlDataCache;
    }

    @Override
    public String identifier() {
        return "%stats_wins%";
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
        return String.valueOf(sqlDataCache.getSQLData(player).getStatsWins());
    }

}
