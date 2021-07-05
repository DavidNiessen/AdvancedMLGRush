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

package net.skillcode.advancedmlgrush.game.queue;

import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.game.map.MapType;

@Singleton
public class Queue4x1 extends Queue {

    @Override
    MapType mapType() {
        return MapType.M4x1;
    }
}
