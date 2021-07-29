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

package com.skillplugins.advancedmlgrush.event.events;

import com.skillplugins.advancedmlgrush.util.Pair;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
public class SchematicLoadEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Iterable<Player> players;
    private final World world;
    private final List<Pair<Location, Location>> bedLocations;

    public SchematicLoadEvent(final @NotNull Iterable<Player> players,
                              final @NotNull World world,
                              final @NotNull List<Pair<Location, Location>> bedLocations) {
        this.players = players;
        this.world = world;
        this.bedLocations = bedLocations;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
