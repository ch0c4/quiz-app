package org.johan.webApi.useCases.v1.answeredQuestion.models;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AnsweredQuestionResponse {

    private Long id;

    private String question;

    private List<String> answers;

    private boolean completed;
}
