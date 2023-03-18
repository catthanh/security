package com.example.security.security.dto.response;

import com.example.security.user.model.RoleEnum;
import com.example.security.user.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterResponse {
    Integer id;
    String username;
    List<RoleEnum> roles;


    public static RegisterResponse of(User user) {
        return new RegisterResponse()
                .setId(user.getId())
                .setUsername(user.getUsername());
    }
}
