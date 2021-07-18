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

package com.skillplugins.advancedmlgrush.placeholder;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.placeholder.placeholders.PlayerPlaceholder;
import com.skillplugins.advancedmlgrush.placeholder.placeholders.PlayersInGamePlaceholder;
import com.skillplugins.advancedmlgrush.placeholder.placeholders.Queue2x1Placeholder;
import com.skillplugins.advancedmlgrush.placeholder.placeholders.Queue4x1Placeholder;
import com.skillplugins.advancedmlgrush.placeholder.placeholders.map.*;
import com.skillplugins.advancedmlgrush.placeholder.placeholders.settings.MapPlaceholder;
import com.skillplugins.advancedmlgrush.placeholder.placeholders.settings.RoundsPlaceholder;
import com.skillplugins.advancedmlgrush.placeholder.placeholders.stats.BedsPlaceholder;
import com.skillplugins.advancedmlgrush.placeholder.placeholders.stats.LosesPlaceholder;
import com.skillplugins.advancedmlgrush.placeholder.placeholders.stats.WinRatePlaceholder;
import com.skillplugins.advancedmlgrush.placeholder.placeholders.stats.ranking.*;
import com.skillplugins.advancedmlgrush.placeholder.placeholders.stats.wins.*;
import com.skillplugins.advancedmlgrush.util.Initializer;
import org.jetbrains.annotations.NotNull;

@Singleton
public class PlaceholderInitializer implements Initializer {

    private final PlaceholderManager placeholderManager;

    @Inject
    public PlaceholderInitializer(final @NotNull PlaceholderManager placeholderManager) {
        this.placeholderManager = placeholderManager;
    }

    @Override
    public void init(final @NotNull Injector injector) {
        placeholderManager.registerPlaceholder(injector.getInstance(RoundsPlaceholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(PlayerPlaceholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(WinsPlaceholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(BedsPlaceholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(WinRatePlaceholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(LosesPlaceholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(RankingPlaceholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(Ranking1Placeholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(Ranking2Placeholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(Ranking3Placeholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(Ranking4Placeholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(Ranking5Placeholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(Ranking6Placeholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(Ranking7Placeholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(Ranking8Placeholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(Ranking9Placeholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(Ranking10Placeholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(Queue2x1Placeholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(Queue4x1Placeholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(MapPlaceholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(MapNamePlaceholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(MapModePlaceholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(MapPlayer1Placeholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(MapPlayer2Placeholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(MapPlayer3Placeholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(MapPlayer4Placeholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(MapScore1Placeholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(MapScore2Placeholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(MapScore3Placeholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(MapScore4Placeholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(MapRoundsPlaceholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(PlayersInGamePlaceholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(Wins1Placeholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(Wins2Placeholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(Wins3Placeholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(Wins4Placeholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(Wins5Placeholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(Wins6Placeholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(Wins7Placeholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(Wins8Placeholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(Wins9Placeholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(Wins10Placeholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(MapsPlaceholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(MapTemplatesPlaceholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(MapTemplates2x1Placeholder.class));
        placeholderManager.registerPlaceholder(injector.getInstance(MapTemplates4x1Placeholder.class));
    }
}
