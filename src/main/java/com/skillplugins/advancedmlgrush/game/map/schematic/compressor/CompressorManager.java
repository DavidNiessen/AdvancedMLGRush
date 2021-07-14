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

package com.skillplugins.advancedmlgrush.game.map.schematic.compressor;

import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.game.map.schematic.compressor.compressors.GZIPCompressor;
import com.skillplugins.advancedmlgrush.util.Initializer;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class CompressorManager implements Initializer {

    private final Map<String, BlockCompressor> compressorMap = new ConcurrentHashMap<>();

    public Optional<BlockCompressor> getCompressor(final @NotNull String compressor) {
        return Optional.ofNullable(compressorMap.getOrDefault(compressor.toUpperCase(Locale.ROOT), null));
    }

    @Override
    public void init(final @NotNull Injector injector) {
        register(injector.getInstance(GZIPCompressor.class));
    }

    private void register(final @NotNull BlockCompressor blockCompressor) {
        compressorMap.put(blockCompressor.id().toUpperCase(Locale.ROOT), blockCompressor);
    }
}
