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

package com.skillplugins.advancedmlgrush.game.map.schematic.serializer;

import com.skillplugins.advancedmlgrush.game.map.schematic.StorableBlock;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public interface BlockSerializer {

    String id();

    Optional<String> serialize(final @NotNull List<StorableBlock> blockList);

    Optional<List<StorableBlock>> deserialize(final @NotNull String string);

}
