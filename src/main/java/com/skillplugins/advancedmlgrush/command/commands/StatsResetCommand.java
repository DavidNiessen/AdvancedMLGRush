/*
 * Copyright (c) 2021 SkillCode
 *
 * This file is a part of the source code of the
 * AdvancedMLGRush plugin by SkillCode.
 *
 * This file may only be used in compliance with the
 * LICENSE.txt (https://github.com/SkillC0de/AdvancedMLGRush/blob/master/LICENSE.txt).
 *
 * Support: http://discord.skillplugins.com
 */

package com.skillplugins.advancedmlgrush.command.commands;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.config.configs.MainConfig;
import com.skillplugins.advancedmlgrush.config.configs.MessageConfig;
import com.skillplugins.advancedmlgrush.sql.data.SQLDataCache;
import com.skillplugins.advancedmlgrush.sql.datasavers.MLGDataSaver;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

@Singleton
public class StatsResetCommand implements CommandExecutor {

    @Inject
    private MessageConfig messageConfig;
    @Inject
    private MainConfig mainConfig;
    @Inject
    private MLGDataSaver mlgDataSaver;
    @Inject
    private SQLDataCache sqlDataCache;

    @Override
    public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] args) {
        if (!(commandSender instanceof Player)) return false;

        final Player player = (Player) commandSender;
        final Optional<Player> optionalPlayer = Optional.of(player);

        if (!player.hasPermission(mainConfig.getString(MainConfig.ADMIN_PERMISSION))) {
            player.sendMessage(messageConfig.getWithPrefix(optionalPlayer, MessageConfig.NO_PERMISSION));
            return false;
        }

        if (args.length < 1) {
            player.sendMessage(messageConfig.getWithPrefix(optionalPlayer, MessageConfig.STATS_RESET_COMMAND_SYNTAX));
            return false;
        }

        sqlDataCache.resetStats(optionalPlayer, args[0]);
        return false;
    }
}
