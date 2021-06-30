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
import com.google.inject.assistedinject.Assisted;
import lombok.Getter;
import net.skillcode.advancedmlgrush.config.configs.MessageConfig;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MapTemplate {

    @Getter
    private final MapData mapData;

    @Inject
    private MapInstanceManager mapInstanceManager;
    @Inject
    private MessageConfig messageConfig;

    @Inject
    public MapTemplate(final @Assisted @NotNull MapData mapData) {
        this.mapData = mapData;
    }

    public void createInstance(final @NotNull List<Player> players, final int rounds) {
        mapInstanceManager.createInstance(players, this, rounds);
    }
}
