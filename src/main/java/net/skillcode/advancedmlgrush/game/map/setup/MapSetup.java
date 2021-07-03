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

package net.skillcode.advancedmlgrush.game.map.setup;

import com.google.inject.Inject;
import net.skillcode.advancedmlgrush.config.configs.MessageConfig;
import net.skillcode.advancedmlgrush.event.EventHandler;
import net.skillcode.advancedmlgrush.event.EventListener;
import net.skillcode.advancedmlgrush.event.EventListenerPriority;
import net.skillcode.advancedmlgrush.game.map.file.MapFile;
import net.skillcode.advancedmlgrush.game.map.file.MapFileLoader;
import net.skillcode.advancedmlgrush.game.map.setup.step.SetupStep;
import net.skillcode.advancedmlgrush.game.map.setup.step.SetupSteps;
import net.skillcode.advancedmlgrush.miscellaneous.registrable.Registrable;
import net.skillcode.advancedmlgrush.util.Pair;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class MapSetup implements Registrable, EventHandler {

    private final Map<Player, Pair<Integer, List<Object>>> map = new ConcurrentHashMap<>();
    private final Map<Player, String> nameMap = new ConcurrentHashMap<>();

    @Inject
    private MessageConfig messageConfig;
    @Inject
    private MapFileLoader mapFileLoader;

    public void startSetup(final @NotNull Player player, final @NotNull String mapName) {
        map.put(player, new Pair<>(0, new ArrayList<>()));
        nameMap.put(player, mapName);

        final Optional<SetupStep<Object>> optionalSetupStep = setupSteps().getSetupStep(0);

        optionalSetupStep.ifPresent(setupStep ->
                player.sendMessage(messageConfig.getWithPrefix(Optional.of(player), setupStep.configPath())));
    }

    public boolean isSettingUp(final @NotNull Player player) {
        return map.containsKey(player);
    }

    public boolean nextStep(final @NotNull Player player) {
        if (!map.containsKey(player)) {
            return false;
        }

        final Pair<Integer, List<Object>> pair = map.get(player);

        int index = pair.getKey();
        final List<Object> list = pair.getValue();

        setupSteps().getSetupStep(index).ifPresent(setupStep -> list.add(setupStep.onPerform(player)));

        map.put(player, new Pair<>(++index, list));

        final Optional<SetupStep<Object>> optionalSetupStep = setupSteps().getSetupStep(index);
        final Optional<Player> optionalPlayer = Optional.of(player);

        if (optionalSetupStep.isPresent()) {
            final SetupStep<?> setupStep = optionalSetupStep.get();
            player.sendMessage(messageConfig.getWithPrefix(optionalPlayer, setupStep.configPath()));
        } else {
            final Optional<MapFile> optional = createMapFile(list, nameMap.getOrDefault(player, "null"));
            if (optional.isPresent()) {
                mapFileLoader.createMapFile(optional.get());
                player.sendMessage(messageConfig.getWithPrefix(optionalPlayer, MessageConfig.SETUP_FINISH));
            } else {
                player.sendMessage(messageConfig.getWithPrefix(optionalPlayer, MessageConfig.ERROR));
            }
            map.remove(player);
        }
        return true;
    }

    @Override
    public void registerListeners(final @NotNull List<EventListener<?>> eventListeners) {
        eventListeners.add(new EventListener<AsyncPlayerChatEvent>(AsyncPlayerChatEvent.class, EventListenerPriority.MEDIUM) {
            @Override
            protected void onEvent(final @NotNull AsyncPlayerChatEvent event) {
                final Player player = event.getPlayer();

                if (event.getMessage().toLowerCase(Locale.ROOT).equals("next")
                        && isSettingUp(player)) {
                    event.setCancelled(true);
                    nextStep(player);
                }
            }
        });
    }

    @Override
    public void unregister(final @NotNull Player player) {
        map.remove(player);
        nameMap.remove(player);
    }

    abstract SetupSteps setupSteps();

    abstract Optional<MapFile> createMapFile(final @NotNull List<Object> objects, final @NotNull String mapName);

}
