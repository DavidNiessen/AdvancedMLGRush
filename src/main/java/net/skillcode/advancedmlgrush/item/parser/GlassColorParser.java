package net.skillcode.advancedmlgrush.item.parser;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.item.GlassColor;
import net.skillcode.advancedmlgrush.util.EnumUtils;
import org.jetbrains.annotations.NotNull;

@Singleton
public class GlassColorParser {

    private final EnumUtils enumUtils;

    @Inject
    public GlassColorParser(final EnumUtils enumUtils) {
        this.enumUtils = enumUtils;
    }

    public GlassColor parse(final @NotNull String input) {
        GlassColor glassColor = GlassColor.GRAY;

        if (enumUtils.isInEnum(GlassColor.class, input)) {
            glassColor = GlassColor.valueOf(input);
        }
        return glassColor;
    }

}
