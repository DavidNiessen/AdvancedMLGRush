package net.skillcode.advancedmlgrush.miscellaneous.registrable;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.annotations.PostConstruct;
import net.skillcode.advancedmlgrush.game.buildmode.BuildModeManager;
import net.skillcode.advancedmlgrush.inventory.InventoryManager;

@Singleton
public class RegistrableRegistry {

    @Inject
    private RegistrableManager manager;
    @Inject
    private BuildModeManager buildModeManager;
    @Inject
    private InventoryManager inventoryManager;

    @PostConstruct
    public void register() {
        manager.registerRegistrable(buildModeManager);
        manager.registerRegistrable(inventoryManager);
    }

}
