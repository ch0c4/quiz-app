package org.johan.domain.quizzes.questions;

import org.johan.domain.quizzes.questions.answers.Answer;
import org.johan.domain.quizzes.questions.answers.AnswerCollection;
import org.johan.domain.quizzes.valueObjects.*;

import java.util.List;

public class Question {

    private QuestionId questionId;

    private final Category category;

    private final Difficulty difficulty;

    private final Subject subject;

    private QuestionStatus questionStatus;

    private final AnswerCollection answers;

    public Question(QuestionId questionId, Category category, Difficulty difficulty, Subject subject, QuestionStatus questionStatus, AnswerCollection answers) {
        this.questionId = questionId;
        this.category = category;
        this.difficulty = difficulty;
        this.subject = subject;
        this.questionStatus = questionStatus;
        this.answers = answers;
    }

    public Question(QuestionId questionId, Category category, Difficulty difficulty, Subject subject, AnswerCollection answers) {
        this.questionId = questionId;
        this.category = category;
        this.difficulty = difficulty;
        this.questionStatus = QuestionStatus.NOT_ANSWERED;
        this.subject = subject;
        this.answers = answers;
    }

    // for Quiz Exchange
    public Question(Category category, Difficulty difficulty, Subject subject, AnswerCollection answers) {
        this.category = category;
        this.difficulty = difficulty;
        this.subject = subject;
        this.questionStatus = QuestionStatus.NOT_ANSWERED;
        this.answers = answers;
    }

    public QuestionId getQuestionId() {
        return questionId;
    }

    public Category getCategory() {
        return category;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public AnswerCollection getAnswers() {
        return answers;
    }

    public QuestionStatus getQuestionStatus() {
        return questionStatus;
    }

    public Subject getSubject() {
        return subject;
    }

    public Answer getCorrectAnswer() {
        return this.answers.getCorrectAnswer();
    }

    public List<Answer> getIncorrectAnswer() {
        return this.answers.getIncorrectAnswer();
    }

    public Boolean hasAnsweredCorrectly() {
        return this.questionStatus.equals(QuestionStatus.ANSWERED_CORRECTLY);
    }

    public void answeredCorrectly() {
        this.questionStatus = QuestionStatus.ANSWERED_CORRECTLY;
    }

    public void answeredWrongly() {
        this.questionStatus = QuestionStatus.ANSWERED_WRONGLY;
    }
}
