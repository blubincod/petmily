package com.concord.petmily.domain.openchat.service;

import com.concord.petmily.domain.openchat.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpenChatServiceImpl implements OpenChatService {

    @Override
    public OpenChatDto createChatRoom(CreateChatRoomDto createDto) {
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