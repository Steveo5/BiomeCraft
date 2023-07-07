package steven.dev.quest.node.requirements;

import net.kyori.adventure.text.TextComponent;
import org.bukkit.inventory.ItemStack;
import steven.dev.BiomeCraftPlayer;
import steven.dev.Handler;
import steven.dev.gui.BookGUIComponent;
import steven.dev.quest.QuestRequirementStatus;
import steven.dev.quest.journal.QuestNodeRequirementProgress;

public abstract class QuestNodeRequirement {
    private String name;
    private QuestNodeRequirementType type;
    private QuestNodeRequirementProgress<Object> defaultValue;

    protected QuestNodeRequirement(String name, QuestNodeRequirementType type) {
        this.name = name;
        this.type = type;
    }

    public QuestNodeRequirementType getType() {
        return type;
    }

    public abstract BookGUIComponent getBookSummary();

    public abstract TextComponent getSidebarSummary(BiomeCraftPlayer player);

    public abstract QuestRequirementStatus compare(QuestNodeRequirementProgress<Object> compare);

    public String getName() {
        return name;
    }

    public void setDefaultValue(QuestNodeRequirementProgress<Object> defaultValue) {
        this.defaultValue = defaultValue;
    }

    public QuestNodeRequirementProgress<Object> getDefaultValue() {
        return this.defaultValue;
    }

    public abstract void execute(BiomeCraftPlayer player) throws QuestNodeException;
}