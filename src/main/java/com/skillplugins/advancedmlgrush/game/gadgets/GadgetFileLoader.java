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

package com.skillplugins.advancedmlgrush.game.gadgets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.annotations.PostConstruct;
import com.skillplugins.advancedmlgrush.miscellaneous.Constants;
import com.skillplugins.advancedmlgrush.util.json.JsonConfigAPI;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Singleton
public class GadgetFileLoader {

    private final JsonConfigAPI jsonConfigAPI;

    @Getter
    private GadgetsFile gadgetsFile;

    @Inject
    public GadgetFileLoader(final @NotNull JsonConfigAPI jsonConfigAPI) {
        this.jsonConfigAPI = jsonConfigAPI;
    }

    @PostConstruct
    public void init() {
        jsonConfigAPI.registerConfig(new GadgetsFile(), Constants.GADGETS_CONFIG_PATH);
        gadgetsFile = jsonConfigAPI.getConfig(GadgetsFile.class);
    }
}
