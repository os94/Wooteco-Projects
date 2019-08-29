package com.woowacourse.dsgram.domain.repository;

import com.woowacourse.dsgram.domain.Follow;
import com.woowacourse.dsgram.domain.User;
import com.woowacourse.dsgram.domain.repository.query.FollowQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findAllByTo(User user);

    List<Follow> findAllByFrom(User user);

    Follow findByFromAndTo(User guest, User feedOwner);

    long countByTo(User user);

    long countByFrom(User user);

    boolean existsByFromAndTo(User guest, User feedOwner);

    @Query(FollowQuery.FIND_FOLLOWING_USER_BY_QUERY)
    List<User> findFollows(@Param("followedUser") User followedUser);
}
