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
import com.google.inject.assistedinject.Assisted;
import lombok.Getter;
import net.skillcode.advancedmlgrush.config.configs.MessageConfig;
import net.skillcode.advancedmlgrush.game.map.file.MapFile;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class MapTemplate {

    @Getter
    private final MapData mapData;
    private final MapFile mapFile;

    @Inject
    private MapInstanceManager mapInstanceManager;
    @Inject
    private MessageConfig messageConfig;

    @Inject
    public MapTemplate(final @Assisted @NotNull MapData mapData,
                       final @Assisted @NotNull MapFile mapFile) {
        this.mapData = mapData;
        this.mapFile = mapFile;
    }

    public void createInstance(final @NotNull List<Player> players) {
        if (mapData.getMapType().getPlayers() == players.size()) {
            mapInstanceManager.createInstance(players, this);
            return;
        }

        players.forEach(player -> player.sendMessage(messageConfig.getWithPrefix(Optional.of(player), MessageConfig.ERROR)));
    }
}
