package net.skillcode.advancedmlgrush.sound;

import lombok.RequiredArgsConstructor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class MLGSound {

    private final Sound sound;
    private final float volume;
    private final float pitch;

    public void playSound(final @NotNull Player player) {
        player.playSound(player.getLocation(), sound, volume, pitch);
    }

}
