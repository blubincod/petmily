package com.concord.petmily.domain.notification.controller;

import com.concord.petmily.domain.notification.dto.NotificationDto;
import com.concord.petmily.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notification")
public class NotificationController {

    private final NotificationService notificationService;

    // Get 요청이 오면 아래 메서드가 호출되어 클라이언트에게 SseEmitter를 반환하여 SSE 스트림 통신 유지
    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    public ResponseEntity<SseEmitter> subscribe(@AuthenticationPrincipal UserDetails userDetails,
                                @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
        String username = userDetails.getUsername();
        SseEmitter sseEmitter = notificationService.subscribe(username, lastEventId);

        return ResponseEntity.ok(sseEmitter);
    }

    /**
     * 생일인 사용자에게 알림 전송
     * 생일인 반려동물의 사용자에게 알림 전송
     */
    @Scheduled(cron = "0 0 12 * * ?") // 매일 정오 실행
    public ResponseEntity<Void> sendAnnouncementToAllUsers() {
        notificationService.sendBirthDateNotification();

        return ResponseEntity.noContent().build();
    }

    /**
     * 관리자가 모든 사용자에게 공지사항을 보냄
     */
    @PostMapping("/announcement")
    public ResponseEntity<String> sendAnnouncementToAllUsers(@RequestBody String content,
                                                             @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        notificationService.sendAnnouncementToAllUsers(username, content);

        return ResponseEntity.status(HttpStatus.OK).body("성공");
    }

    /**
     * 관리자가 특정 사용자에게 메시지를 보냄
     */
    @PostMapping("/message")
    public ResponseEntity<String> sendMessageToUser(@RequestBody NotificationDto.sendToUserDto dto,
                                                    @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        notificationService.sendMessageToUser(username, dto);

        return ResponseEntity.status(HttpStatus.OK).body("성공");
    }
}
