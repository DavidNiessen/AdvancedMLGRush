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

package com.skillplugins.advancedmlgrush.listener.listeners;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.config.configs.MessageConfig;
import com.skillplugins.advancedmlgrush.miscellaneous.Constants;
import com.skillplugins.advancedmlgrush.placeholder.Placeholders;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Singleton
public class PlayerCommandPreprocessListener implements Listener {

    @Inject
    private MessageConfig messageConfig;
    @Inject
    private JavaPlugin javaPlugin;
    @Inject
    private Placeholders placeholders;

    @Inject
    public PlayerCommandPreprocessListener(final @NotNull MessageConfig messageConfig) {
        this.messageConfig = messageConfig;
    }

    @EventHandler
    public void onCommand(final @NotNull PlayerCommandPreprocessEvent event) {
        final String message = event.getMessage().trim();
        if (message.equalsIgnoreCase("/mlgrush")
                || message.equalsIgnoreCase("/advancedmlgrush")) {
            final Player player = event.getPlayer();
            player.sendMessage(placeholders.replace(Optional.of(player),
                    String.format(messageConfig.getString(MessageConfig.PREFIX) + Constants.MLGRUSH_COMMAND_MESSAGE,
                            javaPlugin.getDescription().getVersion())));
            event.setCancelled(true);
        }
    }

}
