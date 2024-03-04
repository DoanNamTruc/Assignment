package com.trucdn.user.repositories;


import com.trucdn.user.helpers.RefreshableCRUDRepository;
import com.trucdn.user.models.RefreshToken;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends RefreshableCRUDRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
}
