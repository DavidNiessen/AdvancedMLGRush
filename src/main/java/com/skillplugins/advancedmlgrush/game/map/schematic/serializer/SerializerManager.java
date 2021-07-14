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

package com.skillplugins.advancedmlgrush.game.map.schematic.serializer;

import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.game.map.schematic.serializer.serializers.JsonSerializer;
import com.skillplugins.advancedmlgrush.util.Initializer;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class SerializerManager implements Initializer {

    private final Map<String, BlockSerializer> serializerMap = new ConcurrentHashMap<>();

    public Optional<BlockSerializer> getSerializer(final @NotNull String serializer) {
        return Optional.ofNullable(serializerMap.getOrDefault(serializer.toUpperCase(Locale.ROOT), null));
    }

    @Override
    public void init(final @NotNull Injector injector) {
        register(injector.getInstance(JsonSerializer.class));
    }

    private void register(final @NotNull BlockSerializer blockSerializer) {
        serializerMap.put(blockSerializer.id().toUpperCase(Locale.ROOT), blockSerializer);
    }

}
