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

package net.skillcode.advancedmlgrush.placeholder;

import com.google.inject.Singleton;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Singleton
public class PlaceholderManager {

    private final Map<String, Placeholder> placeholderMap = new ConcurrentHashMap<>();

    private final Pattern pattern1 = Pattern.compile("%(.*?)%", Pattern.DOTALL);

    public void registerPlaceholder(final @NotNull Placeholder placeholder) {
        placeholderMap.put(placeholder.identifier(), placeholder);
    }

    public String replaceString(final @NotNull Optional<Player> optionalPlayer, final @NotNull String string) {
        final AtomicReference<String> atomicReference = new AtomicReference<>(string);
        getPlaceholders(string).forEach(placeholder -> Optional.ofNullable(placeholderMap.getOrDefault(placeholder, null))
                .ifPresent(placeholder1 -> atomicReference.set(atomicReference.get().replace(placeholder, placeholder1.onRequest(optionalPlayer)))));


        return atomicReference.get();
    }

    private List<String> getPlaceholders(final @NotNull String string) {
        final List<String> list = new ArrayList<>();
        final Matcher matcher = pattern1.matcher(string);

        while (matcher.find()) {
            list.add("%" + matcher.group(1).toLowerCase() + "%");
        }

        return list;
    }

}
