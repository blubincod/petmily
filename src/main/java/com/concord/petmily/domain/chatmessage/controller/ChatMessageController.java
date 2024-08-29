package com.concord.petmily.domain.chatmessage.controller;

import com.concord.petmily.domain.auth.service.TokenProvider;
import com.concord.petmily.domain.chatmessage.dto.ChatMessageDto;
import com.concord.petmily.domain.chatmessage.entity.ChatMessage;
import com.concord.petmily.domain.chatmessage.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {

    private final TokenProvider tokenProvider;
    private final ChatMessageService chatMessageService;
    private final SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/api/v1/chat.sendMessage")
    public void handleChat(@Payload ChatMessageDto chatMessageDTO,
                           @Header("Authorization") String token) {

        System.out.println("TOKEN " + token);

        // 토큰에서 사용자 ID 추출
        String username = tokenProvider.getUsername(token);

        System.out.println("USERNAME " + username);

        // 메시지 저장 및 처리
        ChatMessage savedMessage = chatMessageService.saveMessage(
                chatMessageDTO.getContent(),
                username,
                chatMessageDTO.getOpenChatId()
        );

        // 처리된 메시지를 모든 구독자에게 브로드캐스트
        messagingTemplate.convertAndSend("/topic/messages/" + savedMessage.getOpenChat().getId(), savedMessage);
    }
}