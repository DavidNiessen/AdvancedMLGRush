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

package net.skillcode.advancedmlgrush.game.map.schematic;

import com.google.inject.Singleton;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

@Singleton
public class BlockWrapper {

    public StorableBlock toStorableBlock(final @NotNull Block block) {
        return new StorableBlock(block.getX(), block.getY(), block.getZ(),
                block.getType().name(), block.getData());
    }

}
