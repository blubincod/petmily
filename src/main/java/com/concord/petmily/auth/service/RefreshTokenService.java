package com.concord.petmily.auth.service;

import com.concord.petmily.auth.entity.RefreshToken;
import com.concord.petmily.auth.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 토큰"));
    }

    public void deleteByUsername(String username) {
        refreshTokenRepository.findByUsername(username)
                .ifPresent(refreshTokenRepository::delete);
    }

    public void save(RefreshToken refreshToken) {
        refreshTokenRepository.save(refreshToken);
    }
}
