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

import com.google.inject.Singleton;
import org.jetbrains.annotations.NotNull;

@Singleton
public class EnumUtils {

    public <E extends Enum<E>> boolean isInEnum(final @NotNull Class<E> enumClass, final @NotNull String name) {
        if (name.trim().isEmpty()) {
            return false;
        }
        try {
            Enum.valueOf(enumClass, name);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
