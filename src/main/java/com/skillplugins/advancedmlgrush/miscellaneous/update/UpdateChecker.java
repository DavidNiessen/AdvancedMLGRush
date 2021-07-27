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

import com.google.gson.JsonParser;
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
        getPluginJson(json -> {
            final String spigotVersion = new JsonParser().parse(json).getAsJsonObject().get("current_version").getAsString();
            final String pluginVersion = plugin.getDescription().getVersion();
            if (getStringAsLong(spigotVersion) > getStringAsLong(pluginVersion)) {
                updateAvailable = true;
                latestVersion = spigotVersion;
                plugin.getLogger().info(String.format(Constants.UPDATE_CHECKER_AVAILABLE, latestVersion));
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

    private long getStringAsLong(final @NotNull String string) {
        StringBuilder stringBuilder = new StringBuilder();
        for (final char character : string.toCharArray()) {
            if (Character.isDigit(character)) {
                stringBuilder.append(character);
            }
        }

        final String result = stringBuilder.toString();
        if (!result.isEmpty()) {
            return Long.parseLong(result);
        }
        return -1;
    }

    private void getPluginJson(final @NotNull Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try (final InputStream inputStream = new URL("https://api.spigotmc.org/simple/0.2/index.php?action=getResource&id=" + Constants.RESOURCE_ID).openStream(); final Scanner scanner = new Scanner(inputStream)) {
                final StringBuilder stringBuilder = new StringBuilder();
                while (scanner.hasNext()) {
                    stringBuilder.append(scanner.next());
                }
                consumer.accept(stringBuilder.toString());
            } catch (IOException exception) {
                plugin.getLogger().warning(String.format(Constants.UPDATE_CHECKER_ERROR, exception.getClass().getSimpleName()));
            }
        });
    }
}