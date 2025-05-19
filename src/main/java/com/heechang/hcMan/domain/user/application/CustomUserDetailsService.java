package com.heechang.hcMan.domain.user.application;

import com.heechang.hcMan.domain.user.entity.User;
import com.heechang.hcMan.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1) loginId 로 먼저 조회
        User user = userRepository.findByLoginId(username)
                // 2) 없으면 businessRegNumber 로 조회
                .or(() -> userRepository.findByBusinessRegNumber(username))
                .orElseThrow(() ->
                        new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username)
                );

        return new CustomUserDetails(user);
    }

    // 추가: 검색어(query)를 통한 거래처 검색
    public List<User> searchUsers(String query) {

        if(query == null || query.equalsIgnoreCase("null")) {
            query = "";
        }

        if (query != null && query.matches("\\d+")) { // query가 숫자면
            Long id = Long.valueOf(query);
            return userRepository.findById(id)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList());
        }
        // 그렇지 않으면 기존 이름 검색 로직 실행
        return userRepository.findByLoginIdContainsIgnoreCase(query);
    }

}
