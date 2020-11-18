package org.johan.application.useCases.startQuiz;

import org.apache.commons.lang3.EnumUtils;
import org.johan.application.services.Notification;
import org.johan.domain.common.CustomerId;
import org.johan.domain.quizzes.valueObjects.Category;
import org.johan.domain.quizzes.valueObjects.Difficulty;

public class StartQuizInput {

    private final Notification modelState;

    private CustomerId customerId;

    private Category category;

    private Difficulty difficulty;

    public StartQuizInput(Long customerId, String category, String difficulty) {
        modelState = new Notification();

        if (customerId != null) {
            this.customerId = new CustomerId(customerId);
        } else {
            modelState.add("CustomerId", "CustomerId is required");
        }

        if (category != null && !category.trim().isEmpty() && EnumUtils.isValidEnum(Category.class, category)) {
            this.category = Category.valueOf(category);
        } else {
            modelState.add("Category", "Valid Category is required");
        }

        if (difficulty != null && !difficulty.trim().isEmpty() && EnumUtils.isValidEnum(Difficulty.class, difficulty)) {
            this.difficulty = Difficulty.valueOf(difficulty.toUpperCase());
        } else {
            modelState.add("Difficulty", "Valid Difficulty is required");
        }
    }

    public Notification getModelState() {
        return modelState;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public Category getCategory() {
        return category;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }
}
