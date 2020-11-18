package org.johan.webApi.useCases.v1.startQuiz.models;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StartQuizResponse {
    
    private Long quizId;

    private Long questionId;

    private String question;

    private List<String> answers;
}

