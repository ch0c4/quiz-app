package org.johan.domain.quizzes.questions.answers;

import java.util.Objects;

public class Answer {

    private final String text;

    private final Boolean correct;

    public Answer(String text, Boolean correct) {
        this.text = text;
        this.correct = correct;
    }

    public Answer(String text) {
        this.text = text;
        this.correct = false;
    }

    public String getText() {
        return text;
    }

    public Boolean isCorrect() {
        return correct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return Objects.equals(text, answer.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, correct);
    }

    @Override
    public String toString() {
        return text;
    }
}
