package net.skillcode.advancedmlgrush.game.gadgets;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Gadget {

    private final String name;
    private final String material;
    private final String permission;
    private final String loreUnlocked;
    private final String loreLocked;

}
