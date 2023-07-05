package steven.dev.util;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryUtil {
    public static int getSimiliarItemsCount(Inventory inventory, ItemStack compare) {
        int count = 0;

        for (ItemStack iItem : inventory.getContents()) {
            if (iItem != null && iItem.isSimilar(compare)) {
                count += iItem.getAmount();
            }
        }

        return count;
    }
}
