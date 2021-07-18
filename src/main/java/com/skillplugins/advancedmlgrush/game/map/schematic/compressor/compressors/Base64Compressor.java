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

package com.skillplugins.advancedmlgrush.game.map.schematic.compressor.compressors;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.config.configs.MainConfig;
import com.skillplugins.advancedmlgrush.exception.ExceptionHandler;
import com.skillplugins.advancedmlgrush.game.map.schematic.compressor.BlockCompressor;
import com.skillplugins.advancedmlgrush.util.json.JsonConfigAPI;
import org.jetbrains.annotations.NotNull;

import java.util.Base64;
import java.util.Optional;

@Singleton
public class Base64Compressor implements BlockCompressor {

    private final ExceptionHandler exceptionHandler;
    private final MainConfig mainConfig;

    @Inject
    private JsonConfigAPI jsonConfigAPI;

    @Inject
    public Base64Compressor(final @NotNull ExceptionHandler exceptionHandler,
                            final @NotNull MainConfig mainConfig) {
        this.exceptionHandler = exceptionHandler;
        this.mainConfig = mainConfig;
    }

    @Override
    public String id() {
        return "BASE64";
    }

    public Optional<String> compress(final @NotNull String string) {
        return Optional.of(Base64.getEncoder().encodeToString(string.getBytes()));
    }

    public Optional<String> uncompress(final @NotNull String string) {
        return Optional.of(new String(Base64.getDecoder().decode(string.getBytes())));
    }

}
