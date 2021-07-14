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

package com.skillplugins.advancedmlgrush.game.map.setup.step;

import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.game.map.setup.step.steps.constants.Steps;

@Singleton
public class SetupSteps4x1 extends SetupSteps {

    @Override
    void registerSteps() {
        registerStep(Steps.ICON_STEP);
        registerStep(Steps.FIRST_CORNER_STEP);
        registerStep(Steps.SECOND_CORNER_STEP);
        registerStep(Steps.MAX_BUILD_HEIGHT_STEP);
        registerStep(Steps.DEATH_HEIGHT_STEP);
        registerStep(Steps.SPECTATOR_SPAWN_STEP);
        registerStep(Steps.SPAWN_PLAYER_1_STEP);
        registerStep(Steps.SPAWN_PLAYER_2_STEP);
        registerStep(Steps.SPAWN_PLAYER_3_STEP);
        registerStep(Steps.SPAWN_PLAYER_4_STEP);
        registerStep(Steps.BED_PLAYER_1_STEP);
        registerStep(Steps.BED_PLAYER_2_STEP);
        registerStep(Steps.BED_PLAYER_3_STEP);
        registerStep(Steps.BED_PLAYER_4_STEP);
    }
}
