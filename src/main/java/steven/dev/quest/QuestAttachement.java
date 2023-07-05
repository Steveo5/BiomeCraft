package steven.dev.quest;

import org.bukkit.block.Block;

public class QuestAttachement {
    private Block block;

    public void setBlock(Block block) {
        this.block = block;
    }

    public boolean hasBlock() {
        return block != null;
    }

    public Block getBlock() {
        return block;
    }
}
