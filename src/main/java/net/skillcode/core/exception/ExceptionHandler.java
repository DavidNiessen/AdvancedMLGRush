package net.skillcode.core.exception;

import com.google.inject.Singleton;
import org.jetbrains.annotations.NotNull;

@Singleton
public class ExceptionHandler {

    public void handle(final @NotNull Throwable throwable) {
        throwable.printStackTrace();
    }

}
