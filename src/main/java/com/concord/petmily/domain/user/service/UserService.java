package com.concord.petmily.domain.user.service;

import com.concord.petmily.domain.user.dto.AddUserRequest;
import com.concord.petmily.domain.user.entity.User;
import com.concord.petmily.domain.walk.dto.WalkDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface UserService {

    /**
     *
     * @param dto
     * @return
     */
    Long registerUser(AddUserRequest dto);

    /**
     *
     */
    User findById(Long userId);

    /**
     * TODO 중복 기능?
     */
    User findByUsername(String username);
}
