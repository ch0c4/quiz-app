package org.johan.webApi.useCases.v1.signUp.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SignUpResponse {

    private Long id;

    private String email;
}
