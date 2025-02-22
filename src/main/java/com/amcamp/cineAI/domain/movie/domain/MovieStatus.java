package com.amcamp.cineAI.domain.movie.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MovieStatus {
    NORMAL("NORMAL"),
    CREATED("CREATED");
    private final String role;
}
