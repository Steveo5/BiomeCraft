package steven.dev.quest.node;

import steven.dev.BiomeCraftPlayer;
import steven.dev.quest.node.requirements.QuestNodeRequirement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestNode {
    // The requirements to enter this quest node
//    private List<QuestNodeRequirement> nodeRequirements = null;
    // The subnodes of this quest, if empty then this is effectively the last node and the quest is marked complete
//    private List<QuestNode> nodes = new ArrayList<>();
    // The node id/name
    private String nodeName;
    // The reward for entering this node
    private String message;
    private List<QuestNodeQuestion> questNodeQuestions;
    private List<QuestNodeRequirement> requirements = new ArrayList<>();

    public QuestNode(String nodeName, String message) {
        this.nodeName = nodeName;
        this.message = message;
    }

//    public List<QuestNodeRequirement> getNodeRequirements() {
//        return nodeRequirements;
//    }
//
//    public QuestNode setNodeRequirements(List<QuestNodeRequirement> requirements) {
//        this.nodeRequirements = requirements;
//        return this;
//    }
//
//    public void addRequirement(QuestNodeRequirement requirement) {
//        this.nodeRequirements.add(requirement);
//    }

    public String getNodeName() {
        return nodeName;
    }

    public String getMessage() {
        return message;
    }

    public List<QuestNodeQuestion> getQuestNodeQuestions() {
        return questNodeQuestions;
    }

    public void setQuestNodeQuestions(List<QuestNodeQuestion> questNodeQuestions) {
        this.questNodeQuestions = questNodeQuestions;
    }

    /**
     * Get all quest node requirements over all question chains
     * @return
     */
    public List<QuestNodeRequirement> getAllRequirements() {
        return this.requirements;
    }

    public void setAllRequirements(List<QuestNodeRequirement> requirements) {
        this.requirements = requirements;
    }
}
