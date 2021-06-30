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

package net.skillcode.advancedmlgrush.event;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.game.map.setup.MapSetup1x1;
import net.skillcode.advancedmlgrush.game.map.setup.MapSetup1x4;
import net.skillcode.advancedmlgrush.item.items.handlers.*;
import net.skillcode.advancedmlgrush.sql.data.SQLDataCache;
import net.skillcode.advancedmlgrush.util.Initializer;
import org.jetbrains.annotations.NotNull;

@Singleton
public class EventHandlerInitializer implements Initializer {

    private final EventManager eventManager;

    @Inject
    public EventHandlerInitializer(final @NotNull EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public void init(final @NotNull Injector injector) {
        eventManager.registerEventListeners(injector.getInstance(SQLDataCache.class));

        eventManager.registerEventListeners(injector.getInstance(ChallengerHandler.class));
        eventManager.registerEventListeners(injector.getInstance(QueueLeaveHandler.class));
        eventManager.registerEventListeners(injector.getInstance(SettingsHandler.class));
        eventManager.registerEventListeners(injector.getInstance(GadgetsHandler.class));
        eventManager.registerEventListeners(injector.getInstance(StatsHandler.class));
        eventManager.registerEventListeners(injector.getInstance(SpectateHandler.class));
        eventManager.registerEventListeners(injector.getInstance(MapSetup1x1.class));
        eventManager.registerEventListeners(injector.getInstance(MapSetup1x4.class));
    }
}
