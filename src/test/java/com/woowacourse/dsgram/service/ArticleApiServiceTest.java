package com.woowacourse.dsgram.service;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.domain.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class ArticleApiServiceTest {

    private Article article;

    @InjectMocks
    private ArticleApiService articleApiService;

    @Mock
    ArticleRepository articleRepository;


    @BeforeEach
    void setUp() {
        article = new Article("contents", "fileName", "filePath");
    }

    @Test
    void 게시글_생성_성공() {
        given(articleRepository.save(article)).willReturn(article);

        articleApiService.create(article);

        verify(articleRepository).save(article);
    }

    @Test
    void 게시글_조회_성공() {
        given(articleRepository.findById(any())).willReturn(Optional.of(article));
        articleApiService.findById(1L);
        verify(articleRepository).findById(anyLong());
    }

    @Test
    void 게시글_조회_실패() {
        articleApiService.create(article);
        assertThrows(EntityNotFoundException.class, () -> articleApiService.findById(1L));
    }
}
