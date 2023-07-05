package steven.dev.quest;

import com.bergerkiller.bukkit.common.config.ConfigurationNode;
import com.bergerkiller.bukkit.common.config.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import steven.dev.BiomeCraft;
import steven.dev.Handler;
import steven.dev.quest.journal.QuestJournal;
import steven.dev.quest.journal.QuestJournalProgress;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuestJournalDataHandler extends Handler {
    public void saveToDisk(QuestJournal journal, Player player) {
        JavaPlugin plugin = BiomeCraft.getProvidingPlugin(BiomeCraft.class);
        File journalFolder = new File(plugin.getDataFolder() + File.separator + "data" + File.separator + "journals");

        if (!journalFolder.exists()) {
            journalFolder.mkdir();
        }

        if (journal.getQuestProgress().size() > 0) {
            File playerJournalFile = new File(journalFolder + File.separator + player.getUniqueId().toString() + ".yml");

            if (!playerJournalFile.exists()) {
                try {
                    playerJournalFile.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            FileConfiguration playerJournalData = new FileConfiguration(plugin, "data" + File.separator + "journals" + File.separator + player.getUniqueId().toString() + ".yml");
            playerJournalData.load();

            List<ConfigurationNode> journalDataQuestList = new ArrayList<>();

            for (QuestJournalProgress journalProgress : journal.getQuestProgress()) {
                ConfigurationNode newNode = new ConfigurationNode();

                newNode.set("quest-name", journalProgress.getQuest().getName() + ".yml");
                newNode.set("current-node", journalProgress.getNode().getNodeName());
                journalDataQuestList.add(newNode);
            }

            playerJournalData.setNodeList("quests", journalDataQuestList);
            playerJournalData.save();
        }
    }
}
