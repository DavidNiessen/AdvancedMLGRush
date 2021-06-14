package net.skillcode.advancedmlgrush.sound;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.config.configs.SoundConfig;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Singleton
public class SoundUtil {

    @Inject
    private SoundConfig soundConfig;
    @Inject
    private SoundParser soundParser;

    public void playSound(final @NotNull Player player, final @NotNull String soundPath) {
        final MLGSound mlgSound = soundParser.parse(soundConfig.getString(soundPath));
        if (mlgSound != null) {
            mlgSound.playSound(player);
        }
    }
}
