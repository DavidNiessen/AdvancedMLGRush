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
import com.skillplugins.advancedmlgrush.game.map.schematic.BlockConverter;
import com.skillplugins.advancedmlgrush.game.map.schematic.StorableBlock;
import com.skillplugins.advancedmlgrush.libs.xseries.XMaterial;
import com.skillplugins.advancedmlgrush.util.EnumUtils;
import com.skillplugins.advancedmlgrush.util.LocationConverter;
import com.skillplugins.advancedmlgrush.util.Pair;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

@Singleton
public class MapDataConverter {

    @Inject
    private EnumUtils enumUtils;
    @Inject
    private BlockConverter blockConverter;
    @Inject
    private LocationConverter locationConverter;

    public Optional<MapData> getMapData(final @NotNull MapFile mapFile) {
        final String iconMaterialName = mapFile.getIcon();
        final XMaterial iconMaterial = enumUtils.isInEnum(XMaterial.class, iconMaterialName)
                ? XMaterial.valueOf(iconMaterialName) : XMaterial.STONE;

        final int data = mapFile.getIconData();

        final List<String> description = mapFile.getDescription();

        final Optional<List<StorableBlock>> blocks = blockConverter.toList(mapFile.getBlocks(), mapFile);

        if (!blocks.isPresent()) {
            return Optional.empty();
        }

        final Location spectatorSpawn = locationConverter.toLocation(mapFile.getSpectatorSpawn());
        final List<Location> spawns = locationConverter.toList(mapFile.getSpawns());
        final List<Pair<Location, Location>> beds = locationConverter.toPairList(mapFile.getBeds());

        return Optional.of(new MapData(mapFile.getMapType(), mapFile.getName(), iconMaterial, data,
                description, blocks.get(), mapFile.getMaxBuildHeight(),
                mapFile.getDeathHeight(), spectatorSpawn, spawns, beds));
    }

}
