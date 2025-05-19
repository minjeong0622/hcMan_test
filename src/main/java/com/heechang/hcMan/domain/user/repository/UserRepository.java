package com.heechang.hcMan.domain.user.repository;

import com.heechang.hcMan.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLoginId(String loginId);

    Optional<User> findByBusinessRegNumber(String businessRegNumber);

    List<User> findByLoginIdContainsIgnoreCase(String loginId);

    boolean existsByBusinessRegNumber(String businessRegNumber);

    // 로그인 시 존재 여부 체크 시 두 필드를 모두 사용할 수 있도록 메서드 하나 더
    default boolean existsByLoginIdOrBusinessRegNumber(String credential) {
        return findByLoginId(credential).isPresent()
                || findByBusinessRegNumber(credential).isPresent();
    }

}
