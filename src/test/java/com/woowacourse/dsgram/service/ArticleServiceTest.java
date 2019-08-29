package com.woowacourse.dsgram.service;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.domain.FileInfo;
import com.woowacourse.dsgram.domain.User;
import com.woowacourse.dsgram.domain.repository.ArticleRepository;
import com.woowacourse.dsgram.service.dto.article.ArticleRequest;
import com.woowacourse.dsgram.service.dto.user.LoggedInUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class ArticleServiceTest {

    public static final int DEFAULT_PAGE_NUMBER = 0;
    @InjectMocks
    private ArticleService articleService;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private UserService userService;

    @Mock
    private FileService fileService;

    @Mock
    private HashTagService hashTagService;

    private Article article;
    private final ArticleRequest articleRequest = new ArticleRequest("qwe", new MockMultipartFile("name", "qwe".getBytes()));
    private final LoggedInUser loggedInUser = new LoggedInUser(1, "qwe@naver.com", "qwe", "qwe");

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .userName("test")
                .nickName("test")
                .email("test@gmail.com")
                .password("test123")
                .webSite("")
                .intro("")
                .build();

        article = new Article("contents", new FileInfo("fileName", "filePath"), user);
    }

    @Test
    void 게시글_생성_성공() {
        given(articleRepository.save(article)).willReturn(article);
        given(userService.findUserById(anyLong())).willReturn(user);
        articleService.createAndFindId(articleRequest, loggedInUser);

        verify(articleRepository).save(article);
    }

    @Test
    void 게시글_조회_성공() {
        given(articleRepository.findById(any())).willReturn(Optional.of(article));
        articleService.findById(1L);
        verify(articleRepository).findById(anyLong());
    }

    @Test
    void 게시글_조회_실패() {
        given(articleRepository.findById(anyLong())).willReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> articleService.findById(1L));
    }
}