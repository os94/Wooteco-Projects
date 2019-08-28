package com.woowacourse.dsgram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DsGramApplication {
    public static void main(String[] args) {
        SpringApplication.run(DsGramApplication.class, args);
    }
}
