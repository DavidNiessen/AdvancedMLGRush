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

package net.skillcode.advancedmlgrush.game.map.setup.step.steps;

import net.skillcode.advancedmlgrush.config.configs.MessageConfig;
import net.skillcode.advancedmlgrush.game.map.setup.step.SetupStep;
import net.skillcode.advancedmlgrush.miscellaneous.Constants;
import net.skillcode.advancedmlgrush.util.Pair;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class IconStep implements SetupStep<Pair<String, Integer>> {

    @Override
    public String configPath() {
        return MessageConfig.SETUP_ICON;
    }

    @Override
    public Pair<String, Integer> onPerform(final @NotNull Player player) {
        final ItemStack itemStack = player.getItemInHand();
        return new Pair<>(itemStack.getType() == null || itemStack.getType() == Material.AIR
                ? Constants.DEFAULT_MAP_MATERIAL.name() : itemStack.getType().name(), 0);
    }
}
