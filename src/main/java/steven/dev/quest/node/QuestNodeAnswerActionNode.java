package steven.dev.quest.node;

import steven.dev.BiomeCraftPlayer;
import steven.dev.quest.Quest;

public class QuestNodeAnswerActionNode extends QuestNodeAnswerAction {
    private QuestNode node;

    public QuestNodeAnswerActionNode(QuestNode node) {
        this.node = node;
    }

    public QuestNode getNode() {
        return node;
    }

    @Override
    public void execute(BiomeCraftPlayer player, Quest quest) {
        player.getQuestJournal().setQuestNode(this.getNode(), quest);
    }
}
