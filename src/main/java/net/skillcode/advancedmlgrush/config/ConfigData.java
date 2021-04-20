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
