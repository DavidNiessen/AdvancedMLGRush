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

package net.skillcode.advancedmlgrush.game.spawn;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.skillcode.advancedmlgrush.util.json.JsonConfig;

@Getter
@AllArgsConstructor
public class SpawnFile implements JsonConfig {

    private String worldName;

    private double x;
    private double y;
    private double z;

    private float yaw;
    private float pitch;

}
