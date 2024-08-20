package com.concord.petmily.domain.user.service;

import com.concord.petmily.domain.user.dto.AddUserRequest;
import com.concord.petmily.domain.user.entity.User;

public interface UserService {

    /**
     *
     * @param dto
     * @return
     */
    Long registerUser(AddUserRequest dto);

    /**
     *
     * @param userId
     * @return
     */
    User findById(Long userId);

    /**
     *
     * @param username
     * @return
     */
    User findByUsername(String username);
}
