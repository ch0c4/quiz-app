package org.johan.infrastructure.dataAccess.repositories;

import org.johan.domain.common.CustomerId;
import org.johan.domain.customers.Customer;
import org.johan.domain.customers.ICustomerRepository;
import org.johan.domain.customers.QuizCollection;
import org.johan.domain.customers.valueObjects.Email;
import org.johan.domain.customers.valueObjects.Password;
import org.johan.domain.quizzes.Quiz;
import org.johan.domain.quizzes.questions.Question;
import org.johan.domain.quizzes.questions.QuestionCollection;
import org.johan.domain.quizzes.questions.answers.Answer;
import org.johan.domain.quizzes.questions.answers.AnswerCollection;
import org.johan.domain.quizzes.valueObjects.*;
import org.johan.infrastructure.dataAccess.entities.CustomerEntity;
import org.johan.infrastructure.dataAccess.entities.QuestionEntity;
import org.johan.infrastructure.dataAccess.entities.QuizEntity;
import org.johan.infrastructure.dataAccess.hibernate.CustomerHibernateRepository;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomerRepository implements ICustomerRepository {

    @Inject
    private CustomerHibernateRepository customerRepository;

    @Override
    public Customer find(CustomerId customerId) {
        Optional<CustomerEntity> optionalCustomerEntity = this.customerRepository.findById(customerId.getId());
        return optionalCustomerEntity.map(CustomerRepository::mapCustomer).orElse(null);
    }


    @Override
    public Customer findByEmail(Email email) {
        Optional<CustomerEntity> optionalCustomerEntity = this.customerRepository.findByEmail(email.getText());
        return optionalCustomerEntity.map(CustomerRepository::mapCustomer).orElse(null);
    }

    @Override
    public void update(Customer customer) {

    }

    @Override
    public QuizCollection getQuizzes(CustomerId customerId) {
        Optional<CustomerEntity> optionalCustomerEntity = this.customerRepository.findById(customerId.getId());
        Customer customer = optionalCustomerEntity.map(CustomerRepository::mapCustomerWithQuiz).orElse(null);

        if(customer != null) {
            return customer.getQuizzes();
        } else {
            return null;
        }
    }

    private static Customer mapCustomer(CustomerEntity customerEntity) {
        return new Customer(
                new CustomerId(customerEntity.getId()),
                new Email(customerEntity.getEmail()),
                new Password(customerEntity.getPassword()));
    }

    private static Customer mapCustomerWithQuiz(CustomerEntity customerEntity) {
        return new Customer(
                new CustomerId(customerEntity.getId()),
                new Email(customerEntity.getEmail()),
                new Password(customerEntity.getPassword()),
                new QuizCollection(customerEntity.getQuizzes().stream().map(CustomerRepository::mapQuiz).collect(Collectors.toList())));
    }

    private static Quiz mapQuiz(QuizEntity quizEntity) {
        List<Question> questions = quizEntity.getQuestions()
                .stream()
                .map(CustomerRepository::mapQuestion)
                .collect(Collectors.toList());

        return new Quiz(
                new QuizId(quizEntity.getId()),
                new QuestionCollection(questions),
                quizEntity.getCompleted(),
                new Timestamp(quizEntity.getTimeFinished()),
                new CustomerId(quizEntity.getCustomer().getId()));
    }

    private static Question mapQuestion(QuestionEntity questionEntity) {
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer(questionEntity.getCorrectAnswer(), true));
        answers.addAll(
                questionEntity.getIncorrectAnswer().stream()
                        .map(answer -> new Answer(answer, false)).collect(Collectors.toSet()));

        return new Question(
                new QuestionId(questionEntity.getId()),
                Category.parse(questionEntity.getCategory()),
                Difficulty.valueOf(questionEntity.getDifficulty().toUpperCase()),
                new Subject(questionEntity.getQuestion()),
                QuestionStatus.valueOf(questionEntity.getStatus().toUpperCase()),
                new AnswerCollection(answers));
    }
}
