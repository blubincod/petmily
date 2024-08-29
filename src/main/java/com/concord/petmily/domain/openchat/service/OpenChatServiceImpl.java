package com.concord.petmily.domain.openchat.service;

import com.concord.petmily.common.exception.ErrorCode;
import com.concord.petmily.domain.openchat.dto.CreateChatRoomDto;
import com.concord.petmily.domain.openchat.dto.OpenChatDto;
import com.concord.petmily.domain.openchat.dto.UpdateChatRoomDto;
import com.concord.petmily.domain.openchat.entity.*;
import com.concord.petmily.domain.openchat.repository.OpenChatCategoryRepository;
import com.concord.petmily.domain.openchat.repository.OpenChatParticipantRepository;
import com.concord.petmily.domain.openchat.repository.OpenChatRepository;
import com.concord.petmily.domain.user.entity.User;
import com.concord.petmily.domain.user.exception.UserNotFoundException;
import com.concord.petmily.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenChatServiceImpl implements OpenChatService {

    private final UserRepository userRepository;
    private final OpenChatRepository openChatRepository;
    private final OpenChatCategoryRepository openChatCategoryRepository;
    private final OpenChatParticipantRepository openChatParticipantRepository;

    // 회원 정보 조회
    private User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    // 채팅 카테고리 정보 조회
    private OpenChatCategory getCategory(Long categoryId) {
        return openChatCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("해당 카테고리가 존재하지 않습니다."));
    }

    /**
     * 채팅방 생성
     */
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

    /**
     * 채팅방 목록 조회
     */
    @Override
    public Page<OpenChatDto> getChatRoomList(Pageable pageable) {
        Page<OpenChat> openChats = openChatRepository.findAll(pageable);

        return openChats.map(OpenChatDto::fromEntity);
    }

    /**
     * 채팅방 정보 조회
     */
    @Override
    public OpenChatDto getChatRoomInfo(Long chatId) {
        OpenChat openChat = openChatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("해당 채팅이 존재하지 않습니다."));

        return OpenChatDto.fromEntity(openChat);
    }

    /**
     * 오픈채팅방 참여
     */
    @Override
    @Transactional
    public void joinChatRoom(Long chatId, String username) {
        OpenChat openChat = openChatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("채팅방을 찾을 수 없습니다."));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        // 이미 참여한 사용자인지 확인
        if (openChatParticipantRepository.findByUserIdAndOpenChatId(openChat.getId(), user.getId()).isPresent()) {
            throw new RuntimeException("이미 채팅방에 참여한 사용자입니다.");
        }

        // 최대 참여자 수 확인
        if (openChat.getCurrentParticipants() >= openChat.getMaxParticipants()) {
            throw new RuntimeException("채팅방이 가득 찼습니다.");
        }

        OpenChatParticipant participant = new OpenChatParticipant(user, openChat, ParticipantStatus.ACTIVE, LocalDateTime.now());
        openChatParticipantRepository.save(participant);

        openChat.incrementParticipants();
        openChatRepository.save(openChat);
    }

    /**
     * 오픈채팅방 나가기
     */
    @Override
    @Transactional
    public void leaveChatRoom(Long chatId, String username) {
        OpenChat openChat = openChatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("채팅방을 찾을 수 없습니다."));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        OpenChatParticipant participant = openChatParticipantRepository
                .findByUserIdAndOpenChatId(openChat.getId(), user.getId())
                .orElseThrow(() -> new RuntimeException("참가자를 찾을 수 없습니다."));

        participant.setStatus(ParticipantStatus.LEFT);
        participant.setLeftAt(LocalDateTime.now());

        openChat.decrementParticipants();
        openChatRepository.save(openChat);
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