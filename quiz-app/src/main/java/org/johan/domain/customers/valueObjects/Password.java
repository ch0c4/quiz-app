package org.johan.domain.customers.valueObjects;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class Password {

    private final String text;

    public Password(String text) {
        this.text = text;
    }

    public String getEncodedText() throws NoSuchAlgorithmException {
        return encodeText(this.text);
    }

    public String getText() {
        return text;
    }

    private String encodeText(String text) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(text.getBytes());
        byte[] bytes = md.digest();

        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password = (Password) o;
        return Objects.equals(text, password.text);
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
