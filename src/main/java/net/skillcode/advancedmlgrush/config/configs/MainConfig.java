package net.skillcode.advancedmlgrush.config.configs;

import net.skillcode.advancedmlgrush.annotations.PostConstruct;
import net.skillcode.advancedmlgrush.config.Configurable;
import net.skillcode.advancedmlgrush.item.GlassColor;
import net.skillcode.advancedmlgrush.miscellaneous.Constants;
import net.skillcode.advancedmlgrush.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainConfig extends Configurable {

    public static final String GLASS_COLOR = "inventory_background_color";

    @PostConstruct
    public void initConfig() {
        super.init();
    }

    @Override
    protected String filePath() {
        return Constants.MAIN_CONFIG_PATH;
    }

    @Override
    protected void configure(final @NotNull List<Pair<String, Object>> list) {
        list.add(new Pair<>(GLASS_COLOR, GlassColor.GRAY.name()));
    }
}
