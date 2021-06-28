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

package net.skillcode.advancedmlgrush.game.map.setup.step.steps.constants;

import net.skillcode.advancedmlgrush.game.map.setup.step.steps.*;

public class Steps {

    private Steps() {
    }

    public static final FirstCornerStep FIRST_CORNER_STEP = new FirstCornerStep();
    public static final SecondCornerStep SECOND_CORNER_STEP = new SecondCornerStep();
    public static final MaxBuildHeightStep MAX_BUILD_HEIGHT_STEP = new MaxBuildHeightStep();
    public static final DeathHeightStep DEATH_HEIGHT_STEP = new DeathHeightStep();
    public static final SpectatorSpawnStep SPECTATOR_SPAWN_STEP = new SpectatorSpawnStep();
    public static final SpawnPlayer1Step SPAWN_PLAYER_1_STEP = new SpawnPlayer1Step();
    public static final SpawnPlayer2Step SPAWN_PLAYER_2_STEP = new SpawnPlayer2Step();
    public static final SpawnPlayer3Step SPAWN_PLAYER_3_STEP = new SpawnPlayer3Step();
    public static final SpawnPlayer4Step SPAWN_PLAYER_4_STEP = new SpawnPlayer4Step();
    public static final BedPlayer1Step BED_PLAYER_1_STEP = new BedPlayer1Step();
    public static final BedPlayer2Step BED_PLAYER_2_STEP = new BedPlayer2Step();
    public static final BedPlayer3Step BED_PLAYER_3_STEP = new BedPlayer3Step();
    public static final BedPlayer4Step BED_PLAYER_4_STEP = new BedPlayer4Step();

}
