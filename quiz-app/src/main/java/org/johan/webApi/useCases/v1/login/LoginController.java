package org.johan.webApi.useCases.v1.login;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Error;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Status;
import org.johan.application.services.Notification;
import org.johan.application.useCases.login.LoginInput;
import org.johan.application.useCases.login.LoginUseCase;
import org.johan.application.useCases.login.OutputPort;
import org.johan.application.exceptions.customer.CustomerNotFoundException;
import org.johan.application.exceptions.login.LoginBadRequestException;
import org.johan.application.exceptions.login.PasswordInvalidException;
import org.johan.domain.customers.Customer;
import org.johan.webApi.useCases.v1.login.models.LoginRequest;
import org.johan.webApi.useCases.v1.login.models.LoginResponse;

import javax.inject.Inject;
import java.security.NoSuchAlgorithmException;

@Controller("/v1/customers")
public class LoginController implements OutputPort {

    @Inject
    private LoginUseCase useCase;

    private LoginResponse loginResponse;

    @Post(uri = "/login", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    @Status(HttpStatus.OK)
    @Error
    public LoginResponse login(@Body LoginRequest request) throws NoSuchAlgorithmException, PasswordInvalidException, LoginBadRequestException, CustomerNotFoundException {
        useCase.setOutputPort(this);

        useCase.execute(new LoginInput(request.getEmail(), request.getPassword()));

        return this.loginResponse;
    }

    @Override
    public void invalid(Notification notification) throws LoginBadRequestException {
        throw new LoginBadRequestException(notification.getErrorMessages());
    }

    @Override
    public void notFound() throws CustomerNotFoundException {
        throw new CustomerNotFoundException();
    }

    @Override
    public void passwordInvalid() throws PasswordInvalidException {
        throw new PasswordInvalidException();
    }

    @Override
    public void ok(Customer customer) {
        this.loginResponse = LoginResponse.builder()
                .id(customer.getCustomerId().getId())
                .email(customer.getEmail().getText())
                .build();
    }
}
