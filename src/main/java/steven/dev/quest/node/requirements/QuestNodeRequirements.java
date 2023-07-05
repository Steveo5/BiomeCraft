package steven.dev.quest.node.requirements;

import java.util.ArrayList;
import java.util.List;

public class QuestNodeRequirements {
    private final List<QuestNodeRequirement> requirements = new ArrayList<>();

    public void addRequirement(QuestNodeRequirement requirement) {
        this.requirements.add(requirement);
    }

    public List<QuestNodeRequirement> getRequirements() {
        return this.requirements;
    }


}
