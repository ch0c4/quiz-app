package org.johan.domain.customers;


import org.johan.domain.quizzes.Quiz;

import java.util.ArrayList;
import java.util.List;

public class QuizCollection {

    private final List<Quiz> quizzes;

    public QuizCollection(List<Quiz> quizzes) {
        this.quizzes = new ArrayList<>(quizzes);
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public void addQuiz(Quiz quiz) {
        this.quizzes.add(quiz);
    }
}
