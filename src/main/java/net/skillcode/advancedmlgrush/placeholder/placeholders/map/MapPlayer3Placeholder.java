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

package net.skillcode.advancedmlgrush.placeholder.placeholders.map;

import com.google.inject.Inject;
import net.skillcode.advancedmlgrush.game.map.MapInstance;
import net.skillcode.advancedmlgrush.game.map.MapInstanceManager;
import net.skillcode.advancedmlgrush.game.map.MapType;
import net.skillcode.advancedmlgrush.placeholder.Placeholder;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class MapPlayer3Placeholder extends Placeholder {

    private final MapInstanceManager mapInstanceManager;

    @Inject
    public MapPlayer3Placeholder(final @NotNull MapInstanceManager mapInstanceManager) {
        this.mapInstanceManager = mapInstanceManager;
    }

    @Override
    public String identifier() {
        return "%map_player_3%";
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
        if (mapInstance.getMapData().getMapType() == MapType.ONE_X_FOUR) {
            return Optional.ofNullable(mapInstance.getPlayers().get(2)).map(HumanEntity::getName).orElse(getNullValue());
        }
        return getNullValue();
    }
}
