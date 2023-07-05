package steven.dev.quest;

import io.papermc.paper.event.block.BlockBreakBlockEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import steven.dev.BiomeCraft;
import steven.dev.BiomeCraftPlayer;
import steven.dev.ScoreboardType;
import steven.dev.quest.node.requirements.QuestNodeRequirement;
import steven.dev.quest.node.requirements.QuestNodeRequirementType;
import steven.dev.util.InventoryUtil;

import java.util.List;

public class QuestListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakBlockEvent evt) {

    }

    @EventHandler
    public void onPickupItem(EntityPickupItemEvent evt) {
        if (evt.getEntity() instanceof Player) {
            BiomeCraftPlayer bcp = BiomeCraft.getPlayer((Player)evt.getEntity());

            if (bcp == null) return;

            QuestRequirementQuery query = new QuestRequirementQuery(bcp.getQuestJournal(), bcp.getRoot().getInventory(), QuestNodeRequirementType.COLLECT_ITEM);
            List<QuestRequirementQueryResult> result = query.execute();

            if (result.size() < 1) return;

            QuestRequirementQueryResult questRequirementQueryResult = result.get(0);

            if (questRequirementQueryResult.getQuestRequirementStatus() == QuestRequirementStatus.COMPLETE) {
                final Title title = Title.title(
                        Component.text("Quest complete", NamedTextColor.GREEN),
                        Component.text(questRequirementQueryResult.getQuest().getTitle())
                );
                bcp.getRoot().showTitle(title);
            } else if (questRequirementQueryResult.getQuestRequirementStatus() == QuestRequirementStatus.PROGRESS) {
                bcp.showScoreboard(ScoreboardType.QUEST_JOURNAL);
            }
        }
    }
}
