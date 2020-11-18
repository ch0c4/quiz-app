package org.johan.application.useCases.answeredQuestion;

import org.johan.application.services.Notification;
import org.johan.domain.common.CustomerId;
import org.johan.domain.quizzes.questions.answers.Answer;
import org.johan.domain.quizzes.valueObjects.QuestionId;
import org.johan.domain.quizzes.valueObjects.QuizId;

import java.sql.Timestamp;

public class AnsweredQuestionInput {

    private final Notification modelState;

    private CustomerId customerId;

    private QuizId quizId;

    private QuestionId questionId;

    private Answer answer;

    private Timestamp timeFinished;

    public AnsweredQuestionInput(Long customerId, Long quizId, Long questionId, String answer, Long timeFinished) {
        modelState = new Notification();

        if (customerId != null) {
            this.customerId = new CustomerId(customerId);
        } else {
            modelState.add("CustomerId", "CustomerId is required");
        }

        if (quizId != null) {
            this.quizId = new QuizId(quizId);
        } else {
            modelState.add("QuizId", "QuizId is required");
        }

        if (questionId != null) {
            this.questionId = new QuestionId(questionId);
        } else {
            modelState.add("QuestionId", "QuestionId is required");
        }

        if (answer != null && !answer.trim().isEmpty()) {
            this.answer = new Answer(answer);
        } else {
            modelState.add("Answer", "Answer is required");
        }

        if(timeFinished != null) {
            this.timeFinished = new Timestamp(timeFinished);
        } else {
            modelState.add("Time", "Answer time is required");
        }
    }

    public Notification getModelState() {
        return modelState;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public QuizId getQuizId() {
        return quizId;
    }

    public QuestionId getQuestionId() {
        return questionId;
    }

    public Answer getAnswer() {
        return answer;
    }

    public Timestamp getTimeFinished() {
        return timeFinished;
    }
}
