package steven.dev.quest.node;

import steven.dev.BiomeCraftPlayer;
import steven.dev.quest.Quest;
import steven.dev.quest.QuestRequirementStatus;
import steven.dev.quest.journal.QuestJournalProgress;
import steven.dev.quest.node.requirements.QuestNodeException;
import steven.dev.quest.node.requirements.QuestNodeRequirement;

import java.util.List;

public abstract class QuestNodeAnswerAction {
    private List<QuestNodeRequirement> requirements;

    protected QuestNodeAnswerAction() {}

    public List<QuestNodeRequirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<QuestNodeRequirement> requirements) {
        this.requirements = requirements;
    }

    public boolean hasMetRequirements(QuestJournalProgress playersJournalProgress) {
        System.out.println(this.getRequirements());
        if (this.getRequirements() != null && this.getRequirements().size() > 0) {
            try {
                for (QuestNodeRequirement requirement : this.getRequirements()) {
                    if (requirement.compare(playersJournalProgress.getRequirementProgress(requirement.getName())) != QuestRequirementStatus.COMPLETE) {
                        return false;
                    }
                }

                return true;
            } catch (QuestNodeException e) {
                e.printStackTrace();
            }
        } else {
            return true;
        }

        return false;
    }

    public abstract void execute(BiomeCraftPlayer player, Quest quest);
}
