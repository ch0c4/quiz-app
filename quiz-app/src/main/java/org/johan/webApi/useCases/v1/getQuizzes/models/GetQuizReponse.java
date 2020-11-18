package org.johan.webApi.useCases.v1.getQuizzes.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GetQuizReponse {

    private Long id;

    private String name;

    private Boolean completed;

    private Long timeFinished;
}
