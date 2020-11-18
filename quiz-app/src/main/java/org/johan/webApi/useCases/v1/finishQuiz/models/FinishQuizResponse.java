package org.johan.webApi.useCases.v1.finishQuiz.models;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FinishQuizResponse {

    private List<FinishQuizQuestionResponse> questions;

    private Integer totalCorrectAnswer;

    private Long timeFinish;

}
