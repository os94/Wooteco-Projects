package com.woowacourse.dsgram.service;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.domain.Comment;
import com.woowacourse.dsgram.domain.FileInfo;
import com.woowacourse.dsgram.domain.User;
import com.woowacourse.dsgram.domain.repository.CommentRepository;
import com.woowacourse.dsgram.service.dto.CommentRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class CommentServiceTest {

    @InjectMocks
    CommentService commentService;

    @Mock
    CommentRepository commentRepository;

    @Mock
    ArticleService articleService;

    @Mock
    UserService userService;


    private CommentRequest commentRequest;
    private Comment comment;
    private User user;
    private Article article;
    private Long userId;

    @BeforeEach
    void setUp() {
        commentRequest = new CommentRequest("contents", 1L);
        FileInfo fileInfo = FileInfo.builder()
                .fileName("filename")
                .filePath("filePath")
                .build();

        user = User.builder()
                .userName("test")
                .nickName("test")
                .email("test@gmail.com")
                .password("test123")
                .webSite("")
                .intro("")
                .build();

        article = Article.builder()
                .author(user)
                .contents("contents")
                .fileInfo(fileInfo)
                .build();
        comment = Comment.builder()
                .user(user)
                .article(article)
                .contents("contents")
                .build();
        userId = 1L;
    }

    @Test
    void create() {
        given(articleService.findById(commentRequest.getArticleId())).willReturn(article);
        given(userService.findUserById(userId)).willReturn(user);


        commentService.create(commentRequest, userId);

        verify(commentRepository).save(comment);
    }

    @Test
    void delete() {

    }

    @Test
    void update() {
    }
}