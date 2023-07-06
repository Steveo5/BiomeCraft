package steven.dev;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.TradeSelectEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import steven.dev.quest.Quest;
import steven.dev.quest.data.QuestJournalLoader;
import steven.dev.quest.QuestHandler;
import steven.dev.quest.journal.QuestJournal;

import java.util.logging.Level;

public class BiomeCraftPlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent evt) {
        BiomeCraft.getInstance().log(Level.INFO, "Loaded quest journal for " + evt.getPlayer().getName());
        BiomeCraftPlayer player = BiomeCraft.getPlayer(evt.getPlayer());

        if (player != null) {
            new QuestJournalLoader().loadJournal(player);
            player.showScoreboard(ScoreboardType.QUEST_JOURNAL);
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
