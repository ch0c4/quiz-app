package org.johan.domain.quizzes;

import org.johan.domain.common.CustomerId;
import org.johan.domain.quizzes.questions.Question;
import org.johan.domain.quizzes.questions.QuestionCollection;
import org.johan.domain.quizzes.valueObjects.QuestionId;
import org.johan.domain.quizzes.valueObjects.QuizId;

import java.sql.Timestamp;

public class Quiz {

    private QuizId quizId;

    private QuestionCollection questions;

    private Boolean completed;

    private Timestamp timeFinished;

    private final CustomerId customerId;

    public Quiz(QuizId quizId, QuestionCollection questions, Boolean completed, Timestamp timeFinished, CustomerId customerId) {
        this.quizId = quizId;
        this.questions = questions;
        this.completed = completed;
        this.timeFinished = timeFinished;
        this.customerId = customerId;
    }

    public Quiz(QuestionCollection questions, CustomerId customerId) {
        this.questions = questions;
        this.completed = false;
        this.timeFinished = new Timestamp(0L);
        this.customerId = customerId;
    }

    public QuizId getQuizId() {
        return quizId;
    }

    public QuestionCollection getQuestions() {
        return questions;
    }

    public void setQuestions(QuestionCollection questions) {
        this.questions = questions;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void isCompleted(Timestamp timeFinished) {
        this.completed = true;
        this.timeFinished = timeFinished;
    }

    public Timestamp getTimeFinished() {
        return timeFinished;
    }

    public Integer getTotalCorrectAnswer() {
        return this.questions.getTotalCorrectAnswer();
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public Question getNextQuestion() {
        return this.questions.getNextQuestion();
    }

    public Question getQuestion(QuestionId questionId) {
        return this.getQuestions().getQuestion(questionId);
    }
}
