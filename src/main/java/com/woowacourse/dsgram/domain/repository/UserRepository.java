package com.woowacourse.dsgram.domain.repository;

import com.woowacourse.dsgram.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByNickName(String nickName);

    boolean existsByNickName(String nickName);

    boolean existsByEmail(String email);

}
