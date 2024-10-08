package com.concord.petmily.domain.auth.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class JwtProperties {
    @Value("${spring.jwt.issuer}")
    private String issuer;
    @Value("${spring.jwt.secret}")
    private String secretKey;
}