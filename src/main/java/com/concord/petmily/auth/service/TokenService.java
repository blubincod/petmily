package com.concord.petmily.auth.service;

import com.concord.petmily.auth.exception.AuthException;
import com.concord.petmily.common.exception.ErrorCode;
import com.concord.petmily.user.entity.User;
import com.concord.petmily.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    public String createNewAccessToken(String refreshToken) {
        if (!tokenProvider.validToken(refreshToken)) {
            throw new AuthException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }

        String username = refreshTokenService.findByRefreshToken(refreshToken).getUsername();
        User user = userService.findByUsername(username);

        return tokenProvider.generateToken(user, Duration.ofHours(2));
    }
}