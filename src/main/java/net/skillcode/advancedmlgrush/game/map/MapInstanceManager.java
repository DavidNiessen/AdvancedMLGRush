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

package net.skillcode.advancedmlgrush.game.map;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.miscellaneous.registrable.Registrable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class MapInstanceManager implements Registrable {

    @Inject
    private MapInstanceFactory mapInstanceFactory;

    private final Map<Player, MapInstance> mapInstanceMap = new ConcurrentHashMap<>();

    public void createInstance(final List<Player> players, final @NotNull MapTemplate mapTemplate) {
        final MapInstance mapInstance = mapInstanceFactory.create(mapTemplate, mapTemplate.getMapData(), players);
        players.forEach(player -> mapInstanceMap.put(player, mapInstance));
    }

    @Override
    public void unregister(final @NotNull Player player) {
        mapInstanceMap.remove(player);
    }
}
