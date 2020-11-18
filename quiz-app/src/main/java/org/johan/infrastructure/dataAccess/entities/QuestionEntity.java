package org.johan.infrastructure.dataAccess.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Builder
@Entity
@Table(name = "question")
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;

    private String difficulty;

    private String question;

    private String status;

    private String correctAnswer;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> incorrectAnswer;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "quiz_id")
    private QuizEntity quiz;

    public QuestionEntity() {
    }

    public QuestionEntity(Long id, String category, String difficulty, String question, String status, String correctAnswer, Set<String> incorrectAnswer, QuizEntity quiz) {
        this.id = id;
        this.category = category;
        this.difficulty = difficulty;
        this.question = question;
        this.status = status;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswer = incorrectAnswer;
        this.quiz = quiz;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Set<String> getIncorrectAnswer() {
        return incorrectAnswer;
    }

    public void setIncorrectAnswer(Set<String> incorrectAnswer) {
        this.incorrectAnswer = incorrectAnswer;
    }

    public QuizEntity getQuiz() {
        return quiz;
    }

    public void setQuiz(QuizEntity quiz) {
        this.quiz = quiz;
    }
}
