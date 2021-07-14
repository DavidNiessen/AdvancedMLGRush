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

import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.miscellaneous.Constants;
import com.skillplugins.advancedmlgrush.util.json.utils.JsonUtils;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;

@Singleton
public class MapFileLoader {

    @Getter
    private final List<MapFile> mapFiles = new CopyOnWriteArrayList<>();

    public void loadFilesIfExists() {
        mapFiles.clear();
        final File directory = new File(Constants.MAP_PATH);
        if (directory.exists()) {
            for (final File file : directory.listFiles()) {
                final MapFile mapFile = JsonUtils.getFromFile(MapFile.class, file);
                if (mapFile != null) {
                    mapFiles.add(mapFile);
                }
            }
        }
    }

    public void createMapFile(final @NotNull MapFile mapFile) {
        final String filePath = Constants.MAP_PATH + mapFile.getName().toLowerCase(Locale.ROOT).replace(" ", "_") + ".json";
        final File file = new File(filePath);

        if (!file.exists()) {
            JsonUtils.toFile(mapFile, filePath, true);
        }
    }
}
