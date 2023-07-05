package steven.dev.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookGUI {
    private final ItemStack book;
    private final BookMeta bookMeta;
    private List<BookGUIPage> pages = new ArrayList<>();

    public BookGUI() {
        book = new ItemStack(Material.WRITTEN_BOOK);
        bookMeta = (BookMeta) book.getItemMeta();

        String uuid = UUID.randomUUID().toString();
        bookMeta.setTitle("Blank");
        bookMeta.setAuthor("Blank");

        book.setItemMeta(bookMeta);
    }

    public BookGUI addPage(BookGUIPage page) {
        bookMeta.addPages(page.getBaseComponent());
        book.setItemMeta(bookMeta);
        return this;
    }

    public void open(Player player) {
        player.openBook(book);
    }
}
