package steven.dev.quest;

import com.bergerkiller.bukkit.common.config.ConfigurationNode;
import com.bergerkiller.bukkit.common.config.FileConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import steven.dev.BiomeCraft;
import steven.dev.BiomeCraftPlayer;
import steven.dev.Handler;
import steven.dev.quest.journal.QuestJournal;
import steven.dev.quest.node.*;
import steven.dev.quest.node.requirements.QuestNodeCollectItemRequirement;
import steven.dev.quest.node.requirements.QuestNodeRequirement;
import steven.dev.quest.node.requirements.QuestNodeRequirementType;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

public class QuestDataHandler extends Handler {
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
                    quest.setNode(this.loadNode(plugin, quest, nodeDir, nodeName + ".yml"));

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

    private QuestNode loadNode(BiomeCraft plugin, Quest quest, File nodeDir, String nodeName) {

        File nodeFile = new File(nodeDir + File.separator + nodeName);

        System.out.println("Loading " + nodeFile.getPath());

        if (nodeFile.exists()) {
            FileConfiguration nodeConfig = new FileConfiguration(plugin, "quests" + File.separator + quest.getName() + File.separator + "nodes" + File.separator + nodeName);
            nodeConfig.load();
            String message = nodeConfig.get("message", String.class);
            List<QuestNodeQuestion> questions = new ArrayList<>();

            if (nodeConfig.contains("questions")) {
                questions = this.loadQuestions(plugin, quest, nodeDir, nodeConfig, nodeConfig.getNodeList("questions"));
            }

            QuestNode node = new QuestNode(nodeName.replaceAll(".yml", ""), message, questions);

            return node;
        } else {
            return null;
        }
    }

    private List<QuestNodeQuestion> loadQuestions(BiomeCraft plugin, Quest quest, File nodeDir, ConfigurationNode baseonfigNode, List<ConfigurationNode> questions) {
        List<QuestNodeQuestion> loadedQuestions = new ArrayList<>();

        for (ConfigurationNode node : questions) {
            List<QuestNodeQuestion> nodeQuestions = new ArrayList<>();

            if (node.contains("questions")) {
                nodeQuestions = this.loadQuestions(plugin, quest, nodeDir, baseonfigNode, node.getNodeList("questions"));
            }

            String answer = node.get("answer", String.class);
            String message = node.get("message", String.class);
            List<QuestNodeAnswerAction> actions = new ArrayList<>();

            if (node.contains("actions")) {
                for (ConfigurationNode configNode : node.getNodeList("actions")) {
                    QuestNodeAnswerAction action = null;

                    if (configNode.contains("node")) {
                        action = new QuestNodeAnswerActionNode(this.loadNode(plugin, quest, nodeDir, configNode.get("node", String.class) + ".yml"));
                        actions.add(action);
                    } else if (configNode.contains("collect_item")) {
                        action = new QuestNodeCollectItemAction(null);
                    }

                    if (action != null) {
                        if (configNode.contains("requirements")) {
                            System.out.println("Does have requirement");
                            List<QuestNodeRequirement> requirements = this.loadNodeRequirementsFromFile(baseonfigNode, quest, configNode.get("requirements", String.class).split(","));
                            action.setRequirements(requirements);
                        } else {
                            System.out.println("Does not contin requiremets");
                        }

                        actions.add(action);
                    }
                }
            }

            loadedQuestions.add(new QuestNodeQuestion(message, answer, nodeQuestions, actions));
        }

        return loadedQuestions;
    }

    private List<QuestNodeRequirement> loadNodeRequirementsFromFile(ConfigurationNode baseConfigNode, Quest quest, String... requirementNames) {
        List<QuestNodeRequirement> requirements = new ArrayList<>();

        for (String requirementName : requirementNames) {
            ConfigurationNode requirementNode = null;

            for (ConfigurationNode itrRequirementNode : baseConfigNode.getNodeList("requirements")) {
                if (itrRequirementNode.get("name", String.class).equalsIgnoreCase(requirementName)) requirementNode = itrRequirementNode;
            }

            if (requirementNode == null) continue;

            QuestNodeRequirementType requirementType = QuestNodeRequirementType.valueOf(requirementNode.get("type", String.class).toUpperCase());

            if (requirementType == QuestNodeRequirementType.COLLECT_ITEM) {
                ConfigurationNode itemNode = requirementNode.getNode("item");
                Material itemType = Material.getMaterial(itemNode.get("type", String.class).toUpperCase());
                int amount = itemNode.get("amount", Integer.class);

                if (itemType == null) {
                    BiomeCraft.getInstance().log(Level.WARNING, "Failed to find item " + itemNode.get("type", String.class) + " while loading quest " + quest.getName());
                    continue;
                }

                ItemStack item = new ItemStack(itemType, amount);

                QuestNodeRequirement requirement = new QuestNodeCollectItemRequirement(item);
                requirements.add(requirement);
            }
        }

        return requirements;
    }

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

    public void loadJournal(BiomeCraftPlayer p) {
        QuestJournal journal = new QuestJournal(p.getRoot());

        JavaPlugin plugin = BiomeCraft.getProvidingPlugin(BiomeCraft.class);
        FileConfiguration pJournalDataConfig = new FileConfiguration(plugin, "data" + File.separator + "journals" + File.separator + p.getRoot().getUniqueId().toString() + ".yml");
        pJournalDataConfig.load();

        for (ConfigurationNode progressNode : pJournalDataConfig.getNodeList("quests")) {
            String questName = progressNode.get("quest-name", String.class).replaceAll(".yml", "");
            String nodeName = progressNode.get("current-node", String.class);

            try {
                Quest quest = BiomeCraft.getInstance().getHandler(QuestHandler.class).getQuest(questName);

                if (quest.getNode().getNodeName().equalsIgnoreCase(nodeName)) {
                    journal.setQuestNode(quest.getNode(), quest);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

        p.setQuestJournal(journal);
    }
}
