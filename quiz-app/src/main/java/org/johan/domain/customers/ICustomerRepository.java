package org.johan.domain.customers;

import org.johan.domain.common.CustomerId;
import org.johan.domain.customers.valueObjects.Email;

public interface ICustomerRepository {

    Customer find(CustomerId customerId);

    Customer findByEmail(Email email);

    QuizCollection getQuizzes(CustomerId customerId);

    void update(Customer customer);
}
