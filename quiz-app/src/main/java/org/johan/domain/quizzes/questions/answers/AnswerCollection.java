package org.johan.domain.quizzes.questions.answers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AnswerCollection {

    private final List<Answer> answers;

    public AnswerCollection(List<Answer> answers) {
        this.answers = new ArrayList<>(answers);
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public Answer getCorrectAnswer() {
        return answers.stream().filter(Answer::isCorrect).findFirst().orElse(null);
    }

    public List<Answer> getIncorrectAnswer() {
        return answers.stream().filter(answer -> !answer.isCorrect()).collect(Collectors.toList());
    }
}
