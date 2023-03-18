package com.example.security.security.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
@Entity
public class RefreshToken {
    @Id
    String token;

    @Column
    Boolean active;

    @Column
    UUID family;
}
