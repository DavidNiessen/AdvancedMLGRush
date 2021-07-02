/*
 * Copyright (c) 2021 SkillCode
 *
 * This file is a part of the source code of the
 * AdvancedMLGRush plugin by SkillCode.
 *
 * This file may only be used in compliance with the
 * LICENSE.txt (https://github.com/SkillC0de/AdvancedMLGRush/blob/master/LICENSE.txt).
 *
 * Support: https://discord.skillplugins.com
 */

package net.skillcode.advancedmlgrush.game.map.world;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.config.configs.MessageConfig;
import net.skillcode.advancedmlgrush.miscellaneous.Constants;
import net.skillcode.advancedmlgrush.sql.ThreadPoolManager;
import net.skillcode.advancedmlgrush.util.WorldUtils;
import org.bukkit.*;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Singleton
public class MapWorldGenerator extends ChunkGenerator {

    @Inject
    private MessageConfig messageConfig;
    @Inject
    private ThreadPoolManager threadPoolManager;
    @Inject
    private JavaPlugin javaPlugin;

    @Inject
    public MapWorldGenerator(final @NotNull MessageConfig messageConfig,
                             final @NotNull ThreadPoolManager threadPoolManager) {
        this.messageConfig = messageConfig;
        this.threadPoolManager = threadPoolManager;
    }


    public World createWorld() {
        final UUID uuid = UUID.randomUUID();
        return new WorldCreator(Constants.WORLD_PATH + uuid).type(WorldType.FLAT).generator(this).createWorld();
    }

    public void deleteWorld(final @NotNull World world) {
        if (world != null) {
            world.getPlayers().forEach(player -> player.kickPlayer(messageConfig.getWithPrefix(Optional.of(player), MessageConfig.ERROR)));
            Bukkit.getScheduler().scheduleSyncDelayedTask(javaPlugin, () -> {
                Bukkit.unloadWorld(world, false);
                Bukkit.getWorlds().remove(world);
                Bukkit.getScheduler().scheduleSyncDelayedTask(javaPlugin, () -> {
                    deleteWorldFilesAsync(world.getWorldFolder());
                }, 20);
            }, 20);
        }
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

    private void deleteWorldFilesAsync(final File file) {
        threadPoolManager.submit(() -> WorldUtils.deleteWorldFiles(file));
    }

}
