package com.amcamp.cineAI.domain.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoginStatus {
    FIRST("FIRST"),
    NOTFIRST("NOTFIRST");

    private final String loginStatus;
}
