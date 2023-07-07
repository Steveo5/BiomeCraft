package steven.dev.quest.node;

import steven.dev.BiomeCraft;
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

    protected abstract void execute(BiomeCraftPlayer player, Quest quest);
    public void executeWithRequirements(BiomeCraftPlayer player, Quest quest) throws QuestNodeException {
        if (this.getRequirements() != null) {
            for (QuestNodeRequirement requirement : this.getRequirements()) {
                requirement.execute(player);
            }
        }

        this.execute(player, quest);
    }
}
