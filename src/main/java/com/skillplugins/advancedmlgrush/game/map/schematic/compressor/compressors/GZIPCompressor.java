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
import org.bukkit.Bukkit;
import org.fusesource.jansi.Ansi;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@Singleton
@Deprecated
public class GZIPCompressor implements BlockCompressor {

    private final ExceptionHandler exceptionHandler;
    private final MainConfig mainConfig;

    @Inject
    public GZIPCompressor(final @NotNull ExceptionHandler exceptionHandler,
                          final @NotNull MainConfig mainConfig) {
        this.exceptionHandler = exceptionHandler;
        this.mainConfig = mainConfig;
    }

    @Override
    public String id() {
        return "GZIP";
    }

    @Deprecated
    public Optional<String> compress(final @NotNull String string) {
        Bukkit.getLogger().warning(Ansi.ansi().fg(Ansi.Color.RED).boldOff().toString() + "WARNING: The GZIP compressor will be removed at some point in the future and should not be used if possible." + Ansi.ansi().reset());
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            final GZIPOutputStream gzipOutputStream = new GZIPOutputStream(new BufferedOutputStream(byteArrayOutputStream));
            gzipOutputStream.write(string.getBytes(StandardCharsets.UTF_8));
            gzipOutputStream.close();
            return Optional.of(byteArrayOutputStream.toString("ISO-8859-1"));
        } catch (IOException e) {
            exceptionHandler.handleUnexpected(e);
        }
        return Optional.empty();
    }

    @Deprecated
    public Optional<String> uncompress(final @NotNull String string) {
        Bukkit.getLogger().warning(Ansi.ansi().fg(Ansi.Color.RED).boldOff().toString() + "WARNING: The GZIP compressor will be removed at some point in the future and should not be used if possible." + Ansi.ansi().reset());
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(string.getBytes(StandardCharsets.ISO_8859_1));
        try {
            final GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream);
            int bufferSize = mainConfig.getInt(MainConfig.GZIP_COMPRESSOR_BUFFER_SIZE);
            final byte[] buffer = new byte[bufferSize];

            int read;
            while ((read = gzipInputStream.read(buffer)) >= 0) {
                byteArrayOutputStream.write(buffer, 0, read);
            }
            return Optional.of(byteArrayOutputStream.toString("UTF-8"));
        } catch (IOException e) {
            exceptionHandler.handleUnexpected(e);
        }
        return Optional.empty();
    }

}