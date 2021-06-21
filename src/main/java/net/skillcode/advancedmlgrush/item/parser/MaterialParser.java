/*
 * Copyright (c) 2021 SkillCode
 *
 * This file is a part of the source code of the
 * AdvancedMLGRush plugin from SkillCode.
 *
 * This class may only be used in compliance with the
 * LICENSE.txt (https://github.com/SkillC0de/AdvancedMLGRush/blob/master/LICENSE.txt).
 *
 * Support: https://discord.skillplugins.com
 */

package net.skillcode.advancedmlgrush.item.parser;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.miscellaneous.Constants;
import net.skillcode.advancedmlgrush.util.EnumUtils;
import net.skillcode.advancedmlgrush.libs.xseries.XMaterial;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class MaterialParser {

    private final JavaPlugin javaPlugin;
    private final EnumUtils enumUtils;

    private final Map<String, Material> cache = new ConcurrentHashMap<>();

    @Inject
    public MaterialParser(final @NotNull JavaPlugin javaPlugin, final @NotNull EnumUtils enumUtils) {
        this.javaPlugin = javaPlugin;
        this.enumUtils = enumUtils;
    }

    public Material parseMaterial(final @NotNull String input) {
        if (cache.containsKey(input)) {
            return cache.get(input);
        }

        String materialName = input;
        final String[] array = materialName.split(":");

        if (array.length > 1) {
            materialName = array[0];
        }

        Material material;
        if (!enumUtils.isInEnum(XMaterial.class, materialName)
                || (material = XMaterial.valueOf(materialName).parseMaterial()) == null) {
            javaPlugin.getLogger().warning(String.format(Constants.MATERIAL_PARSE_ERROR, input));
            return Constants.DEFAULT_MATERIAL;
        }
        cache.put(input, material);
        return material;
    }

    public int parseData(final @NotNull String input) {
        int data = 0;
        final String[] array = input.split(":");

        if (array.length > 1) {
            try {
                data = Integer.parseInt(array[1]);
            } catch (final NumberFormatException exception) {
                javaPlugin.getLogger().warning(String.format(Constants.MATERIAL_PARSE_ERROR, input));
            }
        }

        return data;
    }

}
