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

package net.skillcode.advancedmlgrush.game.map.schematic;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.exception.ExceptionHandler;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class BlockStorage {

    private final BlockWrapper blockWrapper;
    private final ExceptionHandler exceptionHandler;

    @Inject
    public BlockStorage(final @NotNull BlockWrapper blockWrapper, final ExceptionHandler exceptionHandler) {
        this.blockWrapper = blockWrapper;
        this.exceptionHandler = exceptionHandler;
    }

    public Optional<String> toString(final @NotNull Collection<Block> list) {
        return toString(list.stream().map(blockWrapper::toStorableBlock).collect(Collectors.toList()));
    }

    public Optional<String> toString(final @NotNull List<StorableBlock> list) {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            final ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(list);
        } catch (IOException e) {
            exceptionHandler.handle(e);
            return Optional.empty();
        }
        return Optional.of(Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray()));
    }

    public Optional<List<StorableBlock>> toList(final @NotNull String string) {
        final byte[] bytes = Base64.getDecoder().decode(string);

        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        try {
            final ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return Optional.of((List<StorableBlock>) objectInputStream.readObject());
        } catch (ClassNotFoundException | IOException e) {
            exceptionHandler.handle(e);
        }
        return Optional.empty();
    }

}
