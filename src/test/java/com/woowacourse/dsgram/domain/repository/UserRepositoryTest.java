package com.woowacourse.dsgram.domain.repository;

import com.woowacourse.dsgram.domain.FileInfo;
import com.woowacourse.dsgram.domain.Follow;
import com.woowacourse.dsgram.domain.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    FollowRepository followRepository;
    @Autowired
    FileInfoRepository fileInfoRepository;

    User user1;
    User user2;
    User user3;
    User user4;

    @BeforeAll
    void setup() {
        FileInfo fileInfo = new FileInfo("name", "path");
        FileInfo fileInfo2 = new FileInfo("name", "path");
        FileInfo fileInfo3 = new FileInfo("name", "path");
        FileInfo fileInfo4 = new FileInfo("name", "path");

        user1 = new User("email1@email.com", "nick1", "name", "pass", "web", "intro", fileInfo, false);
        user2 = new User("email2@email.com", "nick2", "name", "pass", "web", "intro", fileInfo2, false);
        user3 = new User("email3@email.com", "nick3", "name", "pass", "web", "intro", fileInfo3, false);
        user4 = new User("email4@email.com", "nick4", "name", "pass", "web", "intro", fileInfo4, false);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);

        Follow follow1 = new Follow(user1, user3);
        Follow follow2 = new Follow(user1, user4);

        followRepository.save(follow1);
        followRepository.save(follow2);

    }

    @Test
    void findFollows() {

        List<User> userList = followRepository.findFollows(user1);
        assertThat(userList.size()).isEqualTo(2);
    }
}