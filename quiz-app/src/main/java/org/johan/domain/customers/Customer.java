package org.johan.domain.customers;

import org.johan.domain.common.CustomerId;
import org.johan.domain.customers.valueObjects.Email;
import org.johan.domain.customers.valueObjects.Password;
import org.johan.domain.quizzes.Quiz;

import java.util.Collections;

public class Customer {

    private final CustomerId customerId;

    private Email email;

    private Password password;

    private QuizCollection quizzes;

    public Customer(CustomerId customerId, Email email, Password password, QuizCollection quizCollection) {
        this.customerId = customerId;
        this.email = email;
        this.password = password;
        this.quizzes = quizCollection;
    }

    public Customer(CustomerId customerId, Email email, Password password) {
        this.customerId = customerId;
        this.email = email;
        this.password = password;
        this.quizzes = new QuizCollection(Collections.emptyList());
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    public QuizCollection getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(QuizCollection quizzes) {
        this.quizzes = quizzes;
    }

    public void addQuiz(Quiz quiz) {
        this.quizzes.addQuiz(quiz);
    }

    public void update(Email email, Password password, QuizCollection quizzes) {
        this.email = email;
        this.password = password;
        this.quizzes = quizzes;
    }
}
