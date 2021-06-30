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

package net.skillcode.advancedmlgrush.game.queue;

import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.game.map.MapType;

@Singleton
public class Queue1x1 extends Queue {

    @Override
    protected int playerAmount() {
        return 2;
    }

    @Override
    MapType mapType() {
        return MapType.ONE_X_ONE;
    }
}
