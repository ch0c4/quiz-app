package org.johan.application.useCases.getQuizzes;

import org.johan.application.services.Notification;
import org.johan.domain.common.CustomerId;

public class GetQuizzesInput {

    private final Notification modelState;

    private CustomerId customerId;

    public GetQuizzesInput(Long customerId) {
        modelState = new Notification();

        if(customerId != null) {
            this.customerId = new CustomerId(customerId);
        } else {
            modelState.add("CustomerId", "CustomerId is required");
        }
    }

    public Notification getModelState() {
        return modelState;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }
}
