package org.johan.application.useCases.signUp;

import org.johan.application.services.Notification;
import org.johan.domain.customers.Customer;
import org.johan.application.exceptions.customer.CustomerAlreadyExistException;
import org.johan.application.exceptions.signUp.SignUpBadRequestException;

public interface OutputPort {

    void invalid(Notification notification) throws SignUpBadRequestException;

    void alreadyExist() throws CustomerAlreadyExistException;

    void ok(Customer customer);
}
