package net.skillcode.advancedmlgrush.item.parser;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.miscellaneous.Constants;
import net.skillcode.advancedmlgrush.util.EnumUtils;
import net.skillcode.advancedmlgrush.util.XMaterial;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

@Singleton
public class MaterialParser {

    private final JavaPlugin javaPlugin;
    private final EnumUtils enumUtils;

    @Inject
    public MaterialParser(final @NotNull JavaPlugin javaPlugin, final @NotNull EnumUtils enumUtils) {
        this.javaPlugin = javaPlugin;
        this.enumUtils = enumUtils;
    }

    public Material parseMaterial(final @NotNull String input) {
        String materialName = input;
        final String[] array = materialName.split(":");

        if (array.length > 1) {
            materialName = array[0];
        }

        if (!enumUtils.isInEnum(XMaterial.class, materialName)) {
            javaPlugin.getLogger().warning(String.format(Constants.MATERIAL_PARSE_ERROR, input));
            return Constants.DEFAULT_MATERIAL;
        }

        return XMaterial.valueOf(materialName).parseMaterial();
    }

    public int parseData(final @NotNull String input) {
        int data = 0;
        final String[] array = input.split(":");

        if (array.length > 1) {
            try {
                data = Integer.parseInt(array[1]);
            } catch (final NumberFormatException exception) {
                javaPlugin.getLogger().warning(String.format(Constants.MATERIAL_PARSE_ERROR, input));
            }
        }

        return data;
    }

}
