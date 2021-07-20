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

package com.skillplugins.advancedmlgrush.config.configs;

import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.annotations.PostConstruct;
import com.skillplugins.advancedmlgrush.config.Configurable;
import com.skillplugins.advancedmlgrush.miscellaneous.Constants;
import com.skillplugins.advancedmlgrush.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Singleton
public class DebugConfig extends Configurable {

    public static final String LOG_UNEXPECTED_STACKTRACES = "log_unexpected_stacktraces";
    public static final String LOG_EXPECTED_STACKTRACES = "log_expected_stacktraces";
    public static final String LOG_QUERIES = "log_queries";

    @PostConstruct
    public void initConfig() {
        super.init();
    }

    @Override
    protected String filePath() {
        return Constants.DEBUG_CONFIG_PATH;
    }

    @Override
    protected void configure(final @NotNull List<Pair<String, Object>> list) {
        list.add(new Pair<>(LOG_UNEXPECTED_STACKTRACES, true));
        list.add(new Pair<>(LOG_EXPECTED_STACKTRACES, false));
        list.add(new Pair<>(LOG_QUERIES, false));
    }
}
