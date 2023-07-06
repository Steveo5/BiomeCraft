package steven.dev.quest.journal;

import steven.dev.quest.node.requirements.QuestNodeException;

public class QuestNodeRequirementProgress<T> {
    private T progress;

    public QuestNodeRequirementProgress(T progress) throws QuestNodeException {
        if (!(progress instanceof Integer) && !(progress instanceof Boolean) && !(progress instanceof String))  {
            throw new QuestNodeException("Progress can only be a boolean, string or integer");
        }
    }

    public String asString()  {
        return (String)progress;
    }

    public boolean asBoolean() {
        return (boolean)progress;
    }

    public int asInt() {
        return (int)progress;
    }
}
