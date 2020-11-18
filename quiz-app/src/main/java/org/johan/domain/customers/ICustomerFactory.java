package org.johan.domain.customers;

import org.johan.domain.customers.valueObjects.Email;
import org.johan.domain.customers.valueObjects.Password;

import java.security.NoSuchAlgorithmException;

public interface ICustomerFactory {

    Customer newCustomer(Email email, Password password) throws NoSuchAlgorithmException;
}
