/*
 * Copyright (c) 2021 SkillCode
 *
 * This file is a part of the source code of the
 * AdvancedMLGRush plugin by SkillCode.
 *
 * This class may only be used in compliance with the
 * LICENSE.txt (https://github.com/SkillC0de/AdvancedMLGRush/blob/master/LICENSE.txt).
 *
 * Support: https://discord.skillplugins.com
 */

package net.skillcode.advancedmlgrush.placeholder.placeholders;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.game.queue.Queue1x4;
import net.skillcode.advancedmlgrush.placeholder.Placeholder;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Singleton
public class Queue1x4Placeholder extends Placeholder {

    private final Queue1x4 queue1x4;

    @Inject
    public Queue1x4Placeholder(final @NotNull Queue1x4 queue1x4) {
        this.queue1x4 = queue1x4;
    }

    @Override
    public String identifier() {
        return "%queue_1x4%";
    }

    @Override
    public String onRequest(final @NotNull Optional<Player> optionalPlayer) {
        return String.valueOf(queue1x4.getSize());
    }
}
