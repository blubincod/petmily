package com.concord.petmily.domain.auth.controller;

import com.concord.petmily.domain.auth.dto.JwtAuthenticationResponse;
import com.concord.petmily.domain.auth.dto.LoginRequest;
import com.concord.petmily.domain.auth.entity.RefreshToken;
import com.concord.petmily.domain.auth.service.RefreshTokenService;
import com.concord.petmily.domain.auth.service.TokenProvider;
import com.concord.petmily.domain.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;

    /**
     * 로그인
     *
     * @param loginRequest
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = (User) authentication.getPrincipal();
            String accessToken = tokenProvider.generateToken(user, Duration.ofHours(1));
            String refreshToken = tokenProvider.generateToken(user, Duration.ofDays(1));

            RefreshToken token = new RefreshToken(user.getUsername(), refreshToken);
            refreshTokenService.save(token);

            return ResponseEntity.ok(new JwtAuthenticationResponse(accessToken, refreshToken));
        } catch (AuthenticationException e) {
            // 인증 실패 시의 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 혹은 비밀번호가 유효하지 않습니다.");
        }
    }

    /**
     * 사용자를 로그아웃하고 리프레시 토큰을 삭제합니다.
     *
     * @param request HTTP 요청 정보
     * @return 로그아웃 성공 여부 메시지
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpServletRequest request) {
        String refreshToken = getRefreshToken(request.getHeader("Authorization"));
        if (refreshToken != null && tokenProvider.validToken(refreshToken)) {
            String username = tokenProvider.getUsername(refreshToken);
            refreshTokenService.deleteByUsername(username); // 리프레시 토큰 삭제
            SecurityContextHolder.clearContext(); // 세션 무효화
            return ResponseEntity.ok("로그아웃 성공");
        }
        return ResponseEntity.badRequest().body("존재하지 않는 토큰");
    }

    private String getRefreshToken(String authorizationHeader) {
        final String TOKEN_PREFIX = "Bearer ";
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}