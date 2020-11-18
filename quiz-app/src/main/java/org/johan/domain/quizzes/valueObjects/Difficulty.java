package org.johan.domain.quizzes.valueObjects;

import java.util.Objects;

public enum Difficulty {

    EASY("easy"), MEDIUM("medium"), HARD("hard");

    private final String text;

    Difficulty(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
}
