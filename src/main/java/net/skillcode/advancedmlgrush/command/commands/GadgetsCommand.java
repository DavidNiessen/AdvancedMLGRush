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

package net.skillcode.advancedmlgrush.command.commands;

import com.google.inject.Inject;
import net.skillcode.advancedmlgrush.config.configs.MessageConfig;
import net.skillcode.advancedmlgrush.inventory.inventories.GadgetsInventory;
import net.skillcode.advancedmlgrush.sql.data.SQLDataCache;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public class GadgetsCommand implements CommandExecutor {

    @Inject
    private SQLDataCache sqlDataCache;
    @Inject
    private MessageConfig messageConfig;
    @Inject
    private GadgetsInventory gadgetsInventory;

    @Override
    public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        final Player player = (Player) commandSender;

        if (!sqlDataCache.isLoaded(player)) {
            player.sendMessage(messageConfig.getWithPrefix(Optional.of(player), MessageConfig.LOADING_DATA));
            return false;
        }

        gadgetsInventory.open(player);
        return false;
    }

}
