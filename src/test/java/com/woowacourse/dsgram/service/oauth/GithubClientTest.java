package com.woowacourse.dsgram.service.oauth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Java6Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GithubClientTest {

    @Autowired
    private GithubClient githubClient;

    @Test
    void loadProperties() {
        assertThat(githubClient.getClientId()).isEqualTo("f43a512272b4d658b940");
    }
}