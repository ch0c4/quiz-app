package org.johan.application.useCases.login;

import org.johan.domain.customers.Customer;
import org.johan.domain.customers.ICustomerRepository;
import org.johan.domain.customers.valueObjects.Email;
import org.johan.domain.customers.valueObjects.Password;
import org.johan.application.exceptions.customer.CustomerNotFoundException;
import org.johan.application.exceptions.login.LoginBadRequestException;
import org.johan.application.exceptions.login.PasswordInvalidException;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.security.NoSuchAlgorithmException;

@Singleton
public class LoginUseCase {

    @Inject
    private ICustomerRepository customerRepository;

    private OutputPort outputPort;

    public void setOutputPort(OutputPort outputPort) {
        this.outputPort = outputPort;
    }

    public void execute(LoginInput input) throws NoSuchAlgorithmException, LoginBadRequestException, CustomerNotFoundException, PasswordInvalidException {
        if(input.getModelState().isValid()) {
            loginInternal(input.getEmail(), input.getPassword());
        } else {
            this.outputPort.invalid(input.getModelState());
        }
    }

    private void loginInternal(Email email, Password password) throws NoSuchAlgorithmException, CustomerNotFoundException, PasswordInvalidException {
        Customer existingCustomer = this.customerRepository.findByEmail(email);

        if(existingCustomer == null) {
            this.outputPort.notFound();
            return;
        }

        if(!existingCustomer.getPassword().getText().equals(password.getEncodedText())) {
            this.outputPort.passwordInvalid();
        } else {
            this.outputPort.ok(existingCustomer);
        }
    }
}
