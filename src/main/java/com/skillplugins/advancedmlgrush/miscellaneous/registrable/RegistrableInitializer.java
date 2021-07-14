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

package com.skillplugins.advancedmlgrush.miscellaneous.registrable;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.game.GameStateManager;
import com.skillplugins.advancedmlgrush.game.buildmode.BuildModeManager;
import com.skillplugins.advancedmlgrush.game.challenge.ChallengeManager;
import com.skillplugins.advancedmlgrush.game.map.MapInstanceManager;
import com.skillplugins.advancedmlgrush.game.map.setup.MapSetup2x1;
import com.skillplugins.advancedmlgrush.game.map.setup.MapSetup4x1;
import com.skillplugins.advancedmlgrush.game.queue.Queue2x1;
import com.skillplugins.advancedmlgrush.game.queue.Queue4x1;
import com.skillplugins.advancedmlgrush.game.rounds.RoundManager;
import com.skillplugins.advancedmlgrush.game.scoreboard.ScoreboardManager;
import com.skillplugins.advancedmlgrush.inventory.InventoryManager;
import com.skillplugins.advancedmlgrush.util.Initializer;
import org.jetbrains.annotations.NotNull;

@Singleton
public class RegistrableInitializer implements Initializer {

    private final RegistrableManager manager;

    @Inject
    public RegistrableInitializer(final @NotNull RegistrableManager manager) {
        this.manager = manager;
    }

    @Override
    public void init(final @NotNull Injector injector) {
        manager.registerRegistrable(injector.getInstance(BuildModeManager.class));
        manager.registerRegistrable(injector.getInstance(ChallengeManager.class));
        manager.registerRegistrable(injector.getInstance(InventoryManager.class));
        manager.registerRegistrable(injector.getInstance(RoundManager.class));
        manager.registerRegistrable(injector.getInstance(Queue2x1.class));
        manager.registerRegistrable(injector.getInstance(Queue4x1.class));
        manager.registerRegistrable(injector.getInstance(MapSetup2x1.class));
        manager.registerRegistrable(injector.getInstance(MapSetup4x1.class));
        manager.registerRegistrable(injector.getInstance(MapInstanceManager.class));
        manager.registerRegistrable(injector.getInstance(ScoreboardManager.class));
        manager.registerRegistrable(injector.getInstance(GameStateManager.class));
    }
}
