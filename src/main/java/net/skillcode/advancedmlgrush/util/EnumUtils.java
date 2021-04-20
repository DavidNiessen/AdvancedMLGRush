package net.skillcode.advancedmlgrush.util;

import com.google.inject.Singleton;

@Singleton
public class EnumUtils {

    public  <E extends Enum<E>> boolean isInEnum(Class<E> enumClass, String name) {
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
