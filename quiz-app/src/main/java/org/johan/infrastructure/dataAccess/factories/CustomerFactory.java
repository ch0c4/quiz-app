package org.johan.infrastructure.dataAccess.factories;

import org.johan.domain.common.CustomerId;
import org.johan.domain.customers.Customer;
import org.johan.domain.customers.ICustomerFactory;
import org.johan.domain.customers.valueObjects.Email;
import org.johan.domain.customers.valueObjects.Password;
import org.johan.infrastructure.dataAccess.entities.CustomerEntity;
import org.johan.infrastructure.dataAccess.hibernate.CustomerHibernateRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;

@Singleton
public class CustomerFactory implements ICustomerFactory {

    @Inject
    private CustomerHibernateRepository customerRepository;

    @Override
    public Customer newCustomer(Email email, Password password) throws NoSuchAlgorithmException {
        CustomerEntity customerEntity = CustomerEntity.builder()
                .id(null)
                .email(email.getText())
                .password(password.getEncodedText())
                .quizzes(new HashSet<>())
                .build();
        CustomerEntity savedEntity = this.customerRepository.save(customerEntity);

        return mapCustomer(savedEntity);
    }

    private static Customer mapCustomer(CustomerEntity customerEntity) {
        return new Customer(
                new CustomerId(customerEntity.getId()),
                new Email(customerEntity.getEmail()),
                new Password(customerEntity.getPassword()));
    }
}
