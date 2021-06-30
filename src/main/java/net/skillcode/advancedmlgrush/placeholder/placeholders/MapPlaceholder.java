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

package net.skillcode.advancedmlgrush.placeholder.placeholders;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.game.map.MapManager;
import net.skillcode.advancedmlgrush.game.map.MapTemplate;
import net.skillcode.advancedmlgrush.placeholder.Placeholder;
import net.skillcode.advancedmlgrush.sql.data.SQLDataCache;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Singleton
public class MapPlaceholder extends Placeholder {

    private final SQLDataCache sqlDataCache;
    private final MapManager mapManager;

    @Inject
    public MapPlaceholder(final @NotNull SQLDataCache sqlDataCache,
                          final @NotNull MapManager mapManager) {
        this.sqlDataCache = sqlDataCache;
        this.mapManager = mapManager;
    }

    @Override
    public String identifier() {
        return "%settings_map%";
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
        final Optional<MapTemplate> optionalMapTemplate = mapManager.getPlayerMap(player);

        if (!optionalMapTemplate.isPresent()) {
            return getNullValue();
        }

        return optionalMapTemplate.get().getMapData().getName();
    }
}
