package com.woowacourse.dsgram.service;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.domain.Comment;
import com.woowacourse.dsgram.domain.FileInfo;
import com.woowacourse.dsgram.domain.User;
import com.woowacourse.dsgram.domain.exception.InvalidUserException;
import com.woowacourse.dsgram.domain.repository.CommentRepository;
import com.woowacourse.dsgram.service.dto.CommentRequest;
import com.woowacourse.dsgram.service.exception.EmptyCommentRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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
    @DisplayName("댓글 생성 성공")
    void create() {
        given(articleService.findById(commentRequest.getArticleId())).willReturn(article);
        given(userService.findUserById(userId)).willReturn(user);
        commentService.create(commentRequest, userId);

        verify(commentRepository).save(comment);
    }

    @Test
    @DisplayName("내용 없는 댓글 생성 실패")
    void comment_without_contents_create_fail() {
        CommentRequest requestWithoutContents = new CommentRequest(" ", 1L);
        Comment commentWithoutContents = comment = Comment.builder()
                                                    .user(user)
                                                    .article(article)
                                                    .contents(" ")
                                                    .build();
        given(articleService.findById(requestWithoutContents.getArticleId())).willReturn(article);
        given(userService.findUserById(userId)).willReturn(user);

        doThrow(new EmptyCommentRequestException("댓글의 내용을 입력해주세요."))
                .when(commentRepository).save(commentWithoutContents);

        assertThrows(EmptyCommentRequestException.class, () -> {
            commentService.create(requestWithoutContents, userId);
        });
    }

    @Test
    @DisplayName("댓글 삭제 성공")
    void delete_success() {
        given(commentRepository.findById(comment.getId())).willReturn(Optional.of(comment));
        doNothing().when(commentRepository).delete(comment);
        commentService.delete(comment.getId(), user.getId());

    }

    @Test
    @DisplayName("다른 유저가 쓴 댓글 삭제 실패")
    void delete_not_self_comment_fail() {
        given(commentRepository.findById(comment.getId())).willReturn(Optional.of(comment));
        doThrow(new InvalidUserException("글 작성자만 수정, 삭제가 가능합니다.")).when(commentRepository).delete(comment);
        long notAuthor = user.getId() + 1L;

        assertThrows(InvalidUserException.class, () -> {
            commentService.delete(comment.getId(), notAuthor);
        });
    }

}