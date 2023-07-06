package steven.dev.quest.data;

import com.bergerkiller.bukkit.common.config.ConfigurationNode;
import com.bergerkiller.bukkit.common.config.FileConfiguration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import steven.dev.BiomeCraft;
import steven.dev.quest.Quest;
import steven.dev.quest.node.*;
import steven.dev.quest.node.requirements.QuestNodeException;
import steven.dev.quest.node.requirements.QuestNodeItemRequirement;
import steven.dev.quest.node.requirements.QuestNodeRequirement;
import steven.dev.quest.node.requirements.QuestNodeRequirementType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class QuestNodeLoader {
    private Quest quest;
    private File nodeDir;
    private String nodeName;
    private File nodeFile;
    private QuestNode node;
    private FileConfiguration nodeConfig;

    public QuestNodeLoader(Quest quest, File nodeDir, String nodeName) {
        this.quest = quest;
        this.nodeDir = nodeDir;
        this.nodeName = nodeName;
        this.nodeFile = new File(nodeDir + File.separator + nodeName);

        if (nodeFile.exists()) {
            nodeConfig = new FileConfiguration(BiomeCraft.getInstance(), "quests" + File.separator + quest.getName() + File.separator + "nodes" + File.separator + nodeName);
            nodeConfig.load();
            String message = nodeConfig.get("message", String.class);

            node = new QuestNode(nodeName.replaceAll(".yml", ""), message);
            node.setQuestNodeQuestions(this.loadQuestions(this.nodeConfig.getNodeList("questions")));
        }
    }

    public Quest getQuest() {
        return quest;
    }

    public File getNodeDir() {
        return nodeDir;
    }

    public String getNodeName() {
        return nodeName;
    }

    public QuestNode load() {
        return this.node;
    }

    private List<QuestNodeQuestion> loadQuestions(List<ConfigurationNode> questions) {
        List<QuestNodeQuestion> loadedQuestions = new ArrayList<>();

        for (ConfigurationNode configurationNode : questions) {
            List<QuestNodeQuestion> nodeQuestions = new ArrayList<>();

            if (configurationNode.contains("questions")) {
                nodeQuestions = this.loadQuestions(configurationNode.getNodeList("questions"));
            }

            String answer = configurationNode.get("answer", String.class);
            String message = configurationNode.get("message", String.class);
            List<QuestNodeAnswerAction> actions = new ArrayList<>();

            if (configurationNode.contains("actions")) {
                for (ConfigurationNode configNode : configurationNode.getNodeList("actions")) {
                    QuestNodeAnswerAction action = null;

                    if (configNode.contains("node")) {
                        action = new QuestNodeAnswerActionNode(new QuestNodeLoader(this.getQuest(), this.getNodeDir(), configNode.get("node", String.class) + ".yml").load());
                        actions.add(action);
                    } else if (configNode.contains("collect_reward")) {
                        action = new QuestNodeCollectItemAction(new ItemStack(Material.ACACIA_BOAT));
                    }

                    if (action != null) {
                        if (configNode.contains("requirements")) {
                            List<QuestNodeRequirement> requirements = this.loadNodeRequirementsFromFile(configNode.get("requirements", String.class).split(","));
                            action.setRequirements(requirements);
                        }

                        actions.add(action);
                    }
                }
            }

            loadedQuestions.add(new QuestNodeQuestion(message, answer, nodeQuestions, actions));
        }

        return loadedQuestions;
    }

    private List<QuestNodeRequirement> loadNodeRequirementsFromFile(String... requirementNames) {
        List<QuestNodeRequirement> requirements = new ArrayList<>();

        for (String requirementName : requirementNames) {
            ConfigurationNode requirementNode = null;

            for (ConfigurationNode itrRequirementNode : this.nodeConfig.getNodeList("requirements")) {
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

                ItemStack item = new ItemStack(itemType);

                QuestNodeRequirement requirement = new QuestNodeItemRequirement(requirementName, item, amount);
                requirements.add(requirement);
            }
        }

        return requirements;
    }
}
