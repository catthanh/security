package com.example.security.security.dto.request;

import lombok.Data;
import lombok.Getter;

@Data
public class RefreshRequest {
    String refreshToken;
}
