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

package net.skillcode.advancedmlgrush.item.parser;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.libs.xseries.XMaterial;
import net.skillcode.advancedmlgrush.miscellaneous.Constants;
import net.skillcode.advancedmlgrush.util.EnumUtils;
import net.skillcode.advancedmlgrush.util.Pair;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class MaterialParser {

    private final JavaPlugin javaPlugin;
    private final EnumUtils enumUtils;

    private final Map<String, Pair<Material, Integer>> cache = new ConcurrentHashMap<>();

    @Inject
    public MaterialParser(final @NotNull JavaPlugin javaPlugin, final @NotNull EnumUtils enumUtils) {
        this.javaPlugin = javaPlugin;
        this.enumUtils = enumUtils;
    }

    public Pair<Material, Integer> parse(final @NotNull String input) {
        if (cache.containsKey(input)) {
            return cache.get(input);
        }

        if (!enumUtils.isInEnum(XMaterial.class, input)) {
            javaPlugin.getLogger().warning(String.format(Constants.MATERIAL_PARSE_ERROR, input));
            return new Pair<>(Constants.DEFAULT_MATERIAL, 0);
        }

        final XMaterial xMaterial = XMaterial.valueOf(input);
        final Material material = xMaterial.parseMaterial();
        final int data = xMaterial.getData();

        if (material == null) {
            javaPlugin.getLogger().warning(String.format(Constants.MATERIAL_PARSE_ERROR, input));
            return new Pair<>(Constants.DEFAULT_MATERIAL, 0);
        }

        cache.put(input, new Pair<>(material, data));
        return new Pair<>(material, data);
    }
}
