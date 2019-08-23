package com.woowacourse.dsgram.domain.repository;

import com.woowacourse.dsgram.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByNickName(String nickName);

    boolean existsByEmail(String email);
}
