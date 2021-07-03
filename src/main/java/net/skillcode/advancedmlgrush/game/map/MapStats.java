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

package net.skillcode.advancedmlgrush.game.map;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MapStats {

    private final List<Integer> scores = new CopyOnWriteArrayList<>(Arrays.asList(0, 0, 0, 0));

    public void increaseScore(final int index) {
        if (index < scores.size()) {
            scores.set(index, scores.get(index) + 1);
        }
    }

    public int getScore(final int index) {
        return index < scores.size() ? scores.get(index) : -1;
    }
}
