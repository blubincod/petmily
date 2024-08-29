package com.concord.petmily.domain.auth.security;

import com.concord.petmily.domain.auth.service.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 클라이언트 요청 시 JWT 토큰을 처리하는 필터
 * - 클라이언트 요청이 들어올 때마다 실행
 * - 요청에 포함된 JWT를 검증하고, 인증 정보를 설정
 */
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;

    // HTTP 요청 헤더에서 사용할 인증 헤더 이름
    private final static String HEADER_AUTHORIZATION = "Authorization";
    // Bearer 토큰임을 나타내는 접두사
    private final static String TOKEN_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        try {
            // HTTP 요청에서 JWT 토큰 추출
            String token = getAccessToken(request);
            log.debug("Extracted token: {}", token);

            // 토큰이 존재하거나 유효한 경우 인증 처리
            if (token != null && tokenProvider.validToken(token)) {
                Authentication authentication = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug("Set Authentication to security context for '{}', uri: {}", authentication.getName(), request.getRequestURI());
            } else {
                log.debug("No valid JWT token found, uri: {}", request.getRequestURI());
            }
        } catch (Exception ex) {
            log.error("Could not set user authentication in security context", ex);
        }

        // 필터로 요청 전달
        filterChain.doFilter(request, response);
    }

    /**
     * 요청 헤더에서 액세스 토큰 추출
     *
     * @param request HTTP 요청 객체
     * @return 추출된 JWT 토큰, 없으면 null
     */
    private String getAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HEADER_AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}