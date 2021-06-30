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

package net.skillcode.advancedmlgrush.game.map;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.config.configs.MessageConfig;
import net.skillcode.advancedmlgrush.game.map.world.MapWorldGenerator;
import net.skillcode.advancedmlgrush.miscellaneous.registrable.Registrable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Singleton
public class MapInstanceManager implements Registrable {

    @Inject
    private MapInstanceFactory mapInstanceFactory;
    @Inject
    private MapWorldGenerator mapWorldGenerator;
    @Inject
    private MessageConfig messageConfig;

    private final Map<Player, MapInstance> mapInstanceMap = new ConcurrentHashMap<>();

    public Optional<MapInstance> createInstance(final List<Player> players, final @NotNull MapTemplate mapTemplate, final int rounds) {
        if (players.size() != mapTemplate.getMapData().getMapType().getPlayers()) {
            players.forEach(player -> player.sendMessage(messageConfig.getWithPrefix(Optional.of(player), MessageConfig.ERROR)));
            return Optional.empty();
        } else {
            final MapInstance mapInstance = mapInstanceFactory.create(mapTemplate, mapTemplate.getMapData(), new CopyOnWriteArrayList<>(players), rounds);
            players.forEach(player -> mapInstanceMap.put(player, mapInstance));
            return Optional.of(mapInstance);
        }
    }

    public Collection<MapInstance> getMapInstances() {
        return mapInstanceMap.values();
    }

    public Optional<MapInstance> getMapInstance(final @NotNull Player player) {
        return Optional.ofNullable(mapInstanceMap.getOrDefault(player, null));
    }

    @Override
    public void unregister(final @NotNull Player player) {
        mapInstanceMap.remove(player);
    }

    public void onDisable() {
        mapInstanceMap.values().forEach(mapInstance -> mapWorldGenerator.deleteWorld(mapInstance.getWorld()));
    }
}
