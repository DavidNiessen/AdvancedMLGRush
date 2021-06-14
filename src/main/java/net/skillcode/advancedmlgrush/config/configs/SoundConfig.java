package net.skillcode.advancedmlgrush.config.configs;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.annotations.PostConstruct;
import net.skillcode.advancedmlgrush.config.Configurable;
import net.skillcode.advancedmlgrush.miscellaneous.Constants;
import net.skillcode.advancedmlgrush.sound.SoundParser;
import net.skillcode.advancedmlgrush.util.Pair;
import net.skillcode.advancedmlgrush.util.XSound;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Singleton
public class SoundConfig extends Configurable {

    public static final String OPEN_INVENTORY = "open_inventory";

    private final SoundParser soundParser;

    @Inject
    public SoundConfig(final SoundParser soundParser) {
        this.soundParser = soundParser;
    }

    @PostConstruct
    public void initConfig() {
        super.init();
    }

    @Override
    protected String filePath() {
        return Constants.SOUND_CONFIG_PATH;
    }

    @Override
    protected void configure(final @NotNull List<Pair<String, Object>> list) {
        list.add(new Pair<>(OPEN_INVENTORY, soundParser.parseString(XSound.BLOCK_PISTON_EXTEND, 1, 1.9F)));
    }
}
