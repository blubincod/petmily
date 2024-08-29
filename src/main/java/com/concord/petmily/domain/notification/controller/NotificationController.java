package com.concord.petmily.domain.notification.controller;

import com.concord.petmily.common.dto.ApiResponse;
import com.concord.petmily.domain.notification.dto.NotificationDto;
import com.concord.petmily.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * 알림 관련 컨트롤러
 *
 * - SSE 구독 연결
 * - 생일 알림
 * - 모든 사용자에게 공지
 * - 특정 사용자에게 메시지
 * - 산책 알림
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notification")
public class NotificationController {

    private final NotificationService notificationService;

    /**
     * SSE 구독 연결
     * @param userDetails 현재 인증된 사용자의 세부 정보
     * @param lastEventId 미수신 이벤트 확인을 위한 정보
     */
    // Get 요청이 오면 아래 메서드가 호출되어 클라이언트에게 SseEmitter를 반환하여 SSE 스트림 통신 유지
    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    public ResponseEntity<ApiResponse<SseEmitter>> subscribe(@AuthenticationPrincipal UserDetails userDetails,
                                                            @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
        String username = userDetails.getUsername();
        SseEmitter sseEmitter = notificationService.subscribe(username, lastEventId);

        return ResponseEntity.ok(ApiResponse.success(sseEmitter));
    }

    /**
     * 생일인 사용자에게 알림 전송
     * 생일인 반려동물의 사용자에게 알림 전송
     */
    @Scheduled(cron = "0 0 12 * * ?") // 매일 정오 실행
    public ResponseEntity<ApiResponse<Void>> sendBirthDateNotification() {
        notificationService.sendBirthDateNotification();

        return ResponseEntity.ok(ApiResponse.success(null));
    }

    /**
     * 관리자가 모든 사용자에게 공지사항을 보냄
     * @param content 공지사항 내용
     * @param userDetails 현재 인증된 사용자의 세부 정보
     */
    @PostMapping("/announcement")
    public ResponseEntity<ApiResponse<Void>> sendAnnouncementToAllUsers(@RequestBody String content,
                                                             @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        notificationService.sendAnnouncementToAllUsers(username, content);

        return ResponseEntity.ok(ApiResponse.success(null));
    }

    /**
     * 관리자가 특정 사용자에게 메시지를 보냄
     * @param dto 메시지 수신자, 내용
     * @param userDetails 현재 인증된 사용자의 세부 정보
     */
    @PostMapping("/message")
    public ResponseEntity<ApiResponse<Void>> sendMessageToUser(@RequestBody NotificationDto.sendToUserDto dto,
                                                    @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        notificationService.sendMessageToUser(username, dto);

        return ResponseEntity.ok(ApiResponse.success(null));
    }

    /**
     * 산책 목표 달성/미달성 알림
     * 산책 미리 알림
     * 1시간마다 check
     */
    @Scheduled(cron = "0 0 * * * *")
    public ResponseEntity<ApiResponse<Void>> sendWalkNotification() {
        notificationService.sendWalkNotification();

        return ResponseEntity.ok(ApiResponse.success(null));
    }

}
