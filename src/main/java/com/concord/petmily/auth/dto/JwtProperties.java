package com.concord.petmily.auth.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class JwtProperties {

    private String issuer;
    @Value("${spring.jwt.secret-key}")
    private String secretKey;
}