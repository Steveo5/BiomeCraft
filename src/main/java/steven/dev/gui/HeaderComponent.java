package steven.dev.gui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextDecoration;

public class HeaderComponent extends BookGUIComponent {
    public HeaderComponent(String header) {
        TextComponent headerComponent = Component.text(header).decorate(TextDecoration.BOLD);;

        this.setBaseComponent(headerComponent);
    }
}
