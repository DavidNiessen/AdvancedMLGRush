package net.skillcode.advancedmlgrush.item.parser;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.miscellaneous.Constants;
import net.skillcode.advancedmlgrush.util.EnumUtils;
import net.skillcode.advancedmlgrush.util.XMaterial;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

@Singleton
public class MaterialParser {

    private final EnumUtils enumUtils;

    @Inject
    public MaterialParser(final @NotNull EnumUtils enumUtils) {
        this.enumUtils = enumUtils;
    }

    public Material parse(final @NotNull String materialName) {
        return enumUtils.isInEnum(XMaterial.class, materialName) ? XMaterial.valueOf(materialName).parseMaterial()
                : Constants.DEFAULT_MATERIAL;
    }

}
