package steven.dev.gui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

public class ButtonGroupComponent extends BookGUIComponent {
    public ButtonGroupComponent(ButtonComponent... buttons) {
        TextComponent.Builder builder = Component.text();

        for (ButtonComponent buttonComponent : buttons) {
            builder.append(buttonComponent.getBaseComponent());
        }

        this.setBaseComponent(builder.build());
    }
}
