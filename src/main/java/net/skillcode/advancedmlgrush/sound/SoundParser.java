package net.skillcode.advancedmlgrush.sound;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.miscellaneous.Constants;
import net.skillcode.advancedmlgrush.util.EnumUtils;
import net.skillcode.advancedmlgrush.util.XSound;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Singleton
public class SoundParser {

    private final JavaPlugin javaPlugin;
    private final EnumUtils enumUtils;

    @Inject
    public SoundParser(final JavaPlugin javaPlugin, final EnumUtils enumUtils) {
        this.javaPlugin = javaPlugin;
        this.enumUtils = enumUtils;
    }

    public MLGSound parse(final @NotNull String input) {
        final AtomicReference<XSound> sound = new AtomicReference(Constants.DEFAULT_SOUND);

        float volume = Constants.DEFAULT_SOUND_VOLUME;
        float pitch = Constants.DEFAULT_SOUND_PITCH;

        final String[] array = input.split(":");
        final Optional<XSound> optional = parseSound(array[0]);

        if (optional.isPresent()) {
            sound.set(optional.get());
        } else {
            javaPlugin.getLogger().warning(String.format(Constants.SOUND_PARSE_ERROR, array[0]));
        }

        try {

            if (array.length >= 2) {
                volume = Float.parseFloat(array[1]);
            }

            if (array.length >= 3) {
                pitch = Float.parseFloat(array[2]);
            }

        } catch (final NumberFormatException exception) {
            javaPlugin.getLogger().warning(String.format(Constants.SOUND_PARSE_ERROR, array[0]));
        }

        return new MLGSound(sound.get().parseSound(), volume, pitch);
    }

    private Optional<XSound> parseSound(final @NotNull String sound) {
        if (enumUtils.isInEnum(XSound.class, sound)) {
            return Optional.of(XSound.valueOf(sound));
        }

        return Optional.empty();
    }

}
