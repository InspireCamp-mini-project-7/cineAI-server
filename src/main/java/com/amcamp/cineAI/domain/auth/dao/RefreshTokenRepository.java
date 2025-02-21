package com.amcamp.cineAI.domain.auth.dao;

import com.amcamp.cineAI.domain.auth.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
    RefreshToken findByToken(String token);
}
