package org.johan.domain.quizzes.questions;

import org.johan.domain.quizzes.valueObjects.QuestionId;
import org.johan.domain.quizzes.valueObjects.QuestionStatus;

import java.util.ArrayList;
import java.util.List;

public class QuestionCollection {

    private final List<Question> questions;

    public QuestionCollection(List<Question> questions) {
        this.questions = new ArrayList<>(questions);
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void add(Question question) {
        this.questions.add(question);
    }

    public void update(Question question) {
        for (Question q: questions) {
            if(q.getQuestionId().equals(question.getQuestionId())) {
                q = question;
                break;
            }
        }
    }

    public Integer getTotalCorrectAnswer() {
        return (int) questions.stream().filter(Question::hasAnsweredCorrectly).count();
    }

    public Question getNextQuestion() {
        return this.questions
                .stream()
                .filter(question -> question.getQuestionStatus().equals(QuestionStatus.NOT_ANSWERED))
                .findFirst()
                .orElse(null);
    }

    public Question getQuestion(QuestionId questionId) {
        return this.questions
                .stream()
                .filter(question -> question.getQuestionId().equals(questionId))
                .findFirst()
                .orElse(null);
    }
}
