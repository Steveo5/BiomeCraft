package steven.dev.quest.journal;

import steven.dev.quest.Quest;
import steven.dev.quest.node.QuestNode;
import steven.dev.quest.node.requirements.QuestNodeException;
import steven.dev.quest.node.requirements.QuestNodeRequirement;

import java.util.HashMap;
import java.util.Map;

public class QuestJournalProgress {
    private QuestNode node;
    private Quest quest;
    // The requirements get set when the player actually accepts the quest
    private HashMap<QuestNodeRequirement, QuestNodeRequirementProgress<Object>> requirementProgress = new HashMap<>();

    public QuestJournalProgress(QuestNode node, Quest quest) {
        this.node = node;
        this.quest = quest;
    }

    public QuestNode getNode() {
        return node;
    }

    public void setNode(QuestNode node) {
        this.node = node;
    }

    public Quest getQuest() {
        return quest;
    }

    public <T> void setRequirementProgress(String name, QuestNodeRequirementProgress<Object> progress) throws QuestNodeException {
        for (QuestNodeRequirement requirement : this.requirementProgress.keySet()) {
            if (requirement.getName().equalsIgnoreCase(name)) {
                requirementProgress.put(requirement, progress);
                return;
            }
        }

        throw new QuestNodeException("Unknown quest requirement named in set required progress");
    }

    public QuestNodeRequirementProgress<Object> getRequirementProgress(String name) throws QuestNodeException {
        for (Map.Entry<QuestNodeRequirement, QuestNodeRequirementProgress<Object>> requirement : this.requirementProgress.entrySet()) {
            if (requirement.getKey().getName().equalsIgnoreCase(name)) {
                return requirement.getValue();
            }
        }

        throw new QuestNodeException("Unknown quest requirement named");
    }
}
