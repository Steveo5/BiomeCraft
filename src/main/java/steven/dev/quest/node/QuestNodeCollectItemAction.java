package steven.dev.quest.node;

import org.bukkit.inventory.ItemStack;
import steven.dev.BiomeCraftPlayer;
import steven.dev.quest.Quest;

public class QuestNodeCollectItemAction extends QuestNodeAnswerAction {
    private ItemStack item;

    public QuestNodeCollectItemAction(ItemStack item) {

        this.item = item;
    }

    @Override
    public void execute(BiomeCraftPlayer player, Quest quest) {
        player.getRoot().getInventory().addItem(this.getItem());
    }

    public ItemStack getItem() {
        return item;
    }
}
