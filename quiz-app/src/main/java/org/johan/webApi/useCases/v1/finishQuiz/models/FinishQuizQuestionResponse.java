package org.johan.webApi.useCases.v1.finishQuiz.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FinishQuizQuestionResponse {

    private String question;

    private String correctAnswers;

    private Boolean isCorrect;

}
