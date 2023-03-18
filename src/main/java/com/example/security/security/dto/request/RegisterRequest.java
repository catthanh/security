package com.example.security.security.dto.request;

import com.example.security.user.model.RoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors
@NoArgsConstructor
public class RegisterRequest extends LoginRequest {
    List<RoleEnum> roles;
}
