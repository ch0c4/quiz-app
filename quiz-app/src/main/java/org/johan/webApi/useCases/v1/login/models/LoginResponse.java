package org.johan.webApi.useCases.v1.login.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LoginResponse {

    private Long id;

    private String email;
}
