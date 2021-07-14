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
import com.skillplugins.advancedmlgrush.util.Pair;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Singleton
public class BedPlayer3Step implements SetupStep<Pair<Location, Location>> {

    @Override
    public String configPath() {
        return MessageConfig.SETUP_BED_PLAYER_3;
    }

    @Override
    public Pair<Location, Location> onPerform(final @NotNull Player player) {
        return new Pair<>(player.getLocation(), player.getTargetBlock(null, 4).getLocation());
    }
}
