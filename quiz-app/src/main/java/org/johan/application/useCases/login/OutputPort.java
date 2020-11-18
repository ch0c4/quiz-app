package org.johan.application.useCases.login;

import org.johan.application.services.Notification;
import org.johan.application.exceptions.customer.CustomerNotFoundException;
import org.johan.application.exceptions.login.LoginBadRequestException;
import org.johan.application.exceptions.login.PasswordInvalidException;
import org.johan.domain.customers.Customer;

public interface OutputPort {

    void invalid(Notification notification) throws LoginBadRequestException;

    void notFound() throws CustomerNotFoundException;

    void passwordInvalid() throws PasswordInvalidException;

    void ok(Customer customer);
}
