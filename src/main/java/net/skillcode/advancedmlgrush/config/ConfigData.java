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

package net.skillcode.advancedmlgrush.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public interface ConfigData {

    @Nullable
    Object get(final @NotNull String path);

    String getString(final @NotNull String path);

    boolean getBoolean(final @NotNull String path);

    int getInt(final @NotNull String path);

    double getDouble(final @NotNull String path);

    long getLong(final @NotNull String path);

    ArrayList<String> getArrayList(final @NotNull String path);
}
