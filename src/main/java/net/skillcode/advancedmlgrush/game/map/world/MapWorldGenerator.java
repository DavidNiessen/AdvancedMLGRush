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

package net.skillcode.advancedmlgrush.game.map.world;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.config.configs.MessageConfig;
import net.skillcode.advancedmlgrush.miscellaneous.Constants;
import net.skillcode.advancedmlgrush.sql.ThreadPoolManager;
import net.skillcode.advancedmlgrush.util.Initializer;
import net.skillcode.advancedmlgrush.util.Pair;
import org.bukkit.*;
import org.bukkit.generator.ChunkGenerator;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Singleton
public class MapWorldGenerator extends ChunkGenerator implements Initializer {

    private final MessageConfig messageConfig;
    private final ThreadPoolManager threadPoolManager;

    @Inject
    public MapWorldGenerator(final @NotNull MessageConfig messageConfig,
                             final @NotNull ThreadPoolManager threadPoolManager) {
        this.messageConfig = messageConfig;
        this.threadPoolManager = threadPoolManager;
    }


    public Pair<World, UUID> createWorld() {
        final UUID uuid = UUID.randomUUID();
        final WorldCreator worldCreator = new WorldCreator(Constants.WORLD_PATH + uuid);

        worldCreator.type(WorldType.FLAT);
        worldCreator.generator(this);
        worldCreator.generateStructures(false);
        worldCreator.generatorSettings("2;0;1;");

        return new Pair<>(worldCreator.createWorld(), uuid);
    }

    public void deleteWorld(final @NotNull UUID uuid) {
        final World world = Bukkit.getWorld(uuid.toString());
        if (world != null) {
            world.getPlayers().forEach(player -> player.kickPlayer(messageConfig.getWithPrefix(Optional.of(player), MessageConfig.ERROR)));
            Bukkit.unloadWorld(world, false);
            deleteFilesAsync(world.getWorldFolder());
        }
    }

    public void deleteWorlds() {
        final File directory = new File(Constants.WORLD_PATH);
    }

    @Override
    public boolean canSpawn(World world, int x, int z) {
        return true;
    }

    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        return new Location(world, 0, 64, 0);
    }

    @Override
    public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome) {
        return createChunkData(world);
    }

    @Override
    public void init(final @NotNull Injector injector) {
        deleteWorlds();
    }

    private void deleteFilesAsync(final File file) {
        threadPoolManager.submit(() -> deleteFiles(file));
    }

    private void deleteFiles(final File file) {
        if (file.isDirectory()) {
            for (File file1 : file.listFiles()) {
                file1.delete();
                deleteFiles(file1);
            }
        }
        file.delete();
    }
}
