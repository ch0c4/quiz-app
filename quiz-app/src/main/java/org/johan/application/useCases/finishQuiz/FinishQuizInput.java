package org.johan.application.useCases.finishQuiz;

import org.johan.application.services.Notification;
import org.johan.domain.quizzes.valueObjects.QuizId;


public class FinishQuizInput {

    private final Notification modelState;

    private QuizId quizId;

    public FinishQuizInput(Long quizId) {
        modelState = new Notification();
        if (quizId != null) {
            this.quizId = new QuizId(quizId);
        } else {
            modelState.add("QuizId", "QuizId is required");
        }
    }

    public Notification getModelState() {
        return modelState;
    }

    public QuizId getQuizId() {
        return quizId;
    }
}

