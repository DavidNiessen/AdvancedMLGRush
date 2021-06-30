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

package net.skillcode.advancedmlgrush.game.map.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.skillcode.advancedmlgrush.game.map.MapType;
import net.skillcode.advancedmlgrush.util.Pair;
import net.skillcode.advancedmlgrush.util.json.JsonConfig;
import net.skillcode.advancedmlgrush.util.json.JsonLocation;

import java.util.List;

@Getter
@AllArgsConstructor
public class MapFile implements JsonConfig {

    private MapType mapType;
    private String name;
    private String icon;
    private int iconData;
    private String blocks;

    private int maxBuildHeight;
    private int deathHeight;

    private JsonLocation spectatorSpawn;
    private List<JsonLocation> spawns;
    private List<Pair<JsonLocation, JsonLocation>> beds;

}
