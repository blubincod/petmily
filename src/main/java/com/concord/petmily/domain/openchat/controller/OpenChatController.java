package com.concord.petmily.domain.openchat.controller;

import com.concord.petmily.common.dto.ApiResponse;
import com.concord.petmily.common.dto.PagedApiResponse;
import com.concord.petmily.domain.openchat.dto.*;
import com.concord.petmily.domain.chatmessage.service.ChatMessageService;
import com.concord.petmily.domain.openchat.service.OpenChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/open-chats")
@RequiredArgsConstructor
public class OpenChatController {

    private final OpenChatService openChatService;
    private final ChatMessageService chatMessageService;

    /**
     * 채팅방 생성
     */
    @PostMapping
    public ResponseEntity<ApiResponse<OpenChatDto>> createChatRoom(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody CreateChatRoomDto createDto
    ) {
        String username = userDetails.getUsername();
        OpenChatDto chatRoom = openChatService.createChatRoom(username, createDto);

        return ResponseEntity.ok(ApiResponse.success(chatRoom));
    }

    /**
     * 채팅방 목록 조회
     * TODO 목록 정렬, 필터
     */
    @GetMapping
    public ResponseEntity<PagedApiResponse<List<OpenChatDto>>> getChatRoomList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        {
            Page<OpenChatDto> chatRooms = openChatService.getChatRoomList(pageable);

            return ResponseEntity.ok(PagedApiResponse.success(chatRooms));
        }
    }

    /**
     * 특정 채팅방 정보 조회
     */
    @GetMapping("/{chatId}")
    public ResponseEntity<ApiResponse<OpenChatDto>> getChatRoomInfo(
            @PathVariable Long chatId
    ) {
        OpenChatDto chatRoom = openChatService.getChatRoomInfo(chatId);

        return ResponseEntity.ok(ApiResponse.success(chatRoom));
    }

    /**
     * 채팅방 참여
     */
    @PostMapping("/{chatId}/join")
    public ResponseEntity<ApiResponse<Void>> joinChatRoom(
            @PathVariable Long chatId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String username = userDetails.getUsername();
        openChatService.joinChatRoom(chatId, username);

        return ResponseEntity.ok(ApiResponse.success(null));
    }

    /**
     * 채팅방 나가기
     */
    @PostMapping("/{chatId}/leave")
    public ResponseEntity<ApiResponse<Void>> leaveChatRoom(
            @PathVariable Long chatId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String username = userDetails.getUsername();
        openChatService.leaveChatRoom(chatId, username);

        return ResponseEntity.ok(ApiResponse.success(null));
    }

    /**
     * 메시지 목록 조회
     */
//    @GetMapping("/{chatId}/messages")
//    public ResponseEntity<ApiResponse<List<MessageDto>>> getMessages(
//            @PathVariable Long chatId
//    ) {
//        List<MessageDto> messages = chatMessageService.getChatMessages(chatId);
//
//        return ResponseEntity.ok(ApiResponse.success(messages));
//    }

    /**
     * 채팅방 검색
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<OpenChatDto>>> searchChatRooms(
            @RequestParam String keyword
    ) {
        List<OpenChatDto> chatRooms = openChatService.searchChatRooms(keyword);

        return ResponseEntity.ok(ApiResponse.success(chatRooms));
    }

    /**
     * 채팅방 정보 수정 (방장 전용)
     */
    @PutMapping("/{chatId}")
    public ResponseEntity<ApiResponse<OpenChatDto>> updateChatRoom(
            @PathVariable Long chatId, @RequestBody UpdateChatRoomDto updateDto
    ) {
        OpenChatDto updatedChatRoom = openChatService.updateChatRoom(chatId, updateDto);

        return ResponseEntity.ok(ApiResponse.success(updatedChatRoom));
    }

    /**
     * 채팅방 삭제 (방장 전용)
     */
    @DeleteMapping("/{chatId}")
    public ResponseEntity<ApiResponse<Void>> deleteChatRoom(
            @PathVariable Long chatId
    ) {
        openChatService.deleteChatRoom(chatId);

        return ResponseEntity.ok(ApiResponse.success(null));
    }
}