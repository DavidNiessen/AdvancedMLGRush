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

package com.skillplugins.advancedmlgrush.game.map.setup;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.config.configs.MainConfig;
import com.skillplugins.advancedmlgrush.game.map.MapType;
import com.skillplugins.advancedmlgrush.game.map.file.MapFile;
import com.skillplugins.advancedmlgrush.game.map.schematic.BlockWrapper;
import com.skillplugins.advancedmlgrush.game.map.setup.step.SetupSteps;
import com.skillplugins.advancedmlgrush.game.map.setup.step.SetupSteps4x1;
import com.skillplugins.advancedmlgrush.util.BlockUtils;
import com.skillplugins.advancedmlgrush.util.LocationWrapper;
import com.skillplugins.advancedmlgrush.util.Pair;
import com.skillplugins.advancedmlgrush.util.json.JsonLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@Singleton
public class MapSetup4x1 extends MapSetup {

    @Inject
    private SetupSteps4x1 setupSteps4X1;
    @Inject
    private BlockUtils blockUtils;
    @Inject
    private BlockWrapper blockWrapper;
    @Inject
    private LocationWrapper locationWrapper;
    @Inject
    private MainConfig mainConfig;

    @Override
    SetupSteps setupSteps() {
        return setupSteps4X1;
    }

    @Override
    Optional<MapFile> createMapFile(final @NotNull List<Object> objects, final @NotNull String mapName) {
        if (objects.size() == 14) {
            final String serializer = mainConfig.getString(MainConfig.DEFAULT_SERIALIZER);
            final String compressor = mainConfig.getString(MainConfig.DEFAULT_COMPRESSOR);

            final Pair<String, Integer> pair = (Pair<String, Integer>) objects.get(0);
            final String icon = pair.getKey();
            final int data = pair.getValue();

            final Location firstCorner = (Location) objects.get(1);
            final Location secondCorner = (Location) objects.get(2);

            final int maxBuildHeight = (Integer) objects.get(3);
            final int deathHeight = (Integer) objects.get(4);

            final Location spectatorSpawn = (Location) objects.get(5);

            final Location spawnPlayer1 = (Location) objects.get(6);
            final Location spawnPlayer2 = (Location) objects.get(7);
            final Location spawnPlayer3 = (Location) objects.get(8);
            final Location spawnPlayer4 = (Location) objects.get(9);
            final List<Location> playerSpawns = new ArrayList<>(Arrays.asList(spawnPlayer1, spawnPlayer2, spawnPlayer3, spawnPlayer4));

            final Pair<Location, Location> bedPlayer1 = (Pair<Location, Location>) objects.get(10);
            final Pair<Location, Location> bedPlayer2 = (Pair<Location, Location>) objects.get(11);
            final Pair<Location, Location> bedPlayer3 = (Pair<Location, Location>) objects.get(12);
            final Pair<Location, Location> bedPlayer4 = (Pair<Location, Location>) objects.get(13);
            final List<Pair<Location, Location>> playerBeds = new ArrayList<>(Arrays.asList(bedPlayer1, bedPlayer2, bedPlayer3, bedPlayer4));


            final Optional<String> blocks = blockWrapper.toString(blockUtils.getBlocksBetweenLocations(firstCorner, secondCorner), serializer, compressor);
            final JsonLocation jSpectatorSpawn = locationWrapper.toJsonLocation(spectatorSpawn);
            final List<JsonLocation> jPlayerSpawns = new ArrayList<>(locationWrapper.toJsonList(playerSpawns));
            final List<Pair<JsonLocation, JsonLocation>> jPlayerBeds = new ArrayList<>(locationWrapper.toJsonPairList(playerBeds));

            if (!blocks.isPresent()) {
                return Optional.empty();
            }

            return Optional.of(new MapFile(MapType.M4x1, mapName, Bukkit.getBukkitVersion(), icon, data,
                    Collections.singletonList(""), serializer, compressor, blocks.get(), maxBuildHeight, deathHeight,
                    jSpectatorSpawn, jPlayerSpawns, jPlayerBeds));
        }
        return Optional.empty();
    }

}
