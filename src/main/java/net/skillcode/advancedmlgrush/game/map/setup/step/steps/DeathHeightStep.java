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

import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.config.configs.MessageConfig;
import net.skillcode.advancedmlgrush.game.map.setup.step.SetupStep;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Singleton
public class DeathHeightStep implements SetupStep<Integer> {

    @Override
    public String configPath() {
        return MessageConfig.SETUP_DEATH_HEIGHT;
    }

    @Override
    public Integer onPerform(final @NotNull Player player) {
        return (int) player.getLocation().getY();
    }
}
