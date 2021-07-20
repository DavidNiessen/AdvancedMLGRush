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

package com.skillplugins.advancedmlgrush.sql.data;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.config.configs.MessageConfig;
import com.skillplugins.advancedmlgrush.event.EventHandler;
import com.skillplugins.advancedmlgrush.event.EventListener;
import com.skillplugins.advancedmlgrush.event.EventListenerPriority;
import com.skillplugins.advancedmlgrush.event.events.PlayerDataLoadEvent;
import com.skillplugins.advancedmlgrush.exception.ExceptionHandler;
import com.skillplugins.advancedmlgrush.miscellaneous.registrable.Registrable;
import com.skillplugins.advancedmlgrush.sql.datasavers.MLGDataSaver;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Singleton
public class SQLDataCache implements Registrable, EventHandler {

    private final Map<Player, CachedSQLData> cache = new ConcurrentHashMap<>();

    @Inject
    private JavaPlugin javaPlugin;
    @Inject
    private MLGDataSaver mlgDataSaver;
    @Inject
    private ExceptionHandler exceptionHandler;
    @Inject
    private MessageConfig messageConfig;


    @Override
    public void unregister(final @NotNull Player player) {
        cache.remove(player);
    }

    public CachedSQLData getSQLData(final @NotNull Player player) {
        if (cache.containsKey(player)) {
            return cache.get(player);
        }
        return CachedSQLData.DEFAULT_SQL_DATA;
    }

    public void resetStats(final @NotNull Optional<Player> optionalExecutor, final @NotNull String playerName) {
        optionalExecutor.ifPresent(executor -> executor.sendMessage(messageConfig.getWithPrefix(optionalExecutor, MessageConfig.STATS_RESET)));
        mlgDataSaver.resetStats(playerName, state -> {
            if (optionalExecutor.isPresent()) {
                final Player executor = optionalExecutor.get();
                switch (state) {
                    case SUCCEED:
                        executor.sendMessage(messageConfig.getWithPrefix(optionalExecutor, MessageConfig.STATS_RESET_SUCCESS));

                        final Player player = Bukkit.getPlayerExact(playerName);
                        if (player != null
                                && player.isOnline()) {

                            final CachedSQLData cachedSQLData = getSQLData(player);
                            cachedSQLData.setStatsWins(0);
                            cachedSQLData.setStatsLoses(0);
                            cachedSQLData.setStatsBeds(0);
                        }
                        break;
                    case UNKNOWN_PLAYER:
                        executor.sendMessage(messageConfig.getWithPrefix(optionalExecutor, MessageConfig.UNKNOWN_PLAYER));
                        break;
                    default:
                        executor.sendMessage(messageConfig.getWithPrefix(optionalExecutor, MessageConfig.ERROR));
                }
            }
        });
    }

    public boolean isLoaded(final @NotNull Player player) {
        return cache.containsKey(player);
    }

    @Override
    public void registerListeners(final @NotNull List<EventListener<?>> eventListeners) {
        eventListeners.add(new EventListener<PlayerJoinEvent>(PlayerJoinEvent.class, EventListenerPriority.MEDIUM) {
            @Override
            protected void onEvent(final @NotNull PlayerJoinEvent event) {
                final Player player = event.getPlayer();
                final Future<CachedSQLData> future = mlgDataSaver.getPlayerData(player);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (player == null
                                || !player.isOnline()) {
                            cancel();
                        } else if (future.isDone()) {
                            try {
                                final CachedSQLData cachedSQLData = future.get();
                                cache.put(player, cachedSQLData);
                                javaPlugin.getServer().getPluginManager().callEvent(
                                        new PlayerDataLoadEvent(player, cachedSQLData.isDefaultData(), cachedSQLData));
                            } catch (ExecutionException | InterruptedException e) {
                                exceptionHandler.handleUnexpected(e);
                            } finally {
                                cancel();
                            }
                        }
                    }
                }.runTaskTimer(javaPlugin, 5, 5);
            }
        });
        eventListeners.add(new EventListener<PlayerQuitEvent>(PlayerQuitEvent.class, EventListenerPriority.LOW) {
            @Override
            protected void onEvent(final @NotNull PlayerQuitEvent event) {
                final Player player = event.getPlayer();
                if (isLoaded(player)) {
                    mlgDataSaver.updatePlayer(player, cache.get(player));
                }
            }
        });
    }
}
