package com.concord.petmily.auth.controller;

import com.concord.petmily.auth.dto.CreateAccessTokenRequest;
import com.concord.petmily.auth.dto.CreateAccessTokenResponse;
import com.concord.petmily.auth.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TokenApiController {

    private final TokenService tokenService;

    /**
     * 새로운 액세스 토큰을 생성합니다.
     * @param request 리프레시 토큰이 포함된 요청
     * @return 생성된 액세스 토큰
     */
    @PostMapping("/api/token")
    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken(@RequestBody CreateAccessTokenRequest request) {
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateAccessTokenResponse(newAccessToken));
    }
}