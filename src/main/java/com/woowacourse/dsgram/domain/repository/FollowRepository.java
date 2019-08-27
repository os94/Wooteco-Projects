package com.woowacourse.dsgram.domain.repository;

import com.woowacourse.dsgram.domain.Follow;
import com.woowacourse.dsgram.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow,Long> {
    List<Follow> findAllByTo(User user);

    List<Follow> findAllByFrom(User user);

    Follow findByFromAndTo(User guest, User feedOwner);

    long countByTo(User user);

    long countByFrom(User user);

    boolean existsByFromAndTo(User guest, User feedOwner);
}
