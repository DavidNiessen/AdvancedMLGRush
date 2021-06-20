package net.skillcode.advancedmlgrush.config.configs;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.annotations.PostConstruct;
import net.skillcode.advancedmlgrush.config.Configurable;
import net.skillcode.advancedmlgrush.item.EnumItem;
import net.skillcode.advancedmlgrush.item.parser.MaterialParser;
import net.skillcode.advancedmlgrush.miscellaneous.Constants;
import net.skillcode.advancedmlgrush.util.Pair;
import net.skillcode.advancedmlgrush.libs.xseries.XMaterial;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Singleton
public class ItemMaterialConfig extends Configurable {

    private final MaterialParser materialParser;

    @Inject
    public ItemMaterialConfig(final MaterialParser materialParser) {
        this.materialParser = materialParser;
    }

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
        list.add(new Pair<>(EnumItem.CHALLENGER.getConfigPath(), XMaterial.IRON_SWORD.name()));
        list.add(new Pair<>(EnumItem.SETTINGS.getConfigPath(), XMaterial.REPEATER.name()));
        list.add(new Pair<>(EnumItem.SPECTATE.getConfigPath(), XMaterial.COMPASS.name()));
        list.add(new Pair<>(EnumItem.GADGETS.getConfigPath(), XMaterial.CHEST.name()));
        list.add(new Pair<>(EnumItem.QUEUE_LEAVE.getConfigPath(), XMaterial.BARRIER.name()));
        list.add(new Pair<>(EnumItem.SETTINGS_INVENTORY_SORTING.getConfigPath(), XMaterial.REPEATER.name()));
        list.add(new Pair<>(EnumItem.SETTINGS_MAP.getConfigPath(), XMaterial.MAP.name()));
        list.add(new Pair<>(EnumItem.SETTINGS_ROUNDS.getConfigPath(), XMaterial.SLIME_BALL.name()));
        list.add(new Pair<>(EnumItem.GADGETS_STICK.getConfigPath(), XMaterial.STICK.name()));
        list.add(new Pair<>(EnumItem.GADGETS_BLOCKS.getConfigPath(), XMaterial.SANDSTONE.name()));
    }

    public Material getMaterial(final @NotNull String path) {
        return materialParser.parseMaterial(super.getString(path));
    }

    public Material getMaterial(final @NotNull EnumItem enumItem) {
        return getMaterial(enumItem.getConfigPath());
    }

    public int getData(final @NotNull String path) {
        return materialParser.parseData(path);
    }

    public int getData(final @NotNull EnumItem enumItem) {
        return getData(enumItem.getConfigPath());
    }
}
