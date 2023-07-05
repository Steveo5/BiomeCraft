package steven.dev.quest.journal;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import steven.dev.BiomeCraft;
import steven.dev.BiomeCraftPlayer;
import steven.dev.quest.QuestHandler;

import java.util.logging.Level;

public class QuestJournalSaveTask extends BukkitRunnable {
    @Override
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            BiomeCraftPlayer bcp = BiomeCraft.getPlayer(p);

            if (bcp != null) {
                bcp.getQuestJournal().save();
            }
        }

        BiomeCraft.getProvidingPlugin(BiomeCraft.class).getLogger().log(Level.INFO, "Saving quest journals to disk");
    }
}
