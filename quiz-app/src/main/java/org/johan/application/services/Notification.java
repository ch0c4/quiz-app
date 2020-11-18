package org.johan.application.services;

import java.util.*;

public class Notification {

    private final Map<String, List<String>> errorMessages;

    public Notification() {
        this.errorMessages = new HashMap<>();
    }

    public Map<String, List<String>> getErrorMessages() {
        return errorMessages;
    }

    public boolean isValid() {
        return this.errorMessages.size() == 0;
    }

    public void add(String key, String message) {
        if(!this.errorMessages.containsKey(key)) {
            List<String> messages = new ArrayList<>();
            messages.add(message);
            this.errorMessages.put(key, messages);
        } else {
            this.errorMessages.get(key).add(message);
        }
    }
}
