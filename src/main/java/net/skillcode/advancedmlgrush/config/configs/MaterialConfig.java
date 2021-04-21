package net.skillcode.advancedmlgrush.config.configs;

import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.annotations.PostConstruct;
import net.skillcode.advancedmlgrush.config.Configurable;
import net.skillcode.advancedmlgrush.miscellaneous.Constants;
import net.skillcode.advancedmlgrush.util.Pair;
import net.skillcode.advancedmlgrush.util.XMaterial;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Singleton
public class MaterialConfig extends Configurable {

    public static final String CHALLENGER = "challenger";
    public static final String SETTINGS = "settings";
    public static final String SPECTATE = "spectate";
    public static final String EXTRAS = "extras";
    public static final String STATS = "stats";

    @PostConstruct
    public void initConfig() {
        super.init();
    }

    @Override
    protected String filePath() {
        return Constants.ITEM_MATERIAL_CONFIG_PATH;
    }

    @Override
    protected void configure(final @NotNull List<Pair<String, Object>> list) {
        list.add(new Pair<>(CHALLENGER, XMaterial.IRON_SWORD.parseMaterial().name()));
        list.add(new Pair<>(SETTINGS, XMaterial.REPEATER.parseMaterial().name()));
        list.add(new Pair<>(SPECTATE, XMaterial.COMPASS.parseMaterial().name()));
        list.add(new Pair<>(EXTRAS, XMaterial.CHEST.parseMaterial().name()));
    }
}
