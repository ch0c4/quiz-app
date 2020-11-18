package org.johan.application.useCases.signUp;

import org.johan.domain.customers.Customer;
import org.johan.domain.customers.ICustomerFactory;
import org.johan.domain.customers.ICustomerRepository;
import org.johan.domain.customers.valueObjects.Email;
import org.johan.domain.customers.valueObjects.Password;
import org.johan.application.exceptions.customer.CustomerAlreadyExistException;
import org.johan.application.exceptions.signUp.SignUpBadRequestException;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.security.NoSuchAlgorithmException;

@Singleton
public class SignUpUseCase {

    @Inject
    private ICustomerRepository customerRepository;

    @Inject
    private ICustomerFactory customerFactory;

    private OutputPort outputPort;

    public void setOutputPort(OutputPort outputPort) {
        this.outputPort = outputPort;
    }

    public void execute(SignUpInput input) throws SignUpBadRequestException, CustomerAlreadyExistException, NoSuchAlgorithmException {
        if(input.getModelState().isValid()) {
            signUpInternal(input.getEmail(), input.getPassword());
        } else {
            this.outputPort.invalid(input.getModelState());
        }
    }

    private void signUpInternal(Email email, Password password) throws CustomerAlreadyExistException, NoSuchAlgorithmException {
        Customer existingCustomer = this.customerRepository.findByEmail(email);

        if(existingCustomer != null) {
            this.outputPort.alreadyExist();
            return;
        }
        Customer newCustomer = this.customerFactory.newCustomer(email, password);
        this.outputPort.ok(newCustomer);
    }

}
