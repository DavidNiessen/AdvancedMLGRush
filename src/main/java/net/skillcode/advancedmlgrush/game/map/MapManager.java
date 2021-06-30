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

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import lombok.Getter;
import net.skillcode.advancedmlgrush.game.map.file.MapFileLoader;
import net.skillcode.advancedmlgrush.sql.data.CachedSQLData;
import net.skillcode.advancedmlgrush.sql.data.SQLDataCache;
import net.skillcode.advancedmlgrush.util.Initializer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

@Singleton
public class MapManager implements Initializer {

    @Inject
    private MapFileLoader mapFileLoader;
    @Inject
    private MapTemplateFactory mapTemplateFactory;
    @Inject
    private MapDataWrapper mapDataWrapper;
    @Inject
    private SQLDataCache sqlDataCache;

    @Getter
    private final List<MapTemplate> mapTemplates1x1 = new CopyOnWriteArrayList<>();
    @Getter
    private final List<MapTemplate> mapTemplates1x4 = new CopyOnWriteArrayList<>();

    @Override
    public void init(final @NotNull Injector injector) {
        mapFileLoader.loadFilesIfExists();
        mapFileLoader.getMapFiles().forEach(mapFile -> {
            final MapTemplate mapTemplate = mapTemplateFactory.create(mapDataWrapper.getMapData(mapFile));
            switch (mapTemplate.getMapData().getMapType()) {
                case ONE_X_ONE:
                    mapTemplates1x1.add(mapTemplate);
                    break;
                case ONE_X_FOUR:
                    mapTemplates1x4.add(mapTemplate);
                    break;
            }
        });
    }

    public Optional<MapTemplate> getRandomMapTemplate(final @NotNull MapType mapType) {
        final Random random = new Random();
        switch (mapType) {
            case ONE_X_ONE:
                return Optional.of(mapTemplates1x1.get(random.nextInt(mapTemplates1x1.size())));
            case ONE_X_FOUR:
                return Optional.of(mapTemplates1x4.get(random.nextInt(mapTemplates1x4.size())));
            default:
                return Optional.empty();
        }
    }

    public Optional<MapTemplate> getPlayerMap(final @NotNull Player player) {
        if (mapTemplates1x1.size() > 0) {
            final CachedSQLData cachedSQLData = sqlDataCache.getSQLData(player);

            int index = cachedSQLData.getSettingsMap();
            if (index < 0) {
                index = new Random().nextInt(mapTemplates1x1.size());

            }

            if (index < mapTemplates1x1.size()) {
                return Optional.of(mapTemplates1x1.get(index));
            } else {
                cachedSQLData.setSettingsMap(-1);
                return getPlayerMap(player);
            }
        }
        return Optional.empty();
    }
}
