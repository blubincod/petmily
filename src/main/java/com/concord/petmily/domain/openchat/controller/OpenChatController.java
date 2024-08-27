package com.concord.petmily.domain.openchat.controller;

import com.concord.petmily.common.dto.ApiResponse;
import com.concord.petmily.domain.openchat.dto.*;
import com.concord.petmily.domain.openchat.service.MessageService;
import com.concord.petmily.domain.openchat.service.OpenChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/open-chats")
@RequiredArgsConstructor
public class OpenChatController {

    private final OpenChatService openChatService;
    private final MessageService messageService;

    /**
     * 채팅방 생성
     */
    @PostMapping
    public ResponseEntity<ApiResponse<OpenChatDto>> createChatRoom(
            @RequestBody CreateChatRoomDto createDto
    ) {
        OpenChatDto chatRoom = openChatService.createChatRoom(createDto);

        return ResponseEntity.ok(ApiResponse.success(chatRoom));
    }

    /**
     * 채팅방 목록 조회
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<OpenChatDto>>> getChatRoomList() {
        List<OpenChatDto> chatRooms = openChatService.getChatRoomList();

        return ResponseEntity.ok(ApiResponse.success(chatRooms));
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
            @PathVariable Long chatId, @RequestBody JoinChatRoomDto joinDto
    ) {
        openChatService.joinChatRoom(chatId, joinDto);

        return ResponseEntity.ok(ApiResponse.success(null));
    }

    /**
     * 채팅방 나가기
     */
    @PostMapping("/{chatId}/leave")
    public ResponseEntity<ApiResponse<Void>> leaveChatRoom(
            @PathVariable Long chatId, @RequestBody LeaveChatRoomDto leaveDto
    ) {
        openChatService.leaveChatRoom(chatId, leaveDto);

        return ResponseEntity.ok(ApiResponse.success(null));
    }

    /**
     * 메시지 전송
     */
    @PostMapping("/{chatId}/messages")
    public ResponseEntity<ApiResponse<MessageDto>> sendMessage(
            @PathVariable Long chatId, @RequestBody SendMessageDto messageDto
    ) {
        MessageDto sentMessage = messageService.sendMessage(chatId, messageDto);

        return ResponseEntity.ok(ApiResponse.success(sentMessage));
    }

    /**
     * 메시지 목록 조회
     */
    @GetMapping("/{chatId}/messages")
    public ResponseEntity<ApiResponse<List<MessageDto>>> getMessages(
            @PathVariable Long chatId
    ) {
        List<MessageDto> messages = messageService.getMessages(chatId);

        return ResponseEntity.ok(ApiResponse.success(messages));
    }

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