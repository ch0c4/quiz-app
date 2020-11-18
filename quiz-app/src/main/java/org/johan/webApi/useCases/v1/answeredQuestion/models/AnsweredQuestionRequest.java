package org.johan.webApi.useCases.v1.answeredQuestion.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AnsweredQuestionRequest {

    private String answer;

    private Long answerTime;
}
