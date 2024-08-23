package com.concord.petmily.common.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * data.sql의 회원 테이블에 해싱한 비밀번호를 넣기 위한 클래스
 */
public class PasswordHashGenerator {
    public static void main(String[] args) {
        // BCryptPasswordEncoder 인스턴스 생성
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // 이메일 앞부분을 기준으로 설정한 비밀번호
        String[] passwords = {"lee123", "park123", "choi123", "jung123", "kim123"};

        // 각 비밀번호를 해싱
        for (String password : passwords) {
            // 비밀번호 해싱
            String hashedPassword = passwordEncoder.encode(password);
            System.out.println("원본 비밀번호: " + password + " -> 해싱된 비밀번호: " + hashedPassword);
        }
    }
}
