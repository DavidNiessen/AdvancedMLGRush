package net.skillcode.advancedmlgrush.util;

import com.google.inject.Singleton;
import org.jetbrains.annotations.NotNull;

@Singleton
public class EnumUtils {

    public <E extends Enum<E>> boolean isInEnum(final @NotNull Class<E> enumClass, final @NotNull String name) {
        if (name.trim().isEmpty()) {
            return false;
        }
        try {
            Enum.valueOf(enumClass, name);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
