package org.johan.webApi.useCases.v1.signUp;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Error;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Status;
import org.johan.application.services.Notification;
import org.johan.application.useCases.signUp.OutputPort;
import org.johan.application.useCases.signUp.SignUpInput;
import org.johan.application.useCases.signUp.SignUpUseCase;
import org.johan.domain.customers.Customer;
import org.johan.application.exceptions.customer.CustomerAlreadyExistException;
import org.johan.application.exceptions.signUp.SignUpBadRequestException;
import org.johan.webApi.useCases.v1.signUp.models.SignUpRequest;
import org.johan.webApi.useCases.v1.signUp.models.SignUpResponse;

import javax.inject.Inject;
import java.security.NoSuchAlgorithmException;

@Controller("/v1/customers")
public class SignUpController implements OutputPort {

    @Inject
    private SignUpUseCase useCase;

    private SignUpResponse signUpResponse;

    @Post(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    @Status(HttpStatus.CREATED)
    @Error
    public SignUpResponse signUp(@Body SignUpRequest request) throws SignUpBadRequestException, CustomerAlreadyExistException, NoSuchAlgorithmException {
        useCase.setOutputPort(this);

        useCase.execute(new SignUpInput(request.getEmail(), request.getPassword()));

        return signUpResponse;
    }

    @Override
    public void invalid(Notification notification) throws SignUpBadRequestException {
        throw new SignUpBadRequestException(notification.getErrorMessages());
    }

    @Override
    public void alreadyExist() throws CustomerAlreadyExistException {
        throw new CustomerAlreadyExistException();
    }

    @Override
    public void ok(Customer customer) {
        this.signUpResponse = SignUpResponse.builder()
                .id(customer.getCustomerId().getId())
                .email(customer.getEmail().getText())
                .build();
    }
}
