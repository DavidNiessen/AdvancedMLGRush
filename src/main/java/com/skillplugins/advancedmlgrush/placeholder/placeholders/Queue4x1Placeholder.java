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

package com.skillplugins.advancedmlgrush.placeholder.placeholders;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.game.queue.Queue4x1;
import com.skillplugins.advancedmlgrush.placeholder.Placeholder;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Singleton
public class Queue4x1Placeholder extends Placeholder {

    private final Queue4x1 queue4X1;

    @Inject
    public Queue4x1Placeholder(final @NotNull Queue4x1 queue4X1) {
        this.queue4X1 = queue4X1;
    }

    @Override
    public String identifier() {
        return "%queue_4x1%";
    }

    @Override
    public String onRequest(final @NotNull Optional<Player> optionalPlayer) {
        return String.valueOf(queue4X1.getSize());
    }
}
