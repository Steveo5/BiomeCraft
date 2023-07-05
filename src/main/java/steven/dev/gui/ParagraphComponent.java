package steven.dev.gui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

public class ParagraphComponent extends BookGUIComponent {
    public ParagraphComponent setText(TextComponent text) {
        this.setBaseComponent(text.append(Component.text("\n")));
        return this;
    }

    public TextComponent getText() {
        return this.getBaseComponent();
    }
}
