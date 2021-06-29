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

package net.skillcode.advancedmlgrush.game.map.setup;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.game.map.MapType;
import net.skillcode.advancedmlgrush.game.map.file.MapFile;
import net.skillcode.advancedmlgrush.game.map.schematic.BlockStorage;
import net.skillcode.advancedmlgrush.game.map.setup.step.SetupSteps;
import net.skillcode.advancedmlgrush.game.map.setup.step.SetupSteps1x1;
import net.skillcode.advancedmlgrush.util.BlockUtils;
import net.skillcode.advancedmlgrush.util.LocationWrapper;
import net.skillcode.advancedmlgrush.util.Pair;
import net.skillcode.advancedmlgrush.util.json.JsonLocation;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Singleton
public class MapSetup1x1 extends MapSetup {

    @Inject
    private SetupSteps1x1 setupSteps1x1;
    @Inject
    private BlockUtils blockUtils;
    @Inject
    private BlockStorage blockStorage;
    @Inject
    private LocationWrapper locationWrapper;

    @Override
    SetupSteps setupSteps() {
        return setupSteps1x1;
    }

    @Override
    Optional<MapFile> createMapFile(final @NotNull List<Object> objects, final @NotNull String mapName) {
        if (objects.size() == 10) {
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
            final List<Location> playerSpawns = new ArrayList<>(Arrays.asList(spawnPlayer1, spawnPlayer2));

            final Pair<Location, Location> bedPlayer1 = (Pair<Location, Location>) objects.get(8);
            final Pair<Location, Location> bedPlayer2 = (Pair<Location, Location>) objects.get(9);
            final List<Pair<Location, Location>> playerBeds = new ArrayList<>(Arrays.asList(bedPlayer1, bedPlayer2));

            final Optional<String> blocks = blockStorage.toString(blockUtils.getBlocksBetweenLocations(firstCorner, secondCorner));
            final JsonLocation jSpectatorSpawn = locationWrapper.toJsonLocation(spectatorSpawn);
            final List<JsonLocation> jPlayerSpawns = new ArrayList<>(locationWrapper.toJsonList(playerSpawns));
            final List<Pair<JsonLocation, JsonLocation>> jPlayerBeds = new ArrayList<>(locationWrapper.toJsonPairList(playerBeds));

            if (!blocks.isPresent()) {
                return Optional.empty();
            }

            return Optional.of(new MapFile(MapType.ONE_X_ONE, mapName, icon, data, blocks.get(), maxBuildHeight, deathHeight,
                    jSpectatorSpawn, jPlayerSpawns, jPlayerBeds));
        }
        return Optional.empty();
    }
}
