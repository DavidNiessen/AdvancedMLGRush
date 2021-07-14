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

package com.skillplugins.advancedmlgrush.game.map;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.game.map.file.MapFile;
import com.skillplugins.advancedmlgrush.game.map.schematic.BlockWrapper;
import com.skillplugins.advancedmlgrush.game.map.schematic.StorableBlock;
import com.skillplugins.advancedmlgrush.libs.xseries.XMaterial;
import com.skillplugins.advancedmlgrush.util.EnumUtils;
import com.skillplugins.advancedmlgrush.util.LocationWrapper;
import com.skillplugins.advancedmlgrush.util.Pair;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

@Singleton
public class MapDataWrapper {

    @Inject
    private EnumUtils enumUtils;
    @Inject
    private BlockWrapper blockWrapper;
    @Inject
    private LocationWrapper locationWrapper;

    public Optional<MapData> getMapData(final @NotNull MapFile mapFile) {
        final String iconMaterialName = mapFile.getIcon();
        final XMaterial iconMaterial = enumUtils.isInEnum(XMaterial.class, iconMaterialName)
                ? XMaterial.valueOf(iconMaterialName) : XMaterial.STONE;

        final int data = mapFile.getIconData();

        final List<String> description = mapFile.getDescription();

        final Optional<List<StorableBlock>> blocks = blockWrapper.toList(mapFile.getBlocks(), mapFile);

        if (!blocks.isPresent()) {
            return Optional.empty();
        }

        final Location spectatorSpawn = locationWrapper.toLocation(mapFile.getSpectatorSpawn());
        final List<Location> spawns = locationWrapper.toList(mapFile.getSpawns());
        final List<Pair<Location, Location>> beds = locationWrapper.toPairList(mapFile.getBeds());

        return Optional.of(new MapData(mapFile.getMapType(), mapFile.getName(), iconMaterial, data,
                description, blocks.get(), mapFile.getMaxBuildHeight(),
                mapFile.getDeathHeight(), spectatorSpawn, spawns, beds));
    }

}
