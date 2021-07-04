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

package net.skillcode.advancedmlgrush.util;

import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.util.json.JsonLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class LocationWrapper {

    public JsonLocation toJsonLocation(final @NotNull Location location) {
        return new JsonLocation(location.getWorld().getName(), location.getX(), location.getY(),
                location.getZ(), location.getPitch(), location.getYaw());
    }

    public Pair<JsonLocation, JsonLocation> toJsonPair(final @NotNull Pair<Location, Location> pair) {
        return new Pair<>(toJsonLocation(pair.getKey()), toJsonLocation(pair.getValue()));
    }

    public Pair<Location, Location> toPair(final @NotNull Pair<JsonLocation, JsonLocation> pair) {
        return new Pair<>(toLocation(pair.getKey()), toLocation(pair.getValue()));
    }

    public List<JsonLocation> toJsonList(final @NotNull List<Location> list) {
        return list.stream().map(this::toJsonLocation).collect(Collectors.toList());
    }

    public List<Location> toList(final @NotNull List<JsonLocation> list) {
        return list.stream().map(this::toLocation).collect(Collectors.toList());
    }

    public List<Pair<JsonLocation, JsonLocation>> toJsonPairList(final @NotNull List<Pair<Location, Location>> list) {
        return list.stream().map(this::toJsonPair).collect(Collectors.toList());
    }

    public List<Pair<Location, Location>> toPairList(final @NotNull List<Pair<JsonLocation, JsonLocation>> list) {
        return list.stream().map(this::toPair).collect(Collectors.toList());
    }

    public Location toLocation(final @NotNull JsonLocation jsonLocation) {
        return toLocation(jsonLocation, Bukkit.getWorld(jsonLocation.getWorldName()));
    }

    public Location toLocation(final @NotNull JsonLocation jsonLocation, final World world) {
        return new Location(world, jsonLocation.getX(), jsonLocation.getY(), jsonLocation.getZ(),
                jsonLocation.getPitch(), jsonLocation.getYaw());
    }

}
