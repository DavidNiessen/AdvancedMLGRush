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

package com.skillplugins.advancedmlgrush.game.map.file;

import com.skillplugins.advancedmlgrush.game.map.MapType;
import com.skillplugins.advancedmlgrush.util.Pair;
import com.skillplugins.advancedmlgrush.util.json.JsonConfig;
import com.skillplugins.advancedmlgrush.util.json.JsonLocation;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MapFile implements JsonConfig {

    private MapType mapType;
    private String name;
    private String nativeVersion;
    private String icon;
    private int iconData;
    private List<String> description;
    private String serializer;
    private String compressor;
    private String blocks;

    private int maxBuildHeight;
    private int deathHeight;

    private JsonLocation spectatorSpawn;
    private List<JsonLocation> spawns;
    private List<Pair<JsonLocation, JsonLocation>> beds;

}
