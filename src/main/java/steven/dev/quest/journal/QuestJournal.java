package steven.dev.quest.journal;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import steven.dev.BiomeCraft;
import steven.dev.quest.Quest;
import steven.dev.quest.QuestJournalDataHandler;
import steven.dev.quest.QuestRequirementQuery;
import steven.dev.quest.node.QuestNode;
import steven.dev.quest.node.requirements.QuestNodeRequirement;
import steven.dev.quest.node.requirements.QuestNodeRequirementType;

import java.util.ArrayList;
import java.util.List;

public class QuestJournal {
    private Player owner;
    private List<QuestJournalProgress> questProgress = new ArrayList<>();

    public QuestJournal(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }

    public List<QuestJournalProgress> getQuestProgress() {
        return questProgress;
    }

    public boolean addQuest(Quest quest) {
        for (QuestJournalProgress journalQuest : questProgress) {
            if (journalQuest.getQuest().equals(quest)) {
                return false;
            }
        }

        questProgress.add(new QuestJournalProgress(quest.getNode(), quest));

        return true;
    }

    public boolean setQuestNode(QuestNode node, Quest quest) {
        for (QuestJournalProgress journalQuest : questProgress) {
            if (journalQuest.getQuest().getName().equalsIgnoreCase(quest.getName())) {
                journalQuest.setNode(node);
                return true;
            }
        }

        QuestJournalProgress newQuestProgress = new QuestJournalProgress(node, quest);
        questProgress.add(newQuestProgress);

        return false;
    }

    public QuestJournalProgress getQuestProgress(Quest quest) {
        for (QuestJournalProgress questJournalProgress : this.getQuestProgress()) {
            if (questJournalProgress.getQuest().equals(quest)) {
                return questJournalProgress;
            }
        }

        return null;
    }

    public void save() {
        try {
            BiomeCraft.getInstance().getHandler(QuestJournalDataHandler.class).saveToDisk(this, this.getOwner());
        } catch (ClassNotFoundException evt) {
            evt.printStackTrace();
        }
    }

    public boolean isInProgress(Quest quest) {
        for (QuestJournalProgress questJournalProgress : this.getQuestProgress()) {
            if (questJournalProgress.getQuest().equals(quest)) {
                return true;
            }
        }

        return false;
    }

    public void removeQuest(String name) {
        this.getQuestProgress().removeIf(progress -> progress.getQuest().getName().equalsIgnoreCase(name));
    }
}
