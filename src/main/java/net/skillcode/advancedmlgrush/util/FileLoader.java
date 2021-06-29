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

package net.skillcode.advancedmlgrush.util;

import com.google.inject.Inject;
import net.skillcode.advancedmlgrush.annotations.PostConstruct;
import net.skillcode.advancedmlgrush.util.json.JsonConfig;
import net.skillcode.advancedmlgrush.util.json.JsonConfigAPI;
import org.jetbrains.annotations.NotNull;

public abstract class FileLoader {

    @Inject
    private JsonConfigAPI jsonConfigAPI;

    protected JsonConfig jsonConfig;

    @PostConstruct
    public void register() {
        jsonConfigAPI.registerConfig(jsonConfig(), path());
        jsonConfig = jsonConfigAPI.getConfig(jsonConfig.getClass());
        load();
    }

    public void load() {
        jsonConfig = jsonConfigAPI.getConfig(jsonConfig().getClass());
    }

    public void save(final @NotNull JsonConfig config) {
        jsonConfigAPI.saveConfig(config);
        load();
    }

    abstract String path();

    abstract JsonConfig jsonConfig();

}
