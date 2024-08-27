package com.concord.petmily.domain.notification.controller;

import com.concord.petmily.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
