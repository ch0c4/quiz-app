package org.johan.infrastructure.quizExchange;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class QuizPackage {

    private int response_code;

    private List<QuizResult> results;
}

