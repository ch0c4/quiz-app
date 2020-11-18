package org.johan.application.services;

import org.johan.domain.customers.Customer;
import org.johan.domain.quizzes.Quiz;
import org.johan.domain.quizzes.valueObjects.Category;
import org.johan.domain.quizzes.valueObjects.Difficulty;

public interface QuizExchange {

    Quiz fetchQuiz(Customer customer, Category category, Difficulty difficulty);
}
