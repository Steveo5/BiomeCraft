package steven.dev.gui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class BookGUIPage {
    private List<BookGUIComponent> componentList = new ArrayList<>();
    private Component baseComponent;

    public BookGUIPage() {
    }

    public BookGUIPage setContent(BookGUIComponent... components) {
        this.componentList = Arrays.asList(components);

        TextComponent.Builder component = Component.text();

        for (BookGUIComponent bookGUIComponent : components) {
            component.append(bookGUIComponent.getBaseComponent());
        }

        this.baseComponent = component.build();

        return this;
    }

    public Component getBaseComponent() {
        return this.baseComponent;
    }
}
