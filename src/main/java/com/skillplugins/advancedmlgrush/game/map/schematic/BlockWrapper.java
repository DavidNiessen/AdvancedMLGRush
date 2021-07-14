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

package com.skillplugins.advancedmlgrush.game.map.schematic;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.config.configs.MainConfig;
import com.skillplugins.advancedmlgrush.exception.ExceptionHandler;
import com.skillplugins.advancedmlgrush.game.map.file.MapFile;
import com.skillplugins.advancedmlgrush.game.map.schematic.compressor.BlockCompressor;
import com.skillplugins.advancedmlgrush.game.map.schematic.compressor.CompressorManager;
import com.skillplugins.advancedmlgrush.game.map.schematic.serializer.BlockSerializer;
import com.skillplugins.advancedmlgrush.game.map.schematic.serializer.SerializerManager;
import com.skillplugins.advancedmlgrush.miscellaneous.Constants;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class BlockWrapper {

    @Inject
    private ExceptionHandler exceptionHandler;
    @Inject
    private MainConfig mainConfig;
    @Inject
    private CompressorManager compressorManager;
    @Inject
    private SerializerManager serializerManager;
    @Inject
    private JavaPlugin javaPlugin;


    public Optional<String> toString(final @NotNull Collection<Block> list, final @NotNull String serializer,
                                     final @NotNull String compressor) { ;

        final Optional<BlockSerializer> optionalBlockSerializer = serializerManager.getSerializer(serializer);
        final Optional<BlockCompressor> optionalBlockCompressor = compressorManager.getCompressor(compressor);

        if (!optionalBlockSerializer.isPresent()) {
            javaPlugin.getLogger().warning(String.format(Constants.INVALID_SERIALIZER, serializer));
        } else if (!optionalBlockCompressor.isPresent()) {
            javaPlugin.getLogger().warning(String.format(Constants.INVALID_COMPRESSOR, compressor));
        } else {
            final BlockSerializer blockSerializer = optionalBlockSerializer.get();
            final BlockCompressor blockCompressor = optionalBlockCompressor.get();

            final Optional<String> serialized = blockSerializer.serialize(list.stream().map(this::toStorableBlock).collect(Collectors.toList()));
            if (serialized.isPresent()) {
                final Optional<String> compressed = blockCompressor.compress(serialized.get());
                if (compressed.isPresent()) {
                    return compressed;
                }
            }
        }
        return Optional.empty();
    }

    public Optional<List<StorableBlock>> toList(final @NotNull String string, final @NotNull MapFile mapFile) {
        final String blockCompressorID = mapFile.getCompressor();
        final String blockSerializerID = mapFile.getSerializer();

        final Optional<BlockCompressor> optionalBlockCompressor = compressorManager.getCompressor(mapFile.getCompressor());
        final Optional<BlockSerializer> optionalBlockSerializer = serializerManager.getSerializer(mapFile.getSerializer());

        if (!optionalBlockCompressor.isPresent()) {
            javaPlugin.getLogger().warning(String.format(Constants.INVALID_COMPRESSOR, blockCompressorID));
        } else if (!optionalBlockSerializer.isPresent()) {
            javaPlugin.getLogger().warning(String.format(Constants.INVALID_SERIALIZER, blockSerializerID));
        } else {
            final BlockCompressor blockCompressor = optionalBlockCompressor.get();
            final BlockSerializer blockSerializer = optionalBlockSerializer.get();

            final Optional<String> uncompressed = blockCompressor.uncompress(string);
            if (uncompressed.isPresent()) {
                return blockSerializer.deserialize(uncompressed.get());
            }
        }
        return Optional.empty();
    }


    private StorableBlock toStorableBlock(final @NotNull Block block) {
        return new StorableBlock(block.getX(), block.getY(), block.getZ(),
                block.getType().name(), block.getData());
    }

}
