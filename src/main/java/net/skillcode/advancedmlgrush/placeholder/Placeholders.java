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

package net.skillcode.advancedmlgrush.placeholder;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Singleton
public class Placeholders {

    private final PlaceholderManager placeholderManager;

    @Inject
    public Placeholders(final @NotNull PlaceholderManager placeholderManager) {
        this.placeholderManager = placeholderManager;
    }

    public void replace(final @NotNull Optional<Player> optionalPlayer, final @NotNull List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            list.set(i, replace(optionalPlayer, list.get(i)));
        }
    }

    public String replace(final @NotNull Optional<Player> optionalPlayer, final @NotNull String input) {
        final AtomicReference<String> result = new AtomicReference<>(input);

        result.set(replaceColors(result.get()));
        result.set(replaceBuiltinPlaceholders(optionalPlayer, result.get()));

        optionalPlayer.ifPresent(player -> result.set(replacePAPIPlaceholders(player, result.get())));

        return result.get();
    }

    private String replaceColors(final @NotNull String input) {
        return input.replace("&", "§");
    }

    private String replaceBuiltinPlaceholders(final @NotNull Optional<Player> optionalPlayer, final @NotNull String input) {
        return placeholderManager.replaceString(optionalPlayer, input);
    }

    private String replacePAPIPlaceholders(final @NotNull Player player, final @NotNull String input) {
        // TODO: 21.04.2021
        return input;
    }

}
