package com.concord.petmily.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddAdminRequest {

    private String role;
    private String status;
}