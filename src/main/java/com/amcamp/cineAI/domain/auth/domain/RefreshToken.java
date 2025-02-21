package com.amcamp.cineAI.domain.auth.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long id;

    private Long memberId;
    private String token;

    @Builder(access = AccessLevel.PRIVATE)
    private RefreshToken(Long memberId, String token) {
        this.memberId = memberId;
        this.token = token;
    }

    public static RefreshToken createRefreshToken(Long memberId, String token) {
        return RefreshToken.builder().memberId(memberId).token(token).build();
    }
}
