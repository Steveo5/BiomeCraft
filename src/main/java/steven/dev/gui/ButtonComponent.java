package steven.dev.gui;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.function.Consumer;
import java.util.function.Function;

public class ButtonComponent extends BookGUIComponent {
    private Consumer<Audience> callback;
    private TextComponent button;

    public ButtonComponent(String text, NamedTextColor color, ButtonComponentAction action) {
        TextComponent button = Component.text("\ue8b0", NamedTextColor.WHITE)
                .append(Component.text(text, color))
                .clickEvent(ClickEvent.callback((cb) -> {
                    if (action == ButtonComponentAction.CLOSE_BOOK) {
                        return;
                    } else if (action == ButtonComponentAction.CUSTOM) {
                        if (callback != null) {
                            callback.accept(cb);
                        }
                    }
                }));

        this.button = button;
        this.setBaseComponent(button);
    }

    public ButtonComponent clickEvent(Consumer<Audience> fn) {
        callback = fn;
        return this;
    }
}
