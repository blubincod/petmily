package com.concord.petmily.domain.auth.service;

import com.concord.petmily.domain.auth.dto.JwtProperties;
import com.concord.petmily.domain.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

/**
 * JWT 및 리프레시 토큰을 관리하는 서비스 클래스
 * 리프레시 토큰을 사용하여 새로운 액세스 토큰을 생성
 */
@Component
@RequiredArgsConstructor
public class TokenProvider {

    private final JwtProperties jwtProperties;

    /**
     * JWT 토큰을 생성
     * @param user 사용자 정보
     * @param expiredAt 토큰 만료 시간
     * @return 생성된 JWT 토큰
     */
    public String generateToken(User user, Duration expiredAt) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiredAt.toMillis());

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .setSubject(user.getUsername()) // username을 subject로 설정
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    /**
     * 주어진 JWT 토큰의 유효성을 검사
     * @param token 검사할 토큰
     * @return 유효한 경우 true, 그렇지 않으면 false
     */
    public boolean validToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey())
                    .parseClaimsJws(token);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * JWT 토큰에서 인증 정보 추출
     * JWT 클레임에서 Authentication 객체를 생성하여 Spring Security가 사용자를 인증하는 데 사용
     * @param token JWT 토큰
     * @return 추출된 인증 정보
     */
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(
                new org.springframework.security.core.userdetails.User(claims.getSubject(), "", authorities),
                token,
                authorities
        );
    }

    /**
     * JWT 토큰에서 username 추출
     * @param token JWT 토큰
     * @return 추출된 username
     */
    public String getUsername(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    /**
     * JWT 토큰에서 클레임 추출
     * 다른 메서드에서 JWT를 파싱하고 클레임을 추출
     * @param token JWT 토큰
     * @return 추출된 클레임
     */
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }
}
