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

package net.skillcode.advancedmlgrush.util;

import net.skillcode.advancedmlgrush.miscellaneous.Constants;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WorldUtils {

    private WorldUtils() {
    }

    public static void deleteWorldFiles(final @NotNull File path) {
        if (path.exists()
                && path.isDirectory()) {
            final File[] files = path.listFiles();
            for (final File file : files) {
                if (file.isDirectory()) {
                    deleteWorldFiles(file);
                } else {
                    file.delete();
                }
            }
        }
        path.delete();
    }

    public static void deleteWorlds() {
        final File path = new File(Constants.WORLD_PATH);
        if (path.exists()
                && path.isDirectory()) {
            final List<File> files = new ArrayList<>(Arrays.asList(path.listFiles()));
            final List<String> fileNames = new ArrayList<>();
            files.forEach(file -> fileNames.add(file.getName()));

            Bukkit.getWorlds().forEach(world -> {
                if (fileNames.contains(world.getWorldFolder().getName())) {
                    Bukkit.unloadWorld(world, false);
                    Bukkit.getWorlds().remove(world);
                }
            });
            for (final File file : files) {
                deleteWorldFiles(file);
            }
        }
    }

}
