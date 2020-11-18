package org.johan.webApi.useCases.v1.getQuizzes.models;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GetQuizzesResponse {

    private List<GetQuizReponse> quizzes;
}
