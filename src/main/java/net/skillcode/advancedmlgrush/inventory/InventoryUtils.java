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

package net.skillcode.advancedmlgrush.inventory;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.annotations.PostConstruct;
import net.skillcode.advancedmlgrush.config.configs.InventoryNameConfig;
import net.skillcode.advancedmlgrush.config.configs.ItemNameConfig;
import net.skillcode.advancedmlgrush.config.configs.MainConfig;
import net.skillcode.advancedmlgrush.config.configs.SoundConfig;
import net.skillcode.advancedmlgrush.item.EnumItem;
import net.skillcode.advancedmlgrush.item.ItemManager;
import net.skillcode.advancedmlgrush.item.builder.IBFactory;
import net.skillcode.advancedmlgrush.sound.SoundUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class InventoryUtils {

    @Inject
    private InventoryManager inventoryManager;
    @Inject
    private MainConfig mainConfig;
    @Inject
    private ItemNameConfig itemNameConfig;
    @Inject
    private IBFactory ibFactory;
    @Inject
    private SoundUtil soundUtil;
    @Inject
    private InventoryNameConfig inventoryNameConfig;
    @Inject
    private ItemManager itemManager;

    private ItemStack background;

    @PostConstruct
    public void init() {
        background = itemManager.getItem(Optional.empty(), EnumItem.INVENTORY_BACKGROUND);
    }


    public boolean isOpenInventory(final @NotNull Player player, final @NotNull Class<? extends AbstractInventory> clazz) {
        final Optional<Class<? extends AbstractInventory>> optional = inventoryManager.getOpenInventory(player);
        return optional.isPresent() && optional.get().equals(clazz);
    }

    public void frame(final @NotNull Inventory inventory) {
        final int size = inventory.getSize();
        for (int i = 1; i < size + 1; i++) {
            if (i <= 9
                    || i > size - 9
                    || i % 9 <= 1) {
                inventory.setItem(i - 1, background);
            }
        }
    }

    public void fill(final @NotNull Inventory inventory) {
        final int size = inventory.getSize();
        for (int i = 0; i < size; i++) {
            inventory.setItem(i, background);
        }
    }

    public List<Integer> getEmptySlots(final @NotNull Inventory inventory) {
        final List<Integer> slots = new ArrayList<>();
        for (int i = 0; i < inventory.getSize(); i++) {
            final ItemStack itemStack = inventory.getItem(i);
            if (itemStack == null
                    || itemStack.getType() == null
                    || itemStack.getType() == Material.AIR) {
                slots.add(i);
            }
        }
        return slots;
    }

    public void playClickSound(final @NotNull Player player) {
        soundUtil.playSound(player, SoundConfig.INVENTORY_CLICK);
    }

    public String getInventoryName(final @NotNull String path) {
        return inventoryNameConfig.getString(Optional.empty(), path);
    }

}
