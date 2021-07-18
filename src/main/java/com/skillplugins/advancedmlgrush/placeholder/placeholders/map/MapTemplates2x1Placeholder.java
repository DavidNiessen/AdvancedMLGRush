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

package com.skillplugins.advancedmlgrush.placeholder.placeholders.map;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.game.map.MapManager;
import com.skillplugins.advancedmlgrush.placeholder.Placeholder;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Singleton
public class MapTemplates2x1Placeholder extends Placeholder {

    private final MapManager mapManager;

    @Inject
    public MapTemplates2x1Placeholder(final @NotNull MapManager mapManager) {
        this.mapManager = mapManager;
    }

    @Override
    public String identifier() {
        return "%map_templates_2x1%";
    }

    @Override
    public String onRequest(final @NotNull Optional<Player> optionalPlayer) {
        return String.valueOf(mapManager.getMapTemplates2x1().size());
    }
}
