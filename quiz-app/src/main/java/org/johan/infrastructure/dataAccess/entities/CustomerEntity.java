package org.johan.infrastructure.dataAccess.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Builder
@Entity
@Table(name = "customer")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<QuizEntity> quizzes;

    public CustomerEntity() {
    }

    public CustomerEntity(Long id, String email, String password, Set<QuizEntity> quizzes) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.quizzes = quizzes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<QuizEntity> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(Set<QuizEntity> quizzes) {
        this.quizzes = quizzes;
    }
}
