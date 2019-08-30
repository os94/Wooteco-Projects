package com.woowacourse.dsgram;

import com.woowacourse.dsgram.domain.*;
import com.woowacourse.dsgram.domain.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DsGramApplication {
    public static void main(String[] args) {
        SpringApplication.run(DsGramApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(FileInfoRepository fileInfoRepository,
                                  UserRepository userRepository,
                                  FollowRepository followRepository,
                                  ArticleRepository articleRepository,
                                  HashTagRepository hashTagRepository) {
        return (args) -> {

            String homeDirectory = System.getProperty("user.dir");
            String path = homeDirectory + "/" + "src/main/resources/static/images/default";

            FileInfo sampleImg1 = new FileInfo("sample_img_01.jpg", path);
            FileInfo sampleImg2 = new FileInfo("sample_img_02.jpg", path);
            sampleImg1 = fileInfoRepository.save(sampleImg1);
            sampleImg2 = fileInfoRepository.save(sampleImg2);

            User user = User.builder()
                    .email("qwe@naver.com")
                    .userName("qwe")
                    .nickName("qwe")
                    .password("qweqwe")
                    .fileInfo(sampleImg1)
                    .build();

            user = userRepository.save(user);

            User user2 = User.builder()
                    .email("qwe2@naver.com")
                    .userName("qwe2")
                    .nickName("qwe2")
                    .password("qweqwe")
                    .fileInfo(sampleImg2)
                    .build();
            user2 = userRepository.save(user2);

            Follow follow = Follow.builder().from(user2).to(user).build();
            followRepository.save(follow);

            for (int i = 0; i < 18; i++) {
                String contents = i + "#qwe";
                Article article = Article.builder()
                        .author(user)
                        .contents(contents)
                        .fileInfo(sampleImg1)
                        .build();
                article = articleRepository.save(article);

                HashTag hashTag = HashTag.builder()
                        .article(article)
                        .keyword(contents)
                        .build();
                hashTagRepository.save(hashTag);
            }

            for (int i = 0; i < 5; i++) {
                String contents = i + "#hello";
                Article article = Article.builder()
                        .author(user2)
                        .contents(contents)
                        .fileInfo(sampleImg1)
                        .build();
                articleRepository.save(article);

                HashTag hashTag = HashTag.builder()
                        .article(article)
                        .keyword(contents)
                        .build();
                hashTagRepository.save(hashTag);
            }

        };

    }

}
