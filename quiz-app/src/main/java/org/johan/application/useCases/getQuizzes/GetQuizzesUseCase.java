package org.johan.application.useCases.getQuizzes;

import org.johan.application.exceptions.customer.CustomerNotFoundException;
import org.johan.application.exceptions.getQuizzes.GetQuizzesBadRequestException;
import org.johan.application.exceptions.getQuizzes.GetQuizzesNoContentException;
import org.johan.domain.common.CustomerId;
import org.johan.domain.customers.Customer;
import org.johan.domain.customers.ICustomerRepository;
import org.johan.domain.customers.QuizCollection;
import org.johan.domain.quizzes.IQuizRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GetQuizzesUseCase {

    @Inject
    private ICustomerRepository customerRepository;

    private OutputPort outputPort;

    public void setOutputPort(OutputPort outputPort) {
        this.outputPort = outputPort;
    }

    public void execute(GetQuizzesInput input) throws GetQuizzesBadRequestException, CustomerNotFoundException, GetQuizzesNoContentException {
        if (input.getModelState().isValid()) {
            getQuizzesInternal(input.getCustomerId());
            return;
        }
        this.outputPort.invalid(input.getModelState());
    }

    private void getQuizzesInternal(CustomerId customerId) throws GetQuizzesNoContentException, CustomerNotFoundException {
        Customer customer = customerRepository.find(customerId);
        if (customer == null) {
            this.outputPort.notFound();
            return;
        }

        QuizCollection quizzes = customerRepository.getQuizzes(customerId);
        if (quizzes == null) {
            this.outputPort.noContent();
            return;
        }

        if(quizzes.getQuizzes().size() == 0) {
            this.outputPort.noContent();
        }

        this.outputPort.ok(quizzes);
    }
}
