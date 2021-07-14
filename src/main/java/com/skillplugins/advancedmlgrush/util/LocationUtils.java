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

package com.skillplugins.advancedmlgrush.util;

import com.google.inject.Singleton;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

@Singleton
public class LocationUtils {

    public boolean compare(final @NotNull Location location1, final @NotNull Location location2, final @NotNull World world) {
        location1.setWorld(world);
        location2.setWorld(world);

        final Location loc1 = location1.getBlock().getLocation();
        final Location loc2 = location2.getBlock().getLocation();

        return loc1.getX() == loc2.getX()
                && loc1.getY() == loc2.getY()
                && loc1.getZ() == loc2.getZ();
    }
}
