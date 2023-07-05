package steven.dev.quest.journal;

import net.kyori.adventure.text.format.NamedTextColor;
import steven.dev.BiomeCraftPlayer;
import steven.dev.ScoreboardType;
import steven.dev.gui.*;
import steven.dev.quest.node.requirements.QuestNodeRequirement;

import java.util.ArrayList;
import java.util.List;

public class QuestJournalBook {
    private final BookGUI book;
    private final BiomeCraftPlayer player;

    public QuestJournalBook(BiomeCraftPlayer player) {
        book = new BookGUI();

        List<BookGUIComponent> page = new ArrayList<>();

        for (QuestJournalProgress questJournalProgress : player.getQuestJournal().getQuestProgress()) {
            page.add(new HeaderComponent(questJournalProgress.getQuest().getTitle() + "\n"));
            page.add(new HrComponent(NamedTextColor.BLACK));
//            page.addAll(questJournalProgress.getQuest().getNode().getNodeRequirements().stream().map(QuestNodeRequirement::getBookSummary).toList());
            page.add(new HrComponent(NamedTextColor.BLACK));

            page.add(
                    new ButtonGroupComponent(
                            new ButtonComponent("Abandon", NamedTextColor.DARK_AQUA, ButtonComponentAction.CUSTOM).clickEvent(ad -> {
                                player.getQuestJournal().removeQuest(questJournalProgress.getQuest().getName());
                                player.showScoreboard(ScoreboardType.QUEST_JOURNAL);
                            })
                    )
            );

            BookGUIComponent[] arr = new BookGUIComponent[page.size()];
            arr = page.toArray(arr);

            book.addPage(new BookGUIPage().setContent(arr));
        }

//        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
//        BookMeta bookMeta = (BookMeta) book.getItemMeta();
//
//        QuestJournal journal = player.getQuestJournal();
//
//        for (QuestJournalProgress questProgress : journal.getQuestProgress()) {
//            TextComponent.Builder description = Component.text()
//                    .append(Component.text(questProgress.getQuest().getTitle()))
//                    .append(Component.text("-------------------\n"));
//
//
//            for (QuestNodeRequirement requirement : questProgress.getNode().getNodeRequirements().getRequirements()) {
//                description.append(Component.text("x" + requirement.getAsItem().getAmount() + " " + requirement.getAsItem().getType().toString()));
//            }
//
//            description.append(Component.text("-------------------\n"));
//            bookMeta.addPages(description.build());
//        }
//
//
//        bookMeta.setTitle(player.getName());
//        bookMeta.setAuthor(player.getName());
//
//        book.setItemMeta(bookMeta);
        this.player = player;
    }

    public void open() {
        book.open(player.getRoot());
    }
}
