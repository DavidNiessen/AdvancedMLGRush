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

package net.skillcode.advancedmlgrush.util;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.exception.ExceptionHandler;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;

@Singleton
public class NMSUtils {

    private final JavaPlugin javaPlugin;
    private final ExceptionHandler exceptionHandler;

    @Inject
    public NMSUtils(final @NotNull JavaPlugin javaPlugin, final @NotNull ExceptionHandler exceptionHandler) {
        this.javaPlugin = javaPlugin;
        this.exceptionHandler = exceptionHandler;
    }

    public String getServerVersion() {
        return javaPlugin.getServer().getClass().getPackage().getName().substring(23);
    }

    public Class<?> getOBCClass(final @NotNull String name) {
        try {
            return Class.forName("org.bukkit.craftbukkit." + getServerVersion() + '.' + name);
        } catch (ClassNotFoundException e) {
            exceptionHandler.handle(e);
        }
        return null;
    }

    public Class<?> getNMSClass(final @NotNull String className, final @NotNull String newFullClassName) {
        try {
            return Class.forName("net.minecraft.server." + getServerVersion() + '.' + className);
        } catch (ClassNotFoundException e) {
            try {
                return Class.forName(newFullClassName);
            } catch (ClassNotFoundException classNotFoundException) {
                exceptionHandler.handle(e);
            }
        }
        return null;
    }

    public void setBlockData(final @NotNull Block block, final byte blockData) {
        try {
            block.getClass().getMethod("setData", byte.class).invoke(block, blockData);
        } catch (Exception e) {
            exceptionHandler.handle(e);
        }
    }

    public ItemStack setUnbreakable(@NotNull ItemStack itemStack) {
        try {
            Class<?> craftItemStack = getOBCClass("inventory.CraftItemStack");
            Method getNMSI = craftItemStack.getMethod("asNMSCopy", ItemStack.class);
            Object nms = getNMSI.invoke(null, itemStack);
            Object tag = getNMSClass("NBTTagCompound", "net.minecraft.nbt.NBTTagCompound").getConstructor().newInstance();
            tag.getClass().getMethod("setInt", String.class, int.class).invoke(tag, "Unbreakable", 1);
            nms.getClass().getMethod("setTag", tag.getClass()).invoke(nms, tag);
            itemStack = (ItemStack) craftItemStack.getMethod("asCraftMirror", nms.getClass()).invoke(null, nms);
        } catch (Exception e) {
            exceptionHandler.handle(e);
        }
        return itemStack;
    }

}
