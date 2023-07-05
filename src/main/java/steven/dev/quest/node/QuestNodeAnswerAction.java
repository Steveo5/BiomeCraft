package steven.dev.quest.node;

import steven.dev.BiomeCraftPlayer;
import steven.dev.quest.Quest;
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

    public boolean hasMetRequirements(BiomeCraftPlayer player) {
        System.out.println(this.getRequirements());
        if (this.getRequirements() != null && this.getRequirements().size() > 0) {
            return false;
        } else {
            return true;
        }
    }

    public abstract void execute(BiomeCraftPlayer player, Quest quest);
}
