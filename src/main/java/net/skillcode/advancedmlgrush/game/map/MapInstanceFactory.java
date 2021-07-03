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

import com.google.common.collect.BiMap;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface MapInstanceFactory {

    MapInstance create(final @NotNull MapTemplate mapTemplate,
                       final @NotNull MapData mapData,
                       final @NotNull BiMap<Player, Integer> players,
                       final int rounds);

}
