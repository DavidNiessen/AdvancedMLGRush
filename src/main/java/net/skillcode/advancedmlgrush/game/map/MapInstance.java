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
import com.google.inject.assistedinject.Assisted;
import net.skillcode.advancedmlgrush.annotations.PostConstruct;
import net.skillcode.advancedmlgrush.config.configs.MessageConfig;
import net.skillcode.advancedmlgrush.exception.ExceptionHandler;
import net.skillcode.advancedmlgrush.game.map.schematic.SchematicLoader;
import net.skillcode.advancedmlgrush.game.map.world.MapWorldGenerator;
import net.skillcode.advancedmlgrush.util.Pair;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MapInstance {

    private final MapTemplate mapTemplate;
    private final MapData mapData;
    private final List<Player> players;

    private UUID worldUUID;
    private World world;

    @Inject
    private MapWorldGenerator mapWorldGenerator;
    @Inject
    private SchematicLoader schematicLoader;
    @Inject
    private JavaPlugin javaPlugin;
    @Inject
    private MessageConfig messageConfig;
    @Inject
    private ExceptionHandler exceptionHandler;


    @Inject
    public MapInstance(final @Assisted @NotNull MapTemplate mapTemplate,
                       final @Assisted @NotNull MapData mapData,
                       final @Assisted @NotNull List<Player> players) {
        this.mapTemplate = mapTemplate;
        this.mapData = mapData;
        this.players = players;
    }

    @PostConstruct
    public void prepareMap() {
        players.forEach(player -> player.sendMessage(messageConfig.getWithPrefix(Optional.of(player), MessageConfig.MAP_GENERATE)));
        final Pair<World, UUID> pair = mapWorldGenerator.createWorld();

        worldUUID = pair.getValue();
        world = pair.getKey();

        schematicLoader.load(mapData.getBlocks(), world, () -> players.forEach(player -> teleport(player, mapData.getSpawns().get(0))));
    }


    private void teleport(final @NotNull Player player, final @NotNull Location location) {
        player.teleport(new Location(world, location.getX(), location.getY(), location.getZ(),
                location.getYaw(), location.getPitch()));
    }
}
