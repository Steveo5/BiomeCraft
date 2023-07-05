package steven.dev.quest.node.requirements;

import dev.lone.itemsadder.api.FontImages.FontImageWrapper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Item;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import steven.dev.BiomeCraftPlayer;
import steven.dev.gui.BookGUIComponent;
import steven.dev.gui.ParagraphComponent;
import steven.dev.quest.QuestRequirementStatus;
import steven.dev.util.InventoryUtil;

public class QuestNodeCollectItemRequirement extends QuestNodeRequirement {
    private ItemStack requiredItem;

    public QuestNodeCollectItemRequirement(ItemStack requirement) {
        super(QuestNodeRequirementType.COLLECT_ITEM);

        this.requiredItem = requirement;
    }

    @Override
    public BookGUIComponent getBookSummary() {
        ItemStack item = this.getRequiredItem();
        TextComponent.Builder builder = Component.text("").toBuilder();
        builder.append(Component.text(FontImageWrapper.replaceFontImages(":" + item.getType().toString().toLowerCase() + ": ")));
        builder.append(item.displayName().color(NamedTextColor.BLACK).append(Component.text( " x " + item.getAmount())));

        return new ParagraphComponent().setText(builder.build());
    }

    @Override
    public TextComponent getSidebarSummary(BiomeCraftPlayer player) {
        TextComponent.Builder builder = Component.text("").toBuilder();

        int itemCount = InventoryUtil.getSimiliarItemsCount(player.getRoot().getInventory(), this.getRequiredItem());

        builder.append(Component.text("(" + itemCount + " / " + this.getRequiredItem().getAmount() + ")"));

        return builder.build();
    }

    @Override
    public QuestRequirementStatus compare(Object compare) {
        if (!(compare instanceof Inventory i)) return QuestRequirementStatus.NONE;

        int totalOfSimiliarItems = 0;
        for (ItemStack item : i.getContents()) {
            if (item == null || !item.isSimilar(this.getRequiredItem())) continue;

            totalOfSimiliarItems += item.getAmount();
        }

        if (totalOfSimiliarItems == 0) return QuestRequirementStatus.NONE;
        if (totalOfSimiliarItems < this.getRequiredItem().getAmount()) {
            return QuestRequirementStatus.PROGRESS;
        } else {
            return QuestRequirementStatus.COMPLETE;
        }
    }

    public ItemStack getRequiredItem() {
        return requiredItem;
    }
}
