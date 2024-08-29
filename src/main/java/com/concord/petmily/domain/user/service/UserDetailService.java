package com.concord.petmily.domain.user.service;

import com.concord.petmily.domain.user.entity.User;
import com.concord.petmily.domain.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 사용자 이름으로 사용자 세부 정보를 로드합니다.
     * @param username 사용자 이름
     * @return 사용자 세부 정보(UserDetails)
     * @throws UsernameNotFoundException 사용자 이름이 존재하지 않는 경우
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("찾을 수 없는 아이디: " + username));
        return user;
    }
}
