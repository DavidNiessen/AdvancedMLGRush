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

package net.skillcode.advancedmlgrush.game.map.schematica;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.libs.xseries.XMaterial;
import net.skillcode.advancedmlgrush.sql.ThreadPoolManager;
import net.skillcode.advancedmlgrush.util.EnumUtils;
import net.skillcode.advancedmlgrush.util.NMSUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Singleton
public class BlockPaster {

    @Inject
    private NMSUtils nmsUtils;
    @Inject
    private ThreadPoolManager threadPoolManager;
    @Inject
    private EnumUtils enumUtils;

    public Future<Void> pasteAsync(final @NotNull List<StorableBlock> list, final @NotNull World world) {
        return CompletableFuture.runAsync(() -> paste(list, world), threadPoolManager.getThreadPool());
    }

    public void paste(final @NotNull List<StorableBlock> list, final @NotNull World world) {
        list.forEach(storableBlock -> {
            final Block block = new Location(world, storableBlock.getX(), storableBlock.getY(),
                    storableBlock.getZ()).getBlock();

            final String materialName = storableBlock.getMaterial();
            final Material material = enumUtils.isInEnum(Material.class, materialName)
                    ? Material.valueOf(materialName) : XMaterial.STONE.parseMaterial();

            block.setType(material);
            nmsUtils.setBlockData(block, storableBlock.getData());

        });
    }

}
