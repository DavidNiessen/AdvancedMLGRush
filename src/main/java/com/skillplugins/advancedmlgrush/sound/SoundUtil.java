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

package com.skillplugins.advancedmlgrush.sound;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.config.configs.SoundConfig;
import com.skillplugins.advancedmlgrush.event.events.SoundPlayEvent;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Singleton
public class SoundUtil {

    @Inject
    private SoundConfig soundConfig;
    @Inject
    private SoundParser soundParser;

    public void playSound(final @NotNull Player player, final @NotNull String soundPath) {
        final Optional<MLGSound> optional = soundParser.parse(soundConfig.getString(soundPath));
        if (optional.isPresent()) {
            final MLGSound mlgSound = optional.get();
            final SoundPlayEvent soundPlayEvent = new SoundPlayEvent(player, mlgSound);
            if (!soundPlayEvent.isCancelled()) {
                mlgSound.playSound(player);
            }
        }
    }
}
