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

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.skillcode.advancedmlgrush.game.map.schematic.StorableBlock;
import net.skillcode.advancedmlgrush.util.Pair;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.List;

@Getter
@AllArgsConstructor
public class MapData {

    private final MapType mapType;
    private final String name;
    private final Material icon;
    private final int iconData;
    private final List<StorableBlock> blocks;

    private final int maxBuild;
    private final int deathHeight;

    private final Location spectatorSpawn;
    private final List<Location> spawns;
    private final List<Pair<Location, Location>> beds;

}
