package steven.dev;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import steven.dev.quest.node.requirements.QuestNodeRequirementType;

public class Translate {
//    public static TextComponent tl(String name, String subject, String... replacers) {
//
//    }

    public static TextComponent tl(String name) {
        if (name.equalsIgnoreCase("playerOnly")) {
            return Component.text("You must be a player to execute this command");
        }

        return Component.text("");
    }

    public static TextComponent tl(QuestNodeRequirementType requirementType, String... replacers) {
        switch (requirementType) {
            case COLLECT_ITEM ->  {
                return Component.text(String.format("I have been tasked with collecting %s", replacers));
            }
        }

        return null;
    }
}
