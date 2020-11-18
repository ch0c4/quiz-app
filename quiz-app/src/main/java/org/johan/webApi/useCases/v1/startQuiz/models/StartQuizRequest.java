package org.johan.webApi.useCases.v1.startQuiz.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StartQuizRequest {

    private String category;

    private String difficulty;
}
