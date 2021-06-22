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

import net.skillcode.advancedmlgrush.placeholder.Placeholder;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PlayerPlaceholder extends Placeholder {

    @Override
    public String identifier() {
        return "%player%";
    }

    @Override
    public String onRequest(final @NotNull Optional<Player> optionalPlayer) {
        if (optionalPlayer.isPresent()) {
            return optionalPlayer.get().getName();
        }
        return getNullValue();
    }
}
