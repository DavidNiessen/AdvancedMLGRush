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

package net.skillcode.advancedmlgrush.sql;

import com.google.inject.Singleton;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Singleton
@Getter
public class ThreadPoolManager {

    private final ExecutorService threadPool = Executors.newCachedThreadPool();

    public Future<?> submit(final @NotNull Runnable runnable) {
        return threadPool.submit(runnable);
    }
}
