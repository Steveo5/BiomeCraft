package steven.dev.quest;

import steven.dev.BiomeCraft;
import steven.dev.BiomeCraftPlayer;
import steven.dev.quest.journal.QuestJournalProgress;
import steven.dev.quest.node.QuestNode;

public class Quest {
    private String name;
    private String title;
    private String description;
    private Quest requiredQuestToStart;
    private QuestAttachement attachesTo;
    private QuestNode node;

    public Quest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public Quest setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Quest setDescription(String description) {
        this.description = description;
        return this;
    }

    public Quest getRequiredQuestToStart() {
        return requiredQuestToStart;
    }

    public Quest setRequiredQuestToStart(Quest quest) {
        this.requiredQuestToStart = quest;
        return this;
    }

    public QuestAttachement getAttachesTo() {
        return attachesTo;
    }

    public Quest setAttachesTo(QuestAttachement questAttachement) {
        this.attachesTo = questAttachement;
        return this;
    }

    public QuestNode getNode() {
        return this.node;
    }

    public Quest setNode(QuestNode node) {
        this.node = node;
        return this;
    }

    public void show(BiomeCraftPlayer player) {
        if (player.getQuestJournal().isInProgress(this)) {
            QuestJournalProgress progress = player.getQuestJournal().getQuestProgress(this);

            if (progress != null) new QuestBook(this, progress.getNode(), player).open();
        } else {
            new QuestBook(this, this.getNode(), player).open();
        }
    }

    public boolean isAvailableTo(BiomeCraftPlayer player) {
        // TODO
        return true;
    }

    public boolean canHandIn(BiomeCraftPlayer player) {
        // TODO
        return false;
    }
}
