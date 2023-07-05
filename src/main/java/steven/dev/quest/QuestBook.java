package steven.dev.quest;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import steven.dev.BiomeCraft;
import steven.dev.BiomeCraftPlayer;
import steven.dev.gui.*;
import steven.dev.quest.node.QuestNode;
import steven.dev.quest.node.QuestNodeAnswerAction;
import steven.dev.quest.node.QuestNodeQuestion;

import java.util.ArrayList;
import java.util.List;

public class QuestBook {
    private BookGUI book;
    private final BiomeCraftPlayer player;
    private final Quest quest;

    public QuestBook(Quest quest, QuestNode node, BiomeCraftPlayer player) {

        this.quest = quest;
        this.player = player;

        this.setContentAndShow(node.getMessage(), node.getQuestNodeQuestions());
    }

    public void setContentAndShow(String message, String buttonText) {
        List<QuestNodeQuestion> questions = new ArrayList<>();
        questions.add(new QuestNodeQuestion(message, buttonText, new ArrayList<>(), new ArrayList<>()));

        this.setContentAndShow(message, questions);
    }

    public void setContentAndShow(String message) {
        this.setContentAndShow(message, "");
    }

    public void setContentAndShow(String message, List<QuestNodeQuestion> questions) {
        book = new BookGUI();

        List<BookGUIComponent> page = new ArrayList<>();
        page.add(new ParagraphComponent().setText(Component.text(message)));

        for (QuestNodeQuestion question : questions) {
            ButtonComponent button = new ButtonComponent("\n" + question.getAnswer(), NamedTextColor.DARK_AQUA, ButtonComponentAction.CUSTOM).clickEvent(cb -> {
                if (question.getQuestions().size() > 0) {
                    setContentAndShow(question.getMessage(), question.getQuestions());
                } else if (question.getQuestNodeAnswerActions().size() > 0) {
                    // TODO all actions...
                    QuestNodeAnswerAction action = question.getQuestNodeAnswerActions().get(0);

                    setContentAndShow(question.getMessage());

                    if (action.hasMetRequirements(this.player)) {
                        action.execute(this.player, this.quest);
                    } else {
                        this.setContentAndShow("It looks like you don't have what I asked, come back when you do...");
                    }
                } else {
                    if (!question.getAnswer().equalsIgnoreCase("")) {
                        setContentAndShow(question.getMessage());
                    }
                }
            });

            page.add(button);
        }

        BookGUIComponent[] arr = new BookGUIComponent[page.size()];
        arr = page.toArray(arr);

        book.addPage(new BookGUIPage().setContent(arr));
        book.open(player.getRoot());
    }

    public void open() {
//        Merchant merchant = Bukkit.createMerchant(Component.text(":offset_2::all:"));
//
//        ItemStack item = new ItemStack(Material.PAPER, 1);
//        ItemMeta im = item.getItemMeta();
//        im.displayName(Component.text("A lore of elves"));
//        List<Component> lore = new ArrayList<>();
//        lore.add(Component.text("Click to view quest"));
//        im.lore(lore);
//        im.setCustomModelData(10025);
//        item.setItemMeta(im);
//
//        MerchantRecipe recipe = new MerchantRecipe(item, 1000000000);
//        recipe.addIngredient(item);
//
//        List<MerchantRecipe> recipes = new ArrayList<>();
//        recipes.add(recipe);
//        merchant.setRecipes(recipes);
//
//        player.getRoot().openMerchant(merchant, true);
        book.open(player.getRoot());
    }
}
