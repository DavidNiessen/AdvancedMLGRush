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

package com.skillplugins.advancedmlgrush.placeholder.placeholders.map;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.game.map.MapInstance;
import com.skillplugins.advancedmlgrush.game.map.MapInstanceManager;
import com.skillplugins.advancedmlgrush.placeholder.Placeholder;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Singleton
public class MapRoundsPlaceholder extends Placeholder {

    private final MapInstanceManager mapInstanceManager;

    @Inject
    public MapRoundsPlaceholder(final @NotNull MapInstanceManager mapInstanceManager) {
        this.mapInstanceManager = mapInstanceManager;
    }

    @Override
    public String identifier() {
        return "%map_rounds%";
    }

    @Override
    public String onRequest(final @NotNull Optional<Player> optionalPlayer) {
        if (!optionalPlayer.isPresent()) {
            return getNullValue();
        }
        final Player player = optionalPlayer.get();
        final Optional<MapInstance> mapInstanceOptional = mapInstanceManager.getMapInstance(player);

        if (!mapInstanceOptional.isPresent()) {
            return getNullValue();
        }

        final MapInstance mapInstance = mapInstanceOptional.get();
        return String.valueOf(mapInstance.getRounds());
    }
}
