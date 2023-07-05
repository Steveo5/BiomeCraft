package steven.dev.quest;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import steven.dev.BiomeCraft;
import steven.dev.BiomeCraftPlayer;
import steven.dev.quest.journal.QuestJournalBook;

import static steven.dev.Translate.tl;

public class QuestCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("open")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(tl("playerOnly"));
                    return true;
                }

                if (args.length < 2) {
                    sender.sendMessage(Component.text("You must enter a quest name", NamedTextColor.RED));
                    return true;
                }

                BiomeCraftPlayer player = BiomeCraft.getPlayer((Player)sender);

                if (player == null) {
                    return true;
                }

                try {
                    QuestHandler questHandler = BiomeCraft.getInstance().getHandler(QuestHandler.class);
                    Quest quest = questHandler.getQuest(args[1]);
                    quest.show(player);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (args[0].equalsIgnoreCase("create")) {
//                Dialogue dialogue = new Dialogue.Builder()
//                        .addPrompt(
//                                new Prompt.Builder()
//                                        .setText("&aWhat is the quest name?")
//                                        .setType(PromptInputType.LETTERS_AND_NUMBERS)
//                                        .addReceiveInputAction(Action.STORE_INPUT, new ActionContext<>("questName"))
//                        ).addPrompt(
//                                new Prompt.Builder()
//                                        .setText("&aWhat is the quest title?")
//                                        .setType(PromptInputType.LETTERS_AND_NUMBERS)
//                                        .addReceiveInputAction(Action.STORE_INPUT, new ActionContext<>("questTitle"))
//                        ).addPrompt(
//                                new Prompt.Builder()
//                                        .setText("&aWhat is the quest description?")
//                                        .setType(PromptInputType.LETTERS_AND_NUMBERS)
//                                        .addReceiveInputAction(Action.STORE_INPUT, new ActionContext<>("questDescription"))
//                                        .addReceiveInputAction( (context, input) -> {
//                                            // TODO
////                                            try {
////                                                BiomeCraft.getQuestHandler().initializeQuestStructure(context.getStoredInput("questName"), context.getStoredInput("questTitle"), context.getStoredInput("questDescription"));
////                                            } catch (IOException e) {
////                                                throw new RuntimeException(e);
////                                            }
//                                            context.getResponder().sendMessage("Quest structure created, continue editing in the quest directory");
//                                        })
//                        )
//                        // Sequences to exit the dialogue.
//                        .setEscapeSequences("exit")
//                        // Code that runs when the dialogue ends.
//                        // Whether the prompt gets repeated when you give invalid input.
//                        .setRepeatPrompt(true)
//                        .build();

                Player p = (Player)sender;
//                DialogueAPI.startDialogue(p, dialogue);
                return true;
            } else if (args[0].equalsIgnoreCase("journal") || args[0].equalsIgnoreCase("j")) {
                if (sender instanceof Player) {
                    BiomeCraftPlayer player = BiomeCraft.getPlayer((Player)sender);

                    if (player != null) {
                        new QuestJournalBook(player).open();
                    }
                } else {
                    sender.sendMessage(tl("playerOnly"));
                    return true;
                }
            }
        }

        return false;
    }
}
