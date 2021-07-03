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

package net.skillcode.advancedmlgrush.game.map.schematic;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.config.configs.MainConfig;
import net.skillcode.advancedmlgrush.config.configs.MessageConfig;
import net.skillcode.advancedmlgrush.libs.xseries.ActionBar;
import net.skillcode.advancedmlgrush.libs.xseries.XMaterial;
import net.skillcode.advancedmlgrush.util.EnumUtils;
import net.skillcode.advancedmlgrush.util.NMSUtils;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

@Singleton
public class SchematicLoader {

    @Inject
    private NMSUtils nmsUtils;
    @Inject
    private EnumUtils enumUtils;
    @Inject
    private JavaPlugin javaPlugin;
    @Inject
    private MainConfig mainConfig;
    @Inject
    private MessageConfig messageConfig;

    public void load(final @NotNull List<StorableBlock> blockList, final @NotNull Iterable<Player> players,
                     final @NotNull World world, final @NotNull Runnable onFinish) {
        final int blocksPerTick = mainConfig.getInt(MainConfig.PASTE_BLOCKS_PER_TICK);
        final Queue<StorableBlock> queue = new LinkedList<>(blockList);

        new BukkitRunnable() {
            @Override
            public void run() {
                for (int i = 0; i < Math.min(blocksPerTick, queue.size()); i++) {

                    final String progress = String.valueOf(100 - (queue.size() * 100) / blockList.size());
                    players.forEach(player -> ActionBar.sendActionBar(player,
                            messageConfig.getString(Optional.of(player), MessageConfig.LOADING_PROGRESS_ACTION_BAR).replace("%progress%", progress)));

                    final StorableBlock storableBlock = queue.poll();

                    final Block block = world.getBlockAt(
                            (int) storableBlock.getX(),
                            (int) storableBlock.getY(),
                            (int) storableBlock.getZ());

                    final String materialName = storableBlock.getMaterial();
                    final Material material = enumUtils.isInEnum(Material.class, materialName)
                            ? Material.valueOf(materialName) : XMaterial.STONE.parseMaterial();


                    block.setType(material);
                    nmsUtils.setBlockData(block, storableBlock.getData());

                }
                if (queue.isEmpty()) {
                    onFinish.run();
                    cancel();
                }
            }
        }.runTaskTimer(javaPlugin, 1, 1);
    }

}
