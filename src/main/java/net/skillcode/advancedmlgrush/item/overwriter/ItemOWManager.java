/*
 * Copyright (c) 2021 SkillCode
 *
 * This file is a part of the source code of the
 * AdvancedMLGRush plugin from SkillCode.
 *
 * This class may only be used in compliance with the
 * LICENSE.txt (https://github.com/SkillC0de/AdvancedMLGRush/blob/master/LICENSE.txt).
 *
 * Support: https://discord.skillplugins.com
 */

package net.skillcode.advancedmlgrush.item.overwriter;

import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.item.EnumItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class ItemOWManager {
    
    private final Map<EnumItem, ItemOW> ows = new ConcurrentHashMap<>();

    public void registerItemOW(final @NotNull ItemOW itemOW) {

       if (ows.containsKey(itemOW.getEnumItem()) && itemOW.getPriority() != ItemOWPriority.HIGH) {
           return;
       }

       ows.put(itemOW.getEnumItem(), itemOW);
    }

    public Optional<ItemStack> getItem(final @NotNull Optional<Player> optionalPlayer, final @NotNull EnumItem enumItem) {
        if (ows.containsKey(enumItem)) {
            return Optional.of(ows.get(enumItem).getItemStack(optionalPlayer));
        }

        return Optional.empty();
    }

}
