package com.concord.petmily.user.service;

import com.concord.petmily.user.dto.AddUserRequest;
import com.concord.petmily.user.entity.User;

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
