package org.johan.infrastructure.quizExchange;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import org.johan.application.services.QuizExchange;
import org.johan.domain.customers.Customer;
import org.johan.domain.quizzes.Quiz;
import org.johan.domain.quizzes.questions.Question;
import org.johan.domain.quizzes.questions.QuestionCollection;
import org.johan.domain.quizzes.questions.answers.Answer;
import org.johan.domain.quizzes.questions.answers.AnswerCollection;
import org.johan.domain.quizzes.valueObjects.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Timestamp;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Singleton
public class QuizClient implements QuizExchange {

    @Inject
    private IQuizClient client;

    @Override
    public Quiz fetchQuiz(Customer customer, Category category, Difficulty difficulty) {
        HttpResponse<QuizPackage> response = client.fetchQuiz(category.getCode(), difficulty.getText());
        if(response.getStatus() != HttpStatus.OK) {
            return null;
        }

        QuizPackage quizPackage = response.getBody().orElse(null);
        if(quizPackage == null) {
            return null;
        }
        return mapQuiz(customer, quizPackage);
    }

    private static Quiz mapQuiz(Customer customer, QuizPackage quizPackage) {
        List<Question> questions = quizPackage.getResults()
                .stream()
                .map(QuizClient::mapQuestion)
                .collect(Collectors.toList());
        return new Quiz(new QuestionCollection(questions), customer.getCustomerId());
    }

    private static Question mapQuestion(QuizResult quizResult) {
        List<Answer> answers = quizResult.getIncorrect_answers()
                .stream()
                .map(Answer::new)
                .collect(Collectors.toList());
        answers.add(new Answer(quizResult.getCorrect_answer(), true));
        return new Question(
                Category.parse(quizResult.getCategory()),
                Difficulty.valueOf(quizResult.getDifficulty().toUpperCase()),
                new Subject(quizResult.getQuestion()),
                new AnswerCollection(answers));
    }



}
