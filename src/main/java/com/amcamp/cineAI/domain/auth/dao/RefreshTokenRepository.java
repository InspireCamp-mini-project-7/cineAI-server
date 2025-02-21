package com.amcamp.cineAI.domain.auth.dao;

import com.amcamp.cineAI.domain.auth.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken findByToken(String token);

    <Optional> RefreshToken findByMemberId(Long memberId);
}
