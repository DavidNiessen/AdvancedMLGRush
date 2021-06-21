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

package net.skillcode.advancedmlgrush.sound;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.config.configs.SoundConfig;
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
            mlgSound.playSound(player);
        }
    }
}
