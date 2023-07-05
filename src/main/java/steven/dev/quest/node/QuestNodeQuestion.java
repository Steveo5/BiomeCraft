package steven.dev.quest.node;

import java.util.ArrayList;
import java.util.List;

public class QuestNodeQuestion {
    // The message sent to the player if they click the answer
    private String message;
    // The answer to activate the questions
    private String answer;
    private List<QuestNodeQuestion> questions = new ArrayList<>();
    private List<QuestNodeAnswerAction> actions;

    public QuestNodeQuestion(String message, String answer, List<QuestNodeQuestion> questions, List<QuestNodeAnswerAction> actions) {
        this.message = message;
        this.answer = answer;
        this.questions = questions;
        this.actions = actions;
    }

    public String getMessage() {
        return message;
    }

    public List<QuestNodeQuestion> getQuestions() {
        return questions;
    }

    public String getAnswer() {
        return answer;
    }

    public List<QuestNodeAnswerAction> getQuestNodeAnswerActions() {
        return actions;
    }
}
