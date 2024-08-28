package com.concord.petmily.domain.openchat.service;

import com.concord.petmily.common.exception.ErrorCode;
import com.concord.petmily.domain.openchat.dto.*;
import com.concord.petmily.domain.openchat.entity.ChatStatus;
import com.concord.petmily.domain.openchat.entity.OpenChat;
import com.concord.petmily.domain.openchat.entity.OpenChatCategory;
import com.concord.petmily.domain.openchat.repository.OpenChatCategoryRepository;
import com.concord.petmily.domain.openchat.repository.OpenChatRepository;
import com.concord.petmily.domain.user.entity.User;
import com.concord.petmily.domain.user.exception.UserNotFoundException;
import com.concord.petmily.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenChatServiceImpl implements OpenChatService {

    private final UserRepository userRepository;
    private final OpenChatRepository openChatRepository;
    private final OpenChatCategoryRepository openChatCategoryRepository;

    // 회원 정보 조회
    private User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    // 오픈채팅 카테고리 정보 조회
    private OpenChatCategory getCategory(Long categoryId) {
        return openChatCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("해당 카테고리가 존재하지 않습니다."));
    }

    @Override
    public OpenChatDto createChatRoom(String username, CreateChatRoomDto createDto) {
        User user = getUser(username);
        OpenChatCategory category = getCategory(createDto.getCategoryId());

        OpenChat openChat = createOpenChat(user, category, createDto);
        OpenChat savedOpenChat = openChatRepository.save(openChat);

        return OpenChatDto.fromEntity(savedOpenChat);
    }

    // 오픈 채팅 객체 생성
    private OpenChat createOpenChat(User creator, OpenChatCategory category, CreateChatRoomDto createDto) {
        return OpenChat.builder()
                .title(createDto.getTitle())
                .description(createDto.getDescription())
                .category(category)
                .creator(creator)
                .maxParticipants(createDto.getMaxParticipants())
                .status(ChatStatus.ACTIVE)
                .isPublic(createDto.isPublic())
                .build();
    }

    @Override
    public Page<OpenChatDto> getChatRoomList(Pageable pageable) {
        Page<OpenChat> openChats = openChatRepository.findAll(pageable);

        return openChats.map(OpenChatDto::fromEntity);
    }

    @Override
    public OpenChatDto getChatRoomInfo(Long chatId) {
        OpenChat openChat = openChatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("해당 채팅이 존재하지 않습니다."));

        return OpenChatDto.fromEntity(openChat);
    }

    @Override
    public void joinChatRoom(Long chatId, JoinChatRoomDto joinDto) {

    }

    @Override
    public void leaveChatRoom(Long chatId, LeaveChatRoomDto leaveDto) {

    }

    @Override
    public List<OpenChatDto> searchChatRooms(String keyword) {
        return null;
    }

    @Override
    public OpenChatDto updateChatRoom(Long chatId, UpdateChatRoomDto updateDto) {
        return null;
    }

    @Override
    public void deleteChatRoom(Long chatId) {

    }
}