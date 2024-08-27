package com.concord.petmily.domain.openchat.service;

import com.concord.petmily.common.exception.ErrorCode;
import com.concord.petmily.domain.openchat.dto.*;
import com.concord.petmily.domain.openchat.entity.OpenChat;
import com.concord.petmily.domain.openchat.entity.OpenChatCategory;
import com.concord.petmily.domain.openchat.repository.OpenChatCategoryRepository;
import com.concord.petmily.domain.openchat.repository.OpenChatRepository;
import com.concord.petmily.domain.user.entity.User;
import com.concord.petmily.domain.user.exception.UserNotFoundException;
import com.concord.petmily.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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

    @Override
    public OpenChatDto createChatRoom(String username, CreateChatRoomDto createDto) {
        User user = getUser(username);

//        OpenChatCategory openChatCategory = openChatCategoryRepository.findById(createDto.getCategoryId());

//        openChatRepository.save()

        return null;
    }

    @Override
    public List<OpenChatDto> getChatRoomList() {
        return null;
    }

    @Override
    public OpenChatDto getChatRoomInfo(Long chatId) {
        return null;
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