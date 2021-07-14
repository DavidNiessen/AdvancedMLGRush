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
import com.skillplugins.advancedmlgrush.libs.xseries.XSound;
import com.skillplugins.advancedmlgrush.miscellaneous.Constants;
import com.skillplugins.advancedmlgrush.util.EnumUtils;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class SoundParser {

    private final JavaPlugin javaPlugin;
    private final EnumUtils enumUtils;

    private final Map<String, Optional<MLGSound>> cache = new ConcurrentHashMap<>();

    @Inject
    public SoundParser(final JavaPlugin javaPlugin, final EnumUtils enumUtils) {
        this.javaPlugin = javaPlugin;
        this.enumUtils = enumUtils;
    }

    public Optional<MLGSound> parse(final @NotNull String input) {
        if (cache.containsKey(input)) {
            return cache.get(input);
        }

        Optional<XSound> sound = Optional.empty();

        float volume = Constants.DEFAULT_SOUND_VOLUME;
        float pitch = Constants.DEFAULT_SOUND_PITCH;

        final String[] array = input.split(":");
        final String soundName = array[0];
        final Optional<XSound> optional = parseSound(soundName);

        if (optional.isPresent()) {
            sound = optional;
        } else if (!soundName.equalsIgnoreCase("NONE")) {
            javaPlugin.getLogger().warning(String.format(Constants.SOUND_PARSE_ERROR, soundName));
        }

        try {

            if (array.length >= 2) {
                volume = Float.parseFloat(array[1]);
            }

            if (array.length >= 3) {
                pitch = Float.parseFloat(array[2]);
            }

        } catch (final NumberFormatException exception) {
            javaPlugin.getLogger().warning(String.format(Constants.SOUND_PARSE_ERROR, array[0]));
        }

        if (sound.isPresent()) {
            final Optional<MLGSound> mlgSound = Optional.of(new MLGSound(sound.get().parseSound(), volume, pitch));
            cache.put(input, mlgSound);
            return mlgSound;
        }
        return Optional.empty();
    }

    public String parseString(final @NotNull String soundName, final float volume, final float pitch) {
        final StringBuilder stringBuilder = new StringBuilder(soundName);
        if (volume > 0) {
            stringBuilder.append(":").append(volume);
            if (pitch > 0) {
                stringBuilder.append(":").append(pitch);
            }
        }

        return stringBuilder.toString();
    }

    public String parseString(final @NotNull XSound xSound, final float volume, final float pitch) {
        return parseString(xSound.name(), volume, pitch);
    }

    private Optional<XSound> parseSound(final @NotNull String sound) {
        if (enumUtils.isInEnum(XSound.class, sound)) {
            return Optional.of(XSound.valueOf(sound));
        }

        return Optional.empty();
    }

}
