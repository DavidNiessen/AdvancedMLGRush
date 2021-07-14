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

package com.skillplugins.advancedmlgrush.game.map.schematic.serializer.serializers;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.exception.ExceptionHandler;
import com.skillplugins.advancedmlgrush.game.map.schematic.StorableBlock;
import com.skillplugins.advancedmlgrush.game.map.schematic.serializer.BlockSerializer;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Singleton
public class JsonSerializer implements BlockSerializer {

    private final Gson gson = new Gson();

    private final ExceptionHandler exceptionHandler;


    @Inject
    public JsonSerializer(final @NotNull ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public String id() {
        return "JSON";
    }

    @Override
    public Optional<String> serialize(final @NotNull List<StorableBlock> blockList) {
        return Optional.ofNullable(gson.toJson(blockList));
    }

    @Override
    public Optional<List<StorableBlock>> deserialize(final @NotNull String string) {
        final Type type = new TypeToken<List<StorableBlock>>() {}.getType();
        return Optional.ofNullable(gson.fromJson(string, type));
    }
}
