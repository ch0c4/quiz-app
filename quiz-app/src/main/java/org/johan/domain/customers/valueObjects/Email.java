package org.johan.domain.customers.valueObjects;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Objects;

public class Email {

    private final String text;

    public Email(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(text, email.text);
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(text);
        return builder.toHashCode();
    }

    @Override
    public String toString() {
        return text;
    }

}
