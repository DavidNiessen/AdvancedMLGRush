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

package com.skillplugins.advancedmlgrush.game.map.schematic.compressor;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface BlockCompressor {

    String id();

    Optional<String> compress(final @NotNull String string);

    Optional<String> uncompress(final @NotNull String string);

}
