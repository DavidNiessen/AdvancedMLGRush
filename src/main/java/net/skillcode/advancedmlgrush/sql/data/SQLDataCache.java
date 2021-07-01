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

package net.skillcode.advancedmlgrush.sql.data;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.event.EventHandler;
import net.skillcode.advancedmlgrush.event.EventListener;
import net.skillcode.advancedmlgrush.event.events.PlayerDataLoadEvent;
import net.skillcode.advancedmlgrush.exception.ExceptionHandler;
import net.skillcode.advancedmlgrush.miscellaneous.registrable.Registrable;
import net.skillcode.advancedmlgrush.sql.datasavers.MLGDataSaver;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
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

    public boolean isLoaded(final @NotNull Player player) {
        return cache.containsKey(player);
    }

    @Override
    public void registerListeners(final @NotNull List<EventListener<?>> eventListeners) {
        eventListeners.add(new EventListener<PlayerJoinEvent>(PlayerJoinEvent.class) {
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
                                exceptionHandler.handle(e);
                            } finally {
                                cancel();
                            }
                        }
                    }
                }.runTaskTimer(javaPlugin, 5, 5);
            }
        });
        eventListeners.add(new EventListener<PlayerQuitEvent>(PlayerQuitEvent.class) {
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
