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

package net.skillcode.advancedmlgrush.game.map.setup.step;

import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.annotations.PostConstruct;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Singleton
public abstract class SetupSteps {

    @PostConstruct
    public void init() {
        registerSteps();
    }

    final List<SetupStep> setupSteps = new CopyOnWriteArrayList<>();

    public <E> Optional<SetupStep<E>> getSetupStep(final int index) {
        if (index < setupSteps.size()) {
            return Optional.of(setupSteps.get(index));
        }
        return Optional.empty();
    }

    abstract void registerSteps();

    protected void registerStep(final @NotNull SetupStep<?> setupStep) {
        setupSteps.add(setupStep);
    }

}
