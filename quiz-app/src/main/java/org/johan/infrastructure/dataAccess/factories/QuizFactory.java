package org.johan.infrastructure.dataAccess.factories;

import org.johan.domain.common.CustomerId;
import org.johan.domain.customers.Customer;
import org.johan.domain.quizzes.IQuizFactory;
import org.johan.domain.quizzes.Quiz;
import org.johan.domain.quizzes.questions.Question;
import org.johan.domain.quizzes.questions.QuestionCollection;
import org.johan.domain.quizzes.questions.answers.Answer;
import org.johan.domain.quizzes.questions.answers.AnswerCollection;
import org.johan.domain.quizzes.valueObjects.*;
import org.johan.infrastructure.dataAccess.entities.CustomerEntity;
import org.johan.infrastructure.dataAccess.entities.QuestionEntity;
import org.johan.infrastructure.dataAccess.entities.QuizEntity;
import org.johan.infrastructure.dataAccess.hibernate.QuestionHibernateRepository;
import org.johan.infrastructure.dataAccess.hibernate.QuizHibernateRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Singleton
public class QuizFactory implements IQuizFactory {

    @Inject
    private QuizHibernateRepository quizRepository;

    @Inject
    private QuestionHibernateRepository questionRepository;

    @Override
    public Quiz newQuiz(Customer customer, Quiz quiz) {
        QuizEntity savedEntity = this.quizRepository.update(mapNewQuiz(quiz, customer));

        Set<QuestionEntity> questionEntities = new HashSet<>();
        for (Question question: quiz.getQuestions().getQuestions()) {
            questionEntities.add(this.questionRepository.update(mapQuestion(question, savedEntity)));
        }
        savedEntity.setQuestions(questionEntities);
        return mapQuiz(savedEntity, customer.getCustomerId());
    }

    private static CustomerEntity mapCustomer(Customer customer) {
        return CustomerEntity.builder()
                .id(customer.getCustomerId().getId())
                .email(customer.getEmail().getText())
                .password(customer.getPassword().getText())
                .build();
    }

    private static QuizEntity mapNewQuiz(Quiz quiz, Customer customer) {
        return QuizEntity.builder()
                .id(null)
                .completed(quiz.getCompleted())
                .timeFinished(quiz.getTimeFinished().getTime())
                .customer(mapCustomer(customer))
                .build();
    }

    private static Quiz mapQuiz(QuizEntity quizEntity, CustomerId customerId) {

        return new Quiz(
                new QuizId(quizEntity.getId()),
                new QuestionCollection(quizEntity.getQuestions().stream().map(QuizFactory::mapQuestion).collect(Collectors.toList())),
                quizEntity.getCompleted(),
                new Timestamp(quizEntity.getTimeFinished()),
                customerId);
    }

    private static Question mapQuestion(QuestionEntity questionEntity) {
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer(questionEntity.getCorrectAnswer(), true));
        answers.addAll(
                questionEntity.getIncorrectAnswer().stream()
                        .map(answer -> new Answer(answer, false)).collect(Collectors.toSet()));

        return new Question(new QuestionId(
                questionEntity.getId()),
                Category.parse(questionEntity.getCategory()),
                Difficulty.valueOf(questionEntity.getDifficulty().toUpperCase()),
                new Subject(questionEntity.getQuestion()),
                QuestionStatus.valueOf(questionEntity.getStatus().toUpperCase()),
                new AnswerCollection(answers));
    }

    private static QuestionEntity mapQuestion(Question question, QuizEntity quizEntity) {
        return QuestionEntity.builder()
                .id(null)
                .category(question.getCategory().getText())
                .difficulty(question.getDifficulty().getText())
                .status(question.getQuestionStatus().toString())
                .question(question.getSubject().getText())
                .correctAnswer(question.getCorrectAnswer().getText())
                .incorrectAnswer(question.getIncorrectAnswer().stream().map(Answer::getText).collect(Collectors.toSet()))
                .quiz(quizEntity)
                .build();
    }

}
