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

package com.skillplugins.advancedmlgrush.miscellaneous.update;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.config.configs.MainConfig;
import com.skillplugins.advancedmlgrush.config.configs.MessageConfig;
import com.skillplugins.advancedmlgrush.event.EventHandler;
import com.skillplugins.advancedmlgrush.event.EventListener;
import com.skillplugins.advancedmlgrush.event.EventListenerPriority;
import com.skillplugins.advancedmlgrush.miscellaneous.Constants;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Consumer;

@Singleton
public class UpdateChecker implements EventHandler {

    @Inject
    private JavaPlugin plugin;
    @Inject
    private MessageConfig messageConfig;
    @Inject
    private MainConfig mainConfig;

    private boolean updateAvailable = false;
    private String latestVersion = "ERROR";

    public void checkForUpdates() {
        plugin.getLogger().info(Constants.UPDATE_CHECKER);
        getVersion(spigotVersion -> {
            final String pluginVersion = plugin.getDescription().getVersion();
            if (!pluginVersion.equals(spigotVersion)) {
                updateAvailable = true;
                latestVersion = spigotVersion;
                plugin.getLogger().info(String.format(Constants.UPDATE_CHECKER_AVAILABLE, spigotVersion));
            }
        });
    }

    private void getVersion(final @NotNull Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + Constants.RESOURCE_ID).openStream(); Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                }
            } catch (IOException exception) {
                plugin.getLogger().warning(String.format(Constants.UPDATE_CHECKER_ERROR, exception.getClass().getSimpleName()));
                exception.printStackTrace();
            }
        });
    }

    @Override
    public void registerListeners(final @NotNull List<EventListener<?>> eventListeners) {
        eventListeners.add(new EventListener<PlayerJoinEvent>(PlayerJoinEvent.class, EventListenerPriority.LOW) {
            @Override
            protected void onEvent(final @NotNull PlayerJoinEvent event) {
                final Player player = event.getPlayer();
                if (updateAvailable
                        && mainConfig.getBoolean(MainConfig.SEND_UPDATE_MESSAGE)
                        && player.hasPermission(mainConfig.getString(MainConfig.ADMIN_PERMISSION))) {
                    player.sendMessage(messageConfig.getWithPrefix(Optional.of(player), MessageConfig.UPDATE_AVAILABLE)
                            .replace("%version%", latestVersion));
                }
            }
        });
    }
}