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
import net.skillcode.advancedmlgrush.game.map.file.MapFile;
import net.skillcode.advancedmlgrush.game.map.schematic.BlockStorage;
import net.skillcode.advancedmlgrush.game.map.schematic.StorableBlock;
import net.skillcode.advancedmlgrush.miscellaneous.Constants;
import net.skillcode.advancedmlgrush.util.EnumUtils;
import net.skillcode.advancedmlgrush.util.LocationWrapper;
import net.skillcode.advancedmlgrush.util.Pair;
import org.bukkit.Location;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class MapDataWrapper {

    @Inject
    private EnumUtils enumUtils;
    @Inject
    private BlockStorage blockStorage;
    @Inject
    private LocationWrapper locationWrapper;

    public MapData getMapData(final @NotNull MapFile mapFile) {
        final String materialName = mapFile.getIcon();
        final Material material = enumUtils.isInEnum(Material.class, materialName)
                ? Material.valueOf(materialName) : Constants.DEFAULT_MAP_MATERIAL;

        final int data = mapFile.getIconData();

        final List<StorableBlock> blocks = blockStorage.toList(mapFile.getBlocks()).orElse(new ArrayList<>());

        final Location spectatorSpawn = locationWrapper.toLocation(mapFile.getSpectatorSpawn());
        final List<Location> spawns = locationWrapper.toList(mapFile.getSpawns());
        final List<Pair<Location, Location>> beds = locationWrapper.toPairList(mapFile.getBeds());

        return new MapData(mapFile.getMapType(), mapFile.getName(), material, data, blocks, mapFile.getMaxBuildHeight(),
                mapFile.getDeathHeight(), spectatorSpawn, spawns, beds);
    }

}
