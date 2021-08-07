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

package com.skillplugins.advancedmlgrush.game.map.world;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.config.configs.MessageConfig;
import com.skillplugins.advancedmlgrush.miscellaneous.Constants;
import com.skillplugins.advancedmlgrush.sql.ThreadPoolManager;
import com.skillplugins.advancedmlgrush.util.WorldUtils;
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


    public World createWorld() {
        final UUID uuid = UUID.randomUUID();
        final World world = new WorldCreator(Constants.WORLD_PATH + uuid).type(WorldType.FLAT).generator(this).createWorld();
        world.setGameRuleValue("doDaylightCycle", "false");
        world.setGameRuleValue("randomTickSpeed", "0");
        world.setTime(10000);
        return world;
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
