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

package net.skillcode.advancedmlgrush.item.overwriter;

import net.skillcode.advancedmlgrush.item.EnumItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Used to override items from configs
 * <p>
 * @see ItemOWManager
 */
public interface ItemOW {

    EnumItem getEnumItem();

    ItemOWPriority getPriority();

    ItemStack getItemStack(final Optional<Player> optionalPlayer, final @NotNull String itemName);

}
