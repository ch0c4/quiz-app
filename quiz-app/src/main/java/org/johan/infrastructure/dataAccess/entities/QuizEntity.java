package org.johan.infrastructure.dataAccess.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Builder
@Entity
@Table(name = "quiz")
public class QuizEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean completed;

    private Long timeFinished;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @OneToMany(mappedBy = "quiz", fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private Set<QuestionEntity> questions;

    public QuizEntity() {
    }

    public QuizEntity(Long id, Boolean completed, Long timeFinished, CustomerEntity customer, Set<QuestionEntity> questions) {
        this.id = id;
        this.completed = completed;
        this.timeFinished = timeFinished;
        this.customer = customer;
        this.questions = questions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Long getTimeFinished() {
        return timeFinished;
    }

    public void setTimeFinished(Long timeFinished) {
        this.timeFinished = timeFinished;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public Set<QuestionEntity> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<QuestionEntity> questions) {
        this.questions = questions;
    }
}
