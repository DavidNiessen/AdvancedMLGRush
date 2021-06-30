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

package net.skillcode.advancedmlgrush.item;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.config.configs.ItemNameConfig;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@Singleton
public class ItemUtils {

    private final ItemNameConfig itemNameConfig;

    @Inject
    public ItemUtils(final ItemNameConfig itemNameConfig) {
        this.itemNameConfig = itemNameConfig;
    }

    public boolean isValidItem(final @Nullable ItemStack itemStack) {
        return isValidType(itemStack) && isValidName(itemStack);
    }

    public boolean isValidType(final @Nullable ItemStack itemStack) {
        return itemStack != null
                && itemStack.getType() != null
                && itemStack.getType() != Material.AIR;
    }

    public boolean isValidName(final @Nullable ItemStack itemStack) {
        return itemStack != null
                && itemStack.getItemMeta() != null
                && itemStack.getItemMeta().getDisplayName() != null;
    }

    public boolean compare(final @NotNull ItemStack itemStack, final @NotNull EnumItem enumItem, final Optional<Player> optionalPlayer) {
        return isValidItem(itemStack)
                && itemNameConfig.getString(optionalPlayer, enumItem).equals(itemStack.getItemMeta().getDisplayName());
    }

}
