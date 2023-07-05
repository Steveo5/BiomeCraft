package steven.dev;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.megavex.scoreboardlibrary.api.sidebar.Sidebar;
import net.megavex.scoreboardlibrary.api.sidebar.component.ComponentSidebarLayout;
import net.megavex.scoreboardlibrary.api.sidebar.component.SidebarComponent;
import org.bukkit.entity.Player;
import steven.dev.quest.QuestHandler;
import steven.dev.quest.journal.QuestJournal;
import steven.dev.quest.journal.QuestJournalProgress;

import java.util.ArrayList;
import java.util.List;

public class BiomeCraftPlayer {
    private final Player root;
    private Sidebar sidebar;
    private QuestJournal questJournal;

    protected BiomeCraftPlayer(Player root) {
        this.root = root;

        try {
            sidebar = BiomeCraft.getInstance().getHandler(ScoreboardHandler.class).getScoreboardLibrary().createSidebar();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Player getRoot() {
        return this.root;
    }

    public QuestJournal getQuestJournal() {
        return this.questJournal;
    }

    public void setQuestJournal(QuestJournal journal) {
        this.questJournal = journal;
    }

    public void showScoreboard(ScoreboardType type) {
        switch (type) {
            case QUEST_JOURNAL -> {
                if (this.getQuestJournal().getQuestProgress().size() < 1) {
                    if (this.sidebar != null) {
                        if (this.sidebar.players().contains(this.getRoot())) {
                            this.sidebar.removePlayer(this.getRoot());
                        }
                    }

                    return;
                }

                int questNumber = 1;
                SidebarComponent.Builder linesBuilder = SidebarComponent.builder();
                for (QuestJournalProgress journalProgress : this.getQuestJournal().getQuestProgress()) {
                    linesBuilder.addStaticLine(Component.text(questNumber + ". " + journalProgress.getQuest().getTitle()));
                    questNumber++;
                }

                SidebarComponent lines = linesBuilder.build();

                ComponentSidebarLayout layout = new ComponentSidebarLayout(
                        SidebarComponent.staticLine(Component.text("Quests")),
                        lines
                );

                layout.apply(sidebar);
                sidebar.addPlayer(this.getRoot());
            }
        }
    }

    public String getName() {
        return this.getRoot().getName();
    }
}
