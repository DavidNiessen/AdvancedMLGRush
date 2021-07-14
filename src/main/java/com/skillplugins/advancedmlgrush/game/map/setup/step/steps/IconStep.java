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

package com.skillplugins.advancedmlgrush.game.map.setup.step.steps;

import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.config.configs.MessageConfig;
import com.skillplugins.advancedmlgrush.game.map.setup.step.SetupStep;
import com.skillplugins.advancedmlgrush.libs.xseries.XMaterial;
import com.skillplugins.advancedmlgrush.util.Pair;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@Singleton
public class IconStep implements SetupStep<Pair<String, Integer>> {

    @Override
    public String configPath() {
        return MessageConfig.SETUP_ICON;
    }

    @Override
    public Pair<String, Integer> onPerform(final @NotNull Player player) {
        final ItemStack itemStack = player.getItemInHand();
        final XMaterial xMaterial = XMaterial.matchXMaterial(itemStack);
        if (xMaterial != null) {
            if (xMaterial != XMaterial.AIR) {
                return new Pair<String, Integer>(xMaterial.name(), Byte.toUnsignedInt(xMaterial.getData()));
            }
        }

        final XMaterial stone = XMaterial.STONE;
        return new Pair<>(stone.name(), Byte.toUnsignedInt(stone.getData()));
    }
}
