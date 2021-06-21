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

package net.skillcode.advancedmlgrush.item.items;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.item.EnumItem;
import net.skillcode.advancedmlgrush.item.ItemManager;
import net.skillcode.advancedmlgrush.miscellaneous.Constants;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Singleton
public class LobbyItems {

    @Inject
    private ItemManager itemManager;

    public void setLobbyItems(final @NotNull Player player) {
        player.getInventory().setItem(Constants.CHALLENGER_SLOT, itemManager.getItem(Optional.of(player), EnumItem.CHALLENGER));
        player.getInventory().setItem(Constants.SETTINGS_SLOT, itemManager.getItem(Optional.of(player), EnumItem.SETTINGS));
        player.getInventory().setItem(Constants.SPECTATE_SLOT, itemManager.getItem(Optional.of(player), EnumItem.SPECTATE));
        player.getInventory().setItem(Constants.GADGETS_SLOT, itemManager.getItem(Optional.of(player), EnumItem.GADGETS));
        player.getInventory().setItem(Constants.STATS_SLOT, itemManager.getItem(Optional.of(player), EnumItem.STATS));
    }

    public void setQueueLeaveItems(final @NotNull Player player) {
        player.getInventory().setItem(Constants.QUEUE_LEAVE_SLOT, itemManager.getItem(Optional.of(player), EnumItem.QUEUE_LEAVE));
    }
}
