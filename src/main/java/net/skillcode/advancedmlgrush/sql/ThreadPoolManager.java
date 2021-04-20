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
