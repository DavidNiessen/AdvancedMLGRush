package net.skillcode.advancedmlgrush.exception;

import com.google.inject.Singleton;
import org.jetbrains.annotations.NotNull;

@Singleton
public class ExceptionHandler {

    public void handle(final @NotNull Exception exception) {
        exception.printStackTrace();
    }

}
