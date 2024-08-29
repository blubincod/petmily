package com.concord.petmily.common.config;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class WebSocketAuthInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String token = accessor.getFirstNativeHeader("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
                // 여기서 토큰을 검증하고 인증 객체를 생성
                // 예를 들어, JWT를 사용한다면 JWT를 파싱하고 검증
                UsernamePasswordAuthenticationToken auth = validateToken(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
                accessor.setUser(auth);
            }
        }
        return message;
    }

    private UsernamePasswordAuthenticationToken validateToken(String token) {
        // 토큰 검증 로직 구현
        // 실제 구현에서는 JWT 파싱, 서명 확인 등을 수행
        // 여기서는 간단한 예시만 제공
        return new UsernamePasswordAuthenticationToken("user", null, new ArrayList<>());
    }
}