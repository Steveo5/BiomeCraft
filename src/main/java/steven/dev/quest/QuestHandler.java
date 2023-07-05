package steven.dev.quest;

import com.bergerkiller.bukkit.common.config.ConfigurationNode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import steven.dev.BiomeCraft;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import com.bergerkiller.bukkit.common.config.FileConfiguration;
import steven.dev.Handler;
import steven.dev.quest.journal.QuestJournal;

public class QuestHandler extends Handler {
   private final List<Quest> quests = new ArrayList<>();

   public QuestHandler() {
       JavaPlugin plugin = BiomeCraft.getProvidingPlugin(BiomeCraft.class);
       File questsFolder = new File(plugin.getDataFolder().getAbsolutePath() + File.separator + "quests");
       File dataFolder = new File(plugin.getDataFolder().getAbsolutePath() + File.separator + "data");

       if (!questsFolder.exists()) {
           BiomeCraft.getInstance().log(Level.INFO, "Quest folder does not exist, creating...");

           boolean questFolderSuccess = questsFolder.mkdir();

           if (!questFolderSuccess) {
               BiomeCraft.getInstance().log(Level.WARNING, "Failed to create base quest folder");
           }
       }

       if (!dataFolder.exists()) {
           BiomeCraft.getInstance().log(Level.INFO, "Data folder does not exist, creating");

           boolean dataFolderSuccess = dataFolder.mkdir();

           if (!dataFolderSuccess) {
               BiomeCraft.getInstance().log(Level.WARNING, "Failed to create data folder");
           }
       }
   }

//    public void initializeQuestStructure(String name, String title, String description) throws IOException {
//        JavaPlugin plugin = JavaPlugin.getProvidingPlugin(BiomeCraft.class);
//        File questsDirectory = new File(plugin.getDataFolder() + File.separator + "quests");
//
//        if (!questsDirectory.exists()) {
//            questsDirectory.mkdir();
//        }
//
//        File questDirectory = new File(questsDirectory + File.separator + name);
//
//        if (!questDirectory.exists()) {
//            questDirectory.mkdir();
//        }
//
//        String[] filesToCreate = new String[] {
//                "main.yml",
//                "nodes.yml"
//        };
//
//        for (String fileToCreate : filesToCreate) {
//            File file = new File(questDirectory + File.separator + fileToCreate);
//
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//        }
//
//        FileConfiguration mainQuestConfig = new FileConfiguration(plugin, questDirectory + File.separator + filesToCreate[0]);
//        mainQuestConfig.load();
//
//        FileConfiguration nodesQuestConfig = new FileConfiguration(plugin, questDirectory + File.separator + filesToCreate[1]);
//        nodesQuestConfig.load();
//
//        mainQuestConfig.setHeader("name", "The name of the quest, used as an id to identify it");
//        mainQuestConfig.set("name", name);
//        mainQuestConfig.setHeader("title", "The title of the quest, displayed to the user when accepting it");
//        mainQuestConfig.set("title", title);
//        mainQuestConfig.setHeader("description", "The description of the quest when shown to the player");
//        mainQuestConfig.set("description", description);
//        mainQuestConfig.save();
//    }

   public Quest getQuest(String name) {
       System.out.println(this.quests.size());
       for (Quest quest : this.quests) {
           System.out.println(quest.getName());
           if (quest.getName().equalsIgnoreCase(name)) {
               return quest;
           }
       }

       return null;
   }

   public void addQuest(Quest quest) {
       this.quests.add(quest);
   }

    public Quest getQuestAt(Block b) {
        for (Quest q : this.quests) {
            if (q.getAttachesTo() != null && q.getAttachesTo().hasBlock() && b.getLocation().equals(q.getAttachesTo().getBlock().getLocation())) {
                return q;
            }
        }

        return null;
    }
}
