package com.concord.petmily.domain.chatmessage.controller;

import com.concord.petmily.domain.auth.dto.JwtProperties;
import com.concord.petmily.domain.chatmessage.dto.ChatMessageDto;
import com.concord.petmily.domain.chatmessage.entity.ChatMessage;
import com.concord.petmily.domain.chatmessage.service.ChatMessageService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {

    private final JwtProperties jwtProperties;
    private final ChatMessageService chatMessageService;
    private final SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/api/v1/chat.sendMessage")
    public void handleChat(@Payload ChatMessageDto chatMessageDTO,
                           @Header("Authorization") String token) {
        System.out.println("Received message: " + chatMessageDTO);

        // 토큰에서 사용자 ID 추출
        Long userId = getUserIdFromToken(token);

        // 메시지 저장 및 처리
        ChatMessage savedMessage = chatMessageService.saveMessage(
                chatMessageDTO.getContent(),
                userId,
                chatMessageDTO.getOpenChatId()
        );

        // 처리된 메시지를 모든 구독자에게 브로드캐스트
        messagingTemplate.convertAndSend("/topic/messages/" + savedMessage.getOpenChat().getId(), savedMessage);
    }

    // 토큰으로 부터 회원 ID 추출 메서드
    private Long getUserIdFromToken(String token) {
//        private Claims getClaimsFromToken(String token) {
//            // "Bearer " 제거
//            if (token.startsWith("Bearer ")) {
//                token = token.substring(7);
//            }
//
//            return Jwts.parser()
//                    .setSigningKey(jwtProperties.getSecretKey())
//                    .parseClaimsJws(token)
//                    .getBody();
//        }
        return null;
    }
}
