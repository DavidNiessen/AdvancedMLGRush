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

package net.skillcode.advancedmlgrush.game.map;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import lombok.Getter;
import net.skillcode.advancedmlgrush.annotations.PostConstruct;
import net.skillcode.advancedmlgrush.config.configs.MainConfig;
import net.skillcode.advancedmlgrush.config.configs.MessageConfig;
import net.skillcode.advancedmlgrush.config.configs.SoundConfig;
import net.skillcode.advancedmlgrush.event.EventHandler;
import net.skillcode.advancedmlgrush.event.EventListener;
import net.skillcode.advancedmlgrush.event.EventManager;
import net.skillcode.advancedmlgrush.game.map.schematic.SchematicLoader;
import net.skillcode.advancedmlgrush.game.map.world.MapWorldGenerator;
import net.skillcode.advancedmlgrush.game.spawn.SpawnFile;
import net.skillcode.advancedmlgrush.game.spawn.SpawnFileLoader;
import net.skillcode.advancedmlgrush.item.items.IngameItems;
import net.skillcode.advancedmlgrush.item.items.LobbyItems;
import net.skillcode.advancedmlgrush.sound.SoundUtil;
import net.skillcode.advancedmlgrush.sql.data.SQLDataCache;
import net.skillcode.advancedmlgrush.util.LocationUtils;
import net.skillcode.advancedmlgrush.util.LocationWrapper;
import net.skillcode.advancedmlgrush.util.Pair;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class MapInstance implements EventHandler {

    private final List<Location> placedBlocks = new ArrayList<>();
    private final List<Player> spectators = new CopyOnWriteArrayList<>();

    private final MapTemplate mapTemplate;
    private final MapData mapData;
    private final List<Player> players;
    private final int rounds;

    @Getter
    private World world;

    @Inject
    private MapWorldGenerator mapWorldGenerator;
    @Inject
    private EventManager eventManager;
    @Inject
    private SchematicLoader schematicLoader;
    @Inject
    private MessageConfig messageConfig;
    @Inject
    private SQLDataCache sqlDataCache;
    @Inject
    private SpawnFileLoader spawnFileLoader;
    @Inject
    private LocationWrapper locationWrapper;
    @Inject
    private MapInstanceManager mapInstanceManager;
    @Inject
    private IngameItems ingameItems;
    @Inject
    private LobbyItems lobbyItems;
    @Inject
    private LocationUtils locationUtils;
    @Inject
    @Getter
    private MapStats mapStats;
    @Inject
    private MainConfig mainConfig;
    @Inject
    private SoundUtil soundUtil;

    @Inject
    public MapInstance(final @Assisted @NotNull MapTemplate mapTemplate,
                       final @Assisted @NotNull MapData mapData,
                       final @Assisted @NotNull List<Player> players,
                       final @Assisted int rounds) {
        this.mapTemplate = mapTemplate;
        this.mapData = mapData;
        this.players = players;
        this.rounds = rounds;
    }

    @PostConstruct
    public void init() {
        prepareMap();
        eventManager.registerEventListeners(this);
    }

    @Override
    public void registerListeners(final @NotNull List<EventListener<?>> eventListeners) {
        eventListeners.add(new EventListener<PlayerQuitEvent>(PlayerQuitEvent.class) {
            @Override
            protected void onEvent(final @NotNull PlayerQuitEvent event) {
                quitMap(event.getPlayer());
            }
        });

        eventListeners.add(new EventListener<EntityDamageByEntityEvent>(EntityDamageByEntityEvent.class) {
            @Override
            protected void onEvent(final @NotNull EntityDamageByEntityEvent event) {
                if (event.getDamager() instanceof Player
                        && event.getEntity() instanceof Player) {

                    final Player damager = (Player) event.getDamager();
                    final Player entity = (Player) event.getEntity();

                    if (players.contains(damager)
                            && players.contains(entity)) {
                        event.setCancelled(false);
                        event.setDamage(0);
                    }
                }
            }
        });
        eventListeners.add(new EventListener<BlockBreakEvent>(BlockBreakEvent.class) {
            @Override
            protected void onEvent(final @NotNull BlockBreakEvent event) {
                final Player player = event.getPlayer();
                if (players.contains(player)) {
                    final Location blockLocation = event.getBlock().getLocation();

                    for (int i = 0; i < players.size(); i++) {
                        final Pair<Location, Location> pair = mapData.getBeds().get(i);
                        if (locationUtils.compare(pair.getKey(), blockLocation, world)
                                || locationUtils.compare(pair.getValue(), blockLocation, world)) {

                            final Optional<Player> optionalPlayer = Optional.of(player);
                            final int index = players.indexOf(player);

                            if (index == i) {
                                player.sendMessage(messageConfig.getWithPrefix(optionalPlayer, MessageConfig.BREAK_OWN_BED));
                            } else {
                                mapStats.increaseScore(index);
                                sqlDataCache.getSQLData(player).increaseBeds();
                                final int score = mapStats.getScores().get(index);
                                if (score == rounds) {
                                    endGame(player);
                                } else {
                                    clearBlocks();
                                    teleportToPlayerSpawn(players);
                                    players.forEach(player1 -> {
                                        player.getInventory().clear();
                                        ingameItems.setIngameItems(player1);
                                    });
                                }
                            }
                            break;
                        }
                    }

                    if (placedBlocks.contains(blockLocation)) {
                        event.getBlock().getDrops().clear();
                        event.setCancelled(false);
                    }
                }
            }
        });
        eventListeners.add(new EventListener<BlockPlaceEvent>(BlockPlaceEvent.class) {
            @Override
            protected void onEvent(final @NotNull BlockPlaceEvent event) {
                final Player player = event.getPlayer();

                if (players.contains(player)) {
                    final Location location = event.getBlock().getLocation();
                    final Location higherLocation = location.clone();
                    higherLocation.setY(higherLocation.getY() + 1);

                    if (player.getItemInHand().isSimilar(ingameItems.getBlock(player).getKey())
                            && location.getY() <= mapData.getMaxBuild()
                            && (event.getBlockReplacedState() == null
                            || event.getBlockReplacedState().getType() == null
                            || event.getBlockReplacedState().getType() == Material.AIR)) {

                        boolean isSpawnLocation = false;
                        for (final Location spawnLocation : mapData.getSpawns()) {
                            if (locationUtils.compare(location, spawnLocation, world)
                                    || locationUtils.compare(higherLocation, spawnLocation, world)) {
                                isSpawnLocation = true;
                            }
                        }

                        if (!isSpawnLocation) {
                            event.setCancelled(false);
                            final boolean infiniteBlocks = mainConfig.getBoolean(MainConfig.INFINITE_BLOCKS);
                            if (infiniteBlocks) {
                                player.getItemInHand().setAmount(mainConfig.getInt(MainConfig.BLOCK_AMOUNT));
                            }
                            placedBlocks.add(location);
                        }
                    }
                }
            }
        });
        eventListeners.add(new EventListener<PlayerMoveEvent>(PlayerMoveEvent.class) {
            @Override
            protected void onEvent(final @NotNull PlayerMoveEvent event) {
                final Player player = event.getPlayer();
                if (event.getTo().getY() <= mapData.getDeathHeight()) {
                    teleportToPlayerSpawn(player);
                    soundUtil.playSound(player, SoundConfig.DEATH);
                }
            }
        });
    }

    private void prepareMap() {
        players.forEach(player -> player.sendMessage(messageConfig.getWithPrefix(Optional.of(player), MessageConfig.MAP_GENERATE)));
        world = mapWorldGenerator.createWorld();

        schematicLoader.load(mapData.getBlocks(), world, this::startGame);
    }

    private void startGame() {
        teleportToPlayerSpawn(players);
        players.forEach(ingameItems::setIngameItems);
    }

    private void endGame(final @NotNull Player winner) {
        sqlDataCache.getSQLData(winner).increaseWins();
        final Optional<SpawnFile> optional = spawnFileLoader.getSpawnFileOptional();
        players.forEach(player -> {
            final Optional<Player> optionalPlayer = Optional.of(player);
            if (!player.equals(winner)) {
                sqlDataCache.getSQLData(player).increaseLoses();
            }
            player.sendMessage(messageConfig.getWithPrefix(optionalPlayer, MessageConfig.GAME_END).replace("%winner%", winner.getName()));

            mapInstanceManager.unregister(player);

            teleportToSpawn(player);

        });
        eventManager.unregister(this);
        mapWorldGenerator.deleteWorld(world);
    }

    public void quitMap(final @NotNull Player player) {
        players.remove(player);
        mapInstanceManager.unregister(player);
        sqlDataCache.getSQLData(player).increaseLoses();

        teleportToSpawn(player);
        if (players.size() == 1) {
            endGame(players.get(0));
        }
    }

    private void clearBlocks() {
        for (final Location placedBlock : placedBlocks) {
            placedBlock.getBlock().setType(Material.AIR);
        }
        placedBlocks.clear();
    }

    private void teleport(final @NotNull Player player, final @NotNull Location location) {
        player.teleport(new Location(world, location.getX(), location.getY(), location.getZ(),
                location.getYaw(), location.getPitch()));
    }

    private void teleportToSpawn(final @NotNull Player player) {
        final Optional<SpawnFile> optional = spawnFileLoader.getSpawnFileOptional();
        if (optional.isPresent()) {
            player.getInventory().clear();
            player.teleport(locationWrapper.toLocation(optional.get().getJsonLocation()));
            lobbyItems.setLobbyItems(player);
        } else {
            player.kickPlayer(messageConfig.getWithPrefix(Optional.of(player), MessageConfig.ERROR));
        }
    }

    private void teleportToPlayerSpawn(final @NotNull List<Player> list) {
        list.forEach(this::teleportToPlayerSpawn);
    }

    private void teleportToPlayerSpawn(final @NotNull Player player) {
        if (players.contains(player)) {
            teleport(player, mapData.getSpawns().get(players.indexOf(player)));
        }
    }
}
