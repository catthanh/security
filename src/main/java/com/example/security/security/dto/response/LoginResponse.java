package com.example.security.security.dto.response;

import com.example.security.user.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse {
    Integer id;
    String username;
    String accessToken;
    String refreshToken;


    public static LoginResponse of(User user, String accessToken, String refreshToken) {
        return new LoginResponse()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setAccessToken(accessToken)
                .setRefreshToken(refreshToken);
    }
}
