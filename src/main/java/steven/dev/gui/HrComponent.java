package steven.dev.gui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class HrComponent extends BookGUIComponent {
    public HrComponent(NamedTextColor color) {
        this.setBaseComponent(Component.text("-------------------\n", color));
    }
}
