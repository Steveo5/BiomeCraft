package steven.dev;

import com.destroystokyo.paper.event.player.PlayerRecipeBookClickEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.TradeSelectEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.MerchantInventory;
import org.bukkit.inventory.MerchantRecipe;
import steven.dev.quest.Quest;
import steven.dev.quest.QuestDataHandler;
import steven.dev.quest.QuestHandler;
import steven.dev.quest.journal.QuestJournal;

import java.util.logging.Level;

public class BiomeCraftPlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent evt) {
        try {
            QuestDataHandler questDataHandler = BiomeCraft.getInstance().getHandler(QuestDataHandler.class);

            BiomeCraft.getInstance().log(Level.INFO, "Loaded quest journal for " + evt.getPlayer().getName());
            BiomeCraftPlayer player = BiomeCraft.getPlayer(evt.getPlayer());

            if (player != null) {
                questDataHandler.loadJournal(player);
                player.showScoreboard(ScoreboardType.QUEST_JOURNAL);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent evt) {
        if (evt.getAction() == Action.LEFT_CLICK_AIR || evt.getAction() == Action.RIGHT_CLICK_AIR) {
            return;
        }

        if (evt.getHand() != EquipmentSlot.HAND) {
            return;
        }

        QuestHandler questHandler = null;

        try {
            questHandler = BiomeCraft.getInstance().getHandler(QuestHandler.class);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        Quest quest = questHandler.getQuestAt(evt.getClickedBlock());

        if (quest == null) return;

        BiomeCraftPlayer player = BiomeCraft.getPlayer(evt.getPlayer());

        if (player == null) {
            return;
        }

        QuestJournal journal = player.getQuestJournal();
        quest.show(player);
        evt.setCancelled(true);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent evt) {
        BiomeCraft.removePlayer(BiomeCraft.getPlayer(evt.getPlayer()));
    }

    @EventHandler
    public void onQuestChange(TradeSelectEvent evt) {
        evt.getInventory();
    }
}
