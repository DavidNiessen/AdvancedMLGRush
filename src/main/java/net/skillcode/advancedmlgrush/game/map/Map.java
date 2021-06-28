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
import net.skillcode.advancedmlgrush.game.map.file.MapFile;
import org.jetbrains.annotations.NotNull;

public class Map {

    @Getter
    private final MapData mapData;
    private final MapFile mapFile;

    @Inject
    public Map(final @Assisted @NotNull MapData mapData,
               final @Assisted @NotNull MapFile mapFile) {
        this.mapData = mapData;
        this.mapFile = mapFile;
    }
}
