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
import com.skillplugins.advancedmlgrush.config.configs.MessageConfig;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

public class SudoCommand implements CommandExecutor {

    private final MessageConfig messageConfig;

    @Inject
    public SudoCommand(final @NotNull MessageConfig messageConfig) {
        this.messageConfig = messageConfig;
    }

    @Override
    public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] args) {
        if (commandSender instanceof Player) {
            commandSender.sendMessage(messageConfig.getWithPrefix(Optional.of((Player) commandSender),
                    MessageConfig.SUDO_COMMAND_PLAYER_MESSAGE));
            return false;
        }

        if (args.length != 0) {

            final Queue<String> queue = new LinkedList<>(Arrays.asList(args));
            final Player player = Bukkit.getPlayer(queue.poll());
            if (player != null) {
                final StringBuilder stringBuilder = new StringBuilder();
                queue.forEach(string -> stringBuilder.append(string).append(" "));
                Bukkit.dispatchCommand(player, stringBuilder.toString());
            }
        }
        return false;
    }
}
