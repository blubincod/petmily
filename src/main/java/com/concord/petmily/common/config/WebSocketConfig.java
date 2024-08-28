package com.concord.petmily.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocket 설정 클래스
 * 실시간 양방향 통신을 위한 WebSocket 및 STOMP 프로토콜 설정
 *
 * WebSocketMessageBrokerConfigurer
 * - STOMP를 사용하여 WebSocket 메시지 브로커를 구성하는 데 사용되는 Spring Framework의 인터페이스
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 메시지 브로커 설정
     * 클라이언트와 서버 간의 메시지 라우팅
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    /**
     * 메시지 교환을 더 체계적이고 효율적으로 하기 위한 STOMP 엔드포인트 등록
     * 클라이언트가 웹소켓 서버에 연결하는 데 사용할 엔드포인트를 등록
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS();
    }
}
