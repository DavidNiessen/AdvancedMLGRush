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

package net.skillcode.advancedmlgrush.util.json;

import net.skillcode.advancedmlgrush.util.json.utils.JsonUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class JsonConfigAPI {

    private Map<Class<? extends JsonConfig>, JsonConfig> configMap = new HashMap<>();
    private Map<JsonConfig, File> fileMap = new HashMap<>();
    private boolean prettyPrint;

    public JsonConfigAPI(@NotNull Boolean prettyPrint) {
        this.prettyPrint = prettyPrint;
    }

    public void registerConfig(@NotNull JsonConfig jsonConfig, @NotNull String filePath) {
        final File file = new File(filePath);
        fileMap.put(jsonConfig, file);
        if (file.exists()) {
            final JsonConfig config = JsonUtils.getFromFile(jsonConfig.getClass(), file);
            configMap.put(config.getClass(), config);
        } else {
            file.getParentFile().mkdirs();
            JsonUtils.toFile(jsonConfig, file, prettyPrint);
            configMap.put(jsonConfig.getClass(), jsonConfig);
        }
    }

    public void unregisterConfig(@NotNull JsonConfig jsonConfig) {
        final Class<? extends JsonConfig> configClass = jsonConfig.getClass();
        if (configMap.containsKey(configClass)) {
            configMap.remove(configClass);
        }
        if (fileMap.containsKey(jsonConfig)) {
            fileMap.remove(jsonConfig);
        }
    }

    public void saveConfig(@NotNull JsonConfig jsonConfig) {
        final File file = fileMap.getOrDefault(jsonConfig, null);
        if (file != null) {
            JsonUtils.toFile(jsonConfig, file, prettyPrint);
        }
    }

    @Nullable
    public <T> T getConfig(@NotNull Class<? extends T> configClass) {
        return (T) configMap.getOrDefault(configClass, null);
    }

}
