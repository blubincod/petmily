package com.concord.petmily.domain.openchat.service;

import com.concord.petmily.domain.openchat.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OpenChatService {
    /**
     * 채팅방 생성
     */
    OpenChatDto createChatRoom(String username, CreateChatRoomDto createDto);

    /**
     * 채팅방 목록 조회
     */
    Page<OpenChatDto> getChatRoomList(Pageable pageable);

    /**
     * 특정 채팅방 정보 조회
     */
    OpenChatDto getChatRoomInfo(Long chatId);

    /**
     * 채팅방 참여
     */
    void joinChatRoom(Long chatId, String username);

    /**
     * 채팅방 나가기
     */
    void leaveChatRoom(Long chatId, String username);

    /**
     * 채팅방 검색
     */
    List<OpenChatDto> searchChatRooms(String keyword);

    /**
     * 채팅방 정보 수정 (방장 전용)
     */
    OpenChatDto updateChatRoom(Long chatId, UpdateChatRoomDto updateDto);

    /**
     * 채팅방 삭제 (방장 전용)
     */
    void deleteChatRoom(Long chatId);
}

