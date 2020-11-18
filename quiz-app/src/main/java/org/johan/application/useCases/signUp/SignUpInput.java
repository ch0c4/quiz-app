package org.johan.application.useCases.signUp;

import org.johan.application.services.Notification;
import org.johan.domain.customers.valueObjects.Email;
import org.johan.domain.customers.valueObjects.Password;

public class SignUpInput {

    private final Notification modelState;

    private Email email;

    private Password password;

    public SignUpInput(String email, String password) {
        modelState = new Notification();

        if(email != null && !email.trim().isEmpty()) {
            this.email = new Email(email);
        } else {
            modelState.add("Email", "Email is required");
        }

        if(password != null && !password.trim().isEmpty()) {
            this.password = new Password(password);
        } else {
            modelState.add("Password", "Password is required");
        }
    }

    public Notification getModelState() {
        return modelState;
    }

    public Email getEmail() {
        return email;
    }

    public Password getPassword() {
        return password;
    }
}
