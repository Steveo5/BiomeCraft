package steven.dev.quest.data;

import com.bergerkiller.bukkit.common.config.ConfigurationNode;
import com.bergerkiller.bukkit.common.config.FileConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;
import steven.dev.BiomeCraft;
import steven.dev.BiomeCraftPlayer;
import steven.dev.Handler;
import steven.dev.quest.Quest;
import steven.dev.quest.QuestAttachement;
import steven.dev.quest.QuestHandler;
import steven.dev.quest.journal.QuestJournal;

import java.io.File;
import java.util.Objects;
import java.util.logging.Level;

public class QuestLoader {
    /**
     * Load quests from disk into the quest handler
     * @param plugin
     */
    public void loadQuestsFromDisk(BiomeCraft plugin) {
        File questsDirectory = new File(plugin.getDataFolder() + File.separator + "quests");

        if (!questsDirectory.exists()) {
            boolean createdQuestsDirectory = questsDirectory.mkdir();

            if (!createdQuestsDirectory) {
                BiomeCraft.getInstance().log(Level.SEVERE, "Could not create quests directory while loading quests");
                return;
            }
        }

        this.loadQuestsFromFile(plugin, questsDirectory);
    }

    /**
     * Load quests from the specified quests directory into quest handler
     * @param plugin
     * @param questsDirectory
     */
    private void loadQuestsFromFile(BiomeCraft plugin, File questsDirectory) {
        for (File f : Objects.requireNonNull(questsDirectory.listFiles())) {

            if (f.getName().startsWith(".")) {
                continue;
            }

            File mainFile = new File(f + File.separator + "main.yml");
            if (mainFile.exists()) {
                FileConfiguration mainConfig = new FileConfiguration(plugin, "quests" + File.separator + f.getName() + File.separator + "main.yml");
                mainConfig.load();
                String questTitle = mainConfig.get("title", String.class);
                String questDescription = mainConfig.get("description", String.class);
                String nodeName = mainConfig.get("node", String.class);

                // Create the quest
                Quest quest = new Quest(f.getName());
                quest.setTitle(questTitle).setDescription(questDescription);

                if (mainConfig.contains("attaches-to")) {
                    this.loadQuestAttachements(mainConfig, quest);
                }

                File nodeDir = new File(f + File.separator + "nodes");

                if (nodeDir.exists() && nodeDir.isDirectory()) {
                    quest.setNode(new QuestNodeLoader(quest, nodeDir, nodeName + ".yml").load());

                    try {
                        BiomeCraft.getInstance().getHandler(QuestHandler.class).addQuest(quest);
                        plugin.getLogger().log(Level.INFO, "Loaded quest " + quest.getName());
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    plugin.getLogger().log(Level.WARNING, "Failed to find nodes directory when loading quest " + f);
                }
            } else {
                plugin.getLogger().log(Level.WARNING, "Failed to find main.yml when loading quest " + f);
            }
        }
    }

//    /**
//     * Load nodes for a specific quest
//     * @param plugin
//     * @param nodeDir
//     * @param quest
//     * @param nodeName
//     */
//    private void loadNodesFromFile(BiomeCraft plugin, File nodeDir, Quest quest, String nodeName) {
//        for (File nodeFile : Objects.requireNonNull(nodeDir.listFiles())) {
//            if (nodeFile.isFile() && nodeFile.getName().endsWith(".yml")) {
//                // Create the node
//                QuestNode node = new QuestNode(nodeFile.getName());
//                FileConfiguration nodeConfig = new FileConfiguration(plugin, "quests" + File.separator + nodeName + File.separator + "nodes" + File.separator + nodeFile.getName());
//                nodeConfig.load();
//                node.setNodeRequirements(this.loadNodeRequirementsFromFile(nodeConfig, quest));
//                quest.setNode(node);
//            }
//        }
//    }

    private void loadQuestAttachements(FileConfiguration config, Quest quest) {
        ConfigurationNode attachNode = config.getNode("attaches-to");
        QuestAttachement attachment = new QuestAttachement();

        if (attachNode.contains("block")) {
            ConfigurationNode blockNode = attachNode.getNode("block");
            int x = blockNode.get("x", Integer.class);
            int y = blockNode.get("y", Integer.class);
            int z = blockNode.get("z", Integer.class);
            World w = Bukkit.getWorld("world");

            try {
                Block b = null;
                if (w != null) {
                    b = w.getBlockAt(x, y, z);
                }
                attachment.setBlock(b);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            quest.setAttachesTo(attachment);
        }
    }
}
