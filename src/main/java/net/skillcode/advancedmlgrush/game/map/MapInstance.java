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

import com.google.common.collect.BiMap;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import lombok.Getter;
import net.skillcode.advancedmlgrush.annotations.PostConstruct;
import net.skillcode.advancedmlgrush.config.configs.MainConfig;
import net.skillcode.advancedmlgrush.config.configs.MessageConfig;
import net.skillcode.advancedmlgrush.config.configs.SoundConfig;
import net.skillcode.advancedmlgrush.event.EventHandler;
import net.skillcode.advancedmlgrush.event.EventListener;
import net.skillcode.advancedmlgrush.event.EventListenerPriority;
import net.skillcode.advancedmlgrush.event.EventManager;
import net.skillcode.advancedmlgrush.event.events.GameEndEvent;
import net.skillcode.advancedmlgrush.event.events.GameStartEvent;
import net.skillcode.advancedmlgrush.game.buildmode.BuildModeManager;
import net.skillcode.advancedmlgrush.game.map.schematic.SchematicLoader;
import net.skillcode.advancedmlgrush.game.map.world.MapWorldGenerator;
import net.skillcode.advancedmlgrush.game.scoreboard.ScoreboardManager;
import net.skillcode.advancedmlgrush.game.spawn.SpawnFile;
import net.skillcode.advancedmlgrush.game.spawn.SpawnFileLoader;
import net.skillcode.advancedmlgrush.item.EnumItem;
import net.skillcode.advancedmlgrush.item.ItemUtils;
import net.skillcode.advancedmlgrush.item.items.IngameItems;
import net.skillcode.advancedmlgrush.item.items.LobbyItems;
import net.skillcode.advancedmlgrush.libs.xseries.XMaterial;
import net.skillcode.advancedmlgrush.sound.SoundUtil;
import net.skillcode.advancedmlgrush.sql.data.SQLDataCache;
import net.skillcode.advancedmlgrush.util.ListBuilder;
import net.skillcode.advancedmlgrush.util.LocationUtils;
import net.skillcode.advancedmlgrush.util.LocationWrapper;
import net.skillcode.advancedmlgrush.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class MapInstance implements EventHandler {

    private final List<Location> placedBlocks = new ArrayList<>();
    @Getter
    private final List<Player> spectators = new CopyOnWriteArrayList<>();
    @Getter
    //Tasks which will be executed after the map is loaded
    private final List<Runnable> tasks = new CopyOnWriteArrayList<>();

    @Getter
    private final MapTemplate mapTemplate;
    @Getter
    private final MapData mapData;
    //<player, index>
    private final BiMap<Player, Integer> players;
    @Getter
    private final int rounds;
    @Getter
    private boolean loaded = false;

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
    private ItemUtils itemUtils;
    @Inject
    private JavaPlugin javaPlugin;
    @Inject
    private ScoreboardManager scoreboardManager;
    @Inject
    private BuildModeManager buildModeManager;

    @Inject
    public MapInstance(final @Assisted @NotNull MapTemplate mapTemplate,
                       final @Assisted @NotNull MapData mapData,
                       final @Assisted @NotNull BiMap<Player, Integer> players,
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

    public Optional<Player> getPlayer(final int index) {
        return Optional.ofNullable(players.inverse().getOrDefault(index, null));
    }

    public Optional<Player> getPlayerByListIndex(final int index) {
        final List<Player> playerList = ListBuilder.create(Player.class).add(players.keySet()).build();
        return Optional.ofNullable(index < playerList.size() ? playerList.get(index) : null);
    }

    @Override
    public void registerListeners(final @NotNull List<EventListener<?>> eventListeners) {
        eventListeners.add(new EventListener<PlayerQuitEvent>(PlayerQuitEvent.class, EventListenerPriority.HIGH) {
            @Override
            protected void onEvent(final @NotNull PlayerQuitEvent event) {
                final Player player = event.getPlayer();
                if (players.containsKey(player)) {
                    quitMap(event.getPlayer());
                }
            }
        });

        eventListeners.add(new EventListener<EntityDamageByEntityEvent>(EntityDamageByEntityEvent.class, EventListenerPriority.MEDIUM) {
            @Override
            protected void onEvent(final @NotNull EntityDamageByEntityEvent event) {
                if (event.getDamager() instanceof Player
                        && event.getEntity() instanceof Player) {

                    final Player damager = (Player) event.getDamager();
                    final Player entity = (Player) event.getEntity();

                    if (players.containsKey(damager)
                            && players.containsKey(entity)) {
                        if (loaded) {
                            event.setCancelled(false);
                            event.setDamage(0);
                        }
                    }
                }
            }
        });
        eventListeners.add(new EventListener<BlockBreakEvent>(BlockBreakEvent.class, EventListenerPriority.MEDIUM) {
            @Override
            protected void onEvent(final @NotNull BlockBreakEvent event) {
                final Player player = event.getPlayer();
                if (players.containsKey(player)) {
                    if (loaded) {
                        final Location blockLocation = event.getBlock().getLocation();

                        for (int index : players.values()) {
                            final Pair<Location, Location> pair = mapData.getBeds().get(index);
                            if (locationUtils.compare(pair.getKey(), blockLocation, world)
                                    || locationUtils.compare(pair.getValue(), blockLocation, world)) {

                                final Optional<Player> optionalPlayer = Optional.of(player);


                                if (index == players.get(player)) {
                                    player.sendMessage(messageConfig.getWithPrefix(optionalPlayer, MessageConfig.BREAK_OWN_BED));
                                } else {
                                    final int playerIndex = players.get(player);
                                    mapStats.increaseScore(playerIndex);
                                    sqlDataCache.getSQLData(player).increaseBeds();
                                    final int score = mapStats.getScore(playerIndex);
                                    if (score == rounds) {
                                        endGame(player);
                                    } else {
                                        clearBlocks();
                                        teleportToPlayerSpawn(players.keySet());
                                        scoreboardManager.updateScoreboard(players.keySet());
                                        players.keySet().forEach(player1 -> {
                                            player1.getInventory().clear();
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
            }
        });
        eventListeners.add(new EventListener<BlockPlaceEvent>(BlockPlaceEvent.class, EventListenerPriority.MEDIUM) {
            @Override
            protected void onEvent(final @NotNull BlockPlaceEvent event) {
                final Player player = event.getPlayer();

                if (players.containsKey(player)) {
                    if (loaded) {
                        final Location location = event.getBlock().getLocation();

                        if (player.getItemInHand().isSimilar(ingameItems.getBlock(player).getKey())
                                && location.getY() <= mapData.getMaxBuild()
                                && (event.getBlockReplacedState() == null
                                || event.getBlockReplacedState().getType() == null
                                || event.getBlockReplacedState().getType() == Material.AIR)) {

                            boolean isSpawnLocation = false;
                            for (final Location spawnLocation : mapData.getSpawns()) {

                                final Location higherLocation = spawnLocation.clone();
                                higherLocation.setY(higherLocation.getY() + 1);

                                if (locationUtils.compare(location, spawnLocation, world)
                                        || locationUtils.compare(location, higherLocation, world)) {
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
            }
        });
        eventListeners.add(new EventListener<PlayerMoveEvent>(PlayerMoveEvent.class, EventListenerPriority.MEDIUM) {
            @Override
            protected void onEvent(final @NotNull PlayerMoveEvent event) {
                final Player player = event.getPlayer();

                if (players.containsKey(player)
                        || spectators.contains(player)) {
                    if (!buildModeManager.isInBuildMode(player)) {
                        if (loaded) {

                            if (event.getTo().getY() <= mapData.getDeathHeight()) {
                                if (players.containsKey(player)) {
                                    teleportToPlayerSpawn(player);
                                    soundUtil.playSound(player, SoundConfig.DEATH);
                                } else if (spectators.contains(player)) {
                                    teleportToSpectatorSpawn(player);
                                }
                            }

                            if (players.containsKey(player)) {
                                final List<Entity> list = player.getNearbyEntities(1, 1, 1);
                                list.forEach(entity -> {
                                    if (entity instanceof Player) {
                                        final Player player1 = (Player) entity;
                                        if (spectators.contains(player1)) {
                                            player1.setVelocity(player1.getLocation().getDirection().multiply(-0.5));
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
            }
        });
        eventListeners.add(new EventListener<PlayerInteractEvent>(PlayerInteractEvent.class, EventListenerPriority.MEDIUM) {
            @Override
            protected void onEvent(final @NotNull PlayerInteractEvent event) {
                if (event.getAction() == Action.RIGHT_CLICK_AIR
                        || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    final Player player = event.getPlayer();
                    if (itemUtils.compare(player.getItemInHand(), EnumItem.SPECTATE_LEAVE, Optional.of(player))) {
                        removeSpectator(player);
                    }

                    final XMaterial xMaterial = XMaterial.BED;
                    final Block clickedBlock = event.getClickedBlock();
                    final List<String> materialNames = ListBuilder.create(String.class).add(xMaterial.name())
                            .add(xMaterial.getLegacy()).build();

                    if (clickedBlock != null
                            && clickedBlock.getType() != null
                            && (materialNames.contains(clickedBlock.getType().name()))) {
                        event.setCancelled(true);
                    }
                }
            }
        });
    }

    public void addSpectator(final @NotNull Player player) {
        spectators.add(player);
        player.getInventory().clear();
        player.setAllowFlight(true);
        player.setFlying(true);
        player.spigot().setCollidesWithEntities(false);

        teleportToSpectatorSpawn(player);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, true, false));
        Bukkit.getOnlinePlayers().forEach(player1 -> player1.hidePlayer(player));

        Bukkit.getScheduler().scheduleSyncDelayedTask(javaPlugin, () -> ingameItems.setSpectatorItems(player), 10);
    }

    public void removeSpectator(final @NotNull Player player) {
        spectators.remove(player);
        player.getInventory().clear();
        player.setAllowFlight(false);
        player.setFlying(false);
        player.spigot().setCollidesWithEntities(true);
        player.getActivePotionEffects().clear();

        teleportToSpawn(player);
        Bukkit.getOnlinePlayers().forEach(player1 -> player1.showPlayer(player));

        lobbyItems.setLobbyItems(player);
    }

    private void prepareMap() {
        players.keySet().forEach(player -> player.sendMessage(messageConfig.getWithPrefix(Optional.of(player), MessageConfig.MAP_GENERATE)));
        world = mapWorldGenerator.createWorld();

        schematicLoader.load(mapData.getBlocks(), players.keySet(), world, this::startGame);
    }

    private void startGame() {
        teleportToPlayerSpawn(players.keySet());
        loaded = true;
        tasks.forEach(Runnable::run);
        scoreboardManager.updateScoreboard(players.keySet());
        players.keySet().forEach(player -> {
            ingameItems.setIngameItems(player);
            player.sendMessage(messageConfig.getWithPrefix(Optional.of(player), MessageConfig.GAME_START));
        });
        Bukkit.getPluginManager().callEvent(new GameStartEvent(this));
    }

    private void endGame(final @NotNull Player winner) {
        Bukkit.getPluginManager().callEvent(new GameEndEvent(this, winner));
        sqlDataCache.getSQLData(winner).increaseWins();
        spectators.forEach(this::removeSpectator);
        players.keySet().forEach(player -> {
            final Optional<Player> optionalPlayer = Optional.of(player);
            if (!player.equals(winner)) {
                sqlDataCache.getSQLData(player).increaseLoses();
            }
            player.sendMessage(messageConfig.getWithPrefix(optionalPlayer, MessageConfig.GAME_END).replace("%winner%", winner.getName()));

            mapInstanceManager.unregister(player);

            teleportToSpawn(player);
            scoreboardManager.updateScoreboard(player);

        });
        eventManager.unregister(this);
        players.clear();
        if (isLoaded()) {
            mapWorldGenerator.deleteWorld(world);
        } else {
            tasks.add(() -> mapWorldGenerator.deleteWorld(world));
        }
    }

    public void quitMap(final @NotNull Player player) {
        players.remove(player);
        mapInstanceManager.unregister(player);
        sqlDataCache.getSQLData(player).increaseLoses();

        teleportToSpawn(player);
        scoreboardManager.updateScoreboard(player);
        if (players.size() == 1) {
            endGame(players.keySet().iterator().next());
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
                location.getPitch(), location.getYaw()));
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

    private void teleportToSpectatorSpawn(final @NotNull Player player) {
        teleport(player, mapData.getSpectatorSpawn());
    }

    private void teleportToPlayerSpawn(final @NotNull Iterable<Player> iterable) {
        iterable.forEach(this::teleportToPlayerSpawn);
    }

    private void teleportToPlayerSpawn(final @NotNull Player player) {
        if (players.containsKey(player)) {
            teleport(player, mapData.getSpawns().get(players.get(player)));
        }
    }
}
