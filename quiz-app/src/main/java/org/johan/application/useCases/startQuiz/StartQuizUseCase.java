package org.johan.application.useCases.startQuiz;

import org.johan.application.exceptions.client.ClientErrorException;
import org.johan.application.exceptions.customer.CustomerNotFoundException;
import org.johan.application.exceptions.startQuiz.StartQuizBadRequestException;
import org.johan.application.services.QuizExchange;
import org.johan.domain.common.CustomerId;
import org.johan.domain.customers.Customer;
import org.johan.domain.customers.ICustomerRepository;
import org.johan.domain.quizzes.IQuizFactory;
import org.johan.domain.quizzes.Quiz;
import org.johan.domain.quizzes.valueObjects.Category;
import org.johan.domain.quizzes.valueObjects.Difficulty;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class StartQuizUseCase {

    @Inject
    private ICustomerRepository customerRepository;

    @Inject
    private IQuizFactory quizFactory;

    @Inject
    private QuizExchange quizExchange;

    private OutputPort outputPort;

    public void setOutputPort(OutputPort outputPort) {
        this.outputPort = outputPort;
    }

    public void execute(StartQuizInput input) throws StartQuizBadRequestException, CustomerNotFoundException, ClientErrorException {
        if (input.getModelState().isValid()) {
            startQuizInternal(input.getCustomerId(), input.getCategory(), input.getDifficulty());
        } else {
            this.outputPort.invalid(input.getModelState());
        }
    }

    private void startQuizInternal(CustomerId customerId, Category category, Difficulty difficulty) throws CustomerNotFoundException, ClientErrorException {
        Customer existingCustomer = this.customerRepository.find(customerId);

        if (existingCustomer == null) {
            this.outputPort.notFound();
            return;
        }

        Quiz quiz = this.quizExchange.fetchQuiz(existingCustomer, category, difficulty);

        if (quiz == null) {
            this.outputPort.clientError();
        }
        Quiz savedQuiz = this.quizFactory.newQuiz(existingCustomer, quiz);
        existingCustomer.addQuiz(savedQuiz);

        this.outputPort.ok(savedQuiz);
    }
}
