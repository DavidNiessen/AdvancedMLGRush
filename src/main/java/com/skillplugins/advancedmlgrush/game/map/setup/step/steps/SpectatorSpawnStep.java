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
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Singleton
public class SpectatorSpawnStep implements SetupStep<Location> {

    @Override
    public String configPath() {
        return MessageConfig.SETUP_SPECTATOR_SPAWN;
    }

    @Override
    public Location onPerform(final @NotNull Player player) {
        return player.getLocation();
    }
}
