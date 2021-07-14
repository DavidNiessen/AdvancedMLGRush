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

import com.skillplugins.advancedmlgrush.game.map.schematic.StorableBlock;
import com.skillplugins.advancedmlgrush.libs.xseries.XMaterial;
import com.skillplugins.advancedmlgrush.util.Pair;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;

import java.util.List;

@Getter
@AllArgsConstructor
public class MapData {

    private final MapType mapType;
    private final String name;
    private final XMaterial icon;
    private final int iconData;
    private final List<String> description;
    private final List<StorableBlock> blocks;

    private final int maxBuild;
    private final int deathHeight;

    private final Location spectatorSpawn;
    private final List<Location> spawns;
    private final List<Pair<Location, Location>> beds;

}
