package org.johan.domain.quizzes.valueObjects;

import java.util.Objects;

public class QuestionId {

    private final Long id;

    public QuestionId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionId questionId = (QuestionId) o;
        return Objects.equals(id, questionId.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
