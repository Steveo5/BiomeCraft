package steven.dev.quest.journal;

import com.sun.jdi.InvalidTypeException;
import steven.dev.quest.Quest;
import steven.dev.quest.node.QuestNode;
import steven.dev.quest.node.requirements.QuestJournalRequirementProgress;
import steven.dev.quest.node.requirements.QuestNodeException;
import steven.dev.quest.node.requirements.QuestNodeRequirement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestJournalProgress {
    private QuestNode node;
    private Quest quest;

    // The requirements get set when the player actually accepts the quest
    private HashMap<QuestNodeRequirement, Object> requirementProgress = new HashMap<>();

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

    public <T> void setRequirementProgress(String name, T value) throws QuestNodeException {
        if (!(value instanceof Number) && !(value instanceof Boolean)) throw new QuestNodeException("Requirement progress can only be a number or boolean");

        for (QuestNodeRequirement requirement : this.requirementProgress.keySet()) {
            if (requirement.getName().equalsIgnoreCase(name)) {
                requirementProgress.put(requirement, value);
                return;
            }
        }

        throw new QuestNodeException("Unknown quest requirement named in set required progress");
    }

    public <T> T getRequirementProgress(String name, Class<T> type) throws QuestNodeException {
        if (type != Boolean.class && type != Integer.class) throw new QuestNodeException("Requirement progress can only be a number or boolean");

        
    }
}
