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

import net.skillcode.advancedmlgrush.game.map.file.MapFile;
import org.jetbrains.annotations.NotNull;

public interface MapFactory {

    Map create(final @NotNull MapData mapData,
               final @NotNull MapFile mapFile);

}