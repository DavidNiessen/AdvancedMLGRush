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

package com.skillplugins.advancedmlgrush.util;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.config.configs.MessageConfig;
import org.bukkit.Bukkit;

import java.util.Optional;

@Singleton
public class PlayerUtils {

    private final MessageConfig messageConfig;

    @Inject
    public PlayerUtils(final MessageConfig messageConfig) {
        this.messageConfig = messageConfig;
    }

    public void restartKick() {
        Bukkit.getOnlinePlayers().forEach(player -> player.kickPlayer(messageConfig.getWithPrefix(Optional.of(player), MessageConfig.RESTART)));
    }

}
