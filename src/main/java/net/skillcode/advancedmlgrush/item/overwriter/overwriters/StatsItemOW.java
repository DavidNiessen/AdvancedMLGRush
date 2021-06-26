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

package net.skillcode.advancedmlgrush.item.overwriter.overwriters;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.item.EnumItem;
import net.skillcode.advancedmlgrush.item.builder.IBFactory;
import net.skillcode.advancedmlgrush.item.builder.MetaType;
import net.skillcode.advancedmlgrush.item.overwriter.ItemOW;
import net.skillcode.advancedmlgrush.item.overwriter.ItemOWPriority;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Singleton
public class StatsItemOW implements ItemOW {

    private final IBFactory ibFactory;

    @Inject
    public StatsItemOW(final @NotNull IBFactory ibFactory) {
        this.ibFactory = ibFactory;
    }

    @Override
    public EnumItem getEnumItem() {
        return EnumItem.STATS;
    }

    @Override
    public ItemOWPriority getPriority() {
        return ItemOWPriority.LOW;
    }

    @Override
    public ItemStack getItemStack(final @NotNull Optional<Player> optionalPlayer, final @NotNull String itemName) {
        return optionalPlayer.map(player -> ibFactory.create(MetaType.SKULL_META, 3)
                .owner(player.getName()).name(itemName).build()).orElseGet(()
                -> ibFactory.create(MetaType.SKULL_META, 3).name(itemName).build());

    }
}
