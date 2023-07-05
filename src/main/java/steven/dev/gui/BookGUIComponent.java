package steven.dev.gui;

import net.kyori.adventure.text.TextComponent;

public class BookGUIComponent {
    private TextComponent baseComponent;

    protected void setBaseComponent(TextComponent component) {
        this.baseComponent = component;
    }

    protected TextComponent getBaseComponent() {
        return this.baseComponent;
    }
}
