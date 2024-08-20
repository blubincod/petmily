package com.concord.petmily.domain.auth.service;

import com.concord.petmily.domain.auth.entity.RefreshToken;
import com.concord.petmily.domain.auth.exception.AuthException;
import com.concord.petmily.domain.auth.repository.RefreshTokenRepository;
import com.concord.petmily.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 리프레시 토큰을 관리하는 서비스 클래스
 */
@RequiredArgsConstructor
@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new AuthException(ErrorCode.REFRESH_TOKEN_EXPIRED));
    }

    public void deleteByUsername(String username) {
        refreshTokenRepository.findByUsername(username)
                .ifPresent(refreshTokenRepository::delete);
    }

    public void save(RefreshToken refreshToken) {
        refreshTokenRepository.save(refreshToken);
    }
}
