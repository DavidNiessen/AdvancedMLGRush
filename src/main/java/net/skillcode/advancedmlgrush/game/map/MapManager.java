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

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import lombok.Getter;
import net.skillcode.advancedmlgrush.game.map.file.MapFileLoader;
import net.skillcode.advancedmlgrush.util.Initializer;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Singleton
public class MapManager implements Initializer {

    @Inject
    private MapFileLoader mapFileLoader;
    @Inject
    private MapFactory mapFactory;
    @Inject
    private MapDataWrapper mapDataWrapper;

    @Getter
    private final List<Map> maps = new CopyOnWriteArrayList<>();

    @Override
    public void init(final @NotNull Injector injector) {
        mapFileLoader.loadFilesIfExists();
        System.out.println("...." + mapFileLoader.getMapFiles().size());
        mapFileLoader.getMapFiles().forEach(mapFile
                -> maps.add(mapFactory.create(mapDataWrapper.getMapData(mapFile), mapFile)));
    }
}
