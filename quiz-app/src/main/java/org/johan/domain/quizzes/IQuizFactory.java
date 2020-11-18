package org.johan.domain.quizzes;

import org.johan.domain.customers.Customer;
import org.johan.domain.quizzes.questions.Question;
import org.johan.domain.quizzes.questions.answers.AnswerCollection;
import org.johan.domain.quizzes.valueObjects.*;

public interface IQuizFactory {

    Quiz newQuiz(Customer customer, Quiz quiz);
}
