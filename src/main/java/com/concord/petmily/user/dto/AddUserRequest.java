package com.concord.petmily.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class AddUserRequest {

    @NotNull(message = "아이디를 입력해주세요.")
    @NotEmpty(message = "아이디를 입력해주세요")
    private String username;

    @NotNull(message = "이메일을 이력해주세요.")
    @NotEmpty(message = "이메일을 입력해주세요.")
    @Email(message = "이메일을 입력해주세요.")
    private String email;

    @NotNull(message = "비밀번호를 입력해주세요.")
    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String password;

    private String address;
    private Integer age;
    private String birthDate;
    private String gender;
    private String name;
    private String nickname;
    private String phone;
    private LocalDateTime registeredAt = LocalDateTime.now();
    private String role = "USER";
    private String status = "ACTIVE";
}
