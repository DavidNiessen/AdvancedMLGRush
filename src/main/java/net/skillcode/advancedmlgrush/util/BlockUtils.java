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

package net.skillcode.advancedmlgrush.util;

import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.libs.cuboid.Cuboid;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Singleton
public class BlockUtils {

    public List<Block> getBlocksBetweenLocations(final @NotNull Location location1,
                                                 final @NotNull Location location2) {
         return new Cuboid(location1, location2).blockList(true);
    }

}
