package org.johan.infrastructure.quizExchange;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class QuizResult {

    private String category;

    private String type;

    private String difficulty;

    private String question;

    private String correct_answer;

    private List<String> incorrect_answers;
}

