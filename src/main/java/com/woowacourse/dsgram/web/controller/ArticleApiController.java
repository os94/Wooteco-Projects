package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.service.ArticleService;
import com.woowacourse.dsgram.service.dto.ArticleEditRequest;
import com.woowacourse.dsgram.service.dto.ArticleRequest;
import com.woowacourse.dsgram.service.dto.user.LoggedInUser;
import com.woowacourse.dsgram.web.argumentresolver.UserSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles")
public class ArticleApiController {

    private ArticleService articleService;

    public ArticleApiController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public ResponseEntity<Article> create(ArticleRequest articleRequest, @UserSession LoggedInUser loggedInUser) {
        Article savedArticle = articleService.create(articleRequest, loggedInUser);
        return new ResponseEntity<>(savedArticle, HttpStatus.OK);
    }

    @GetMapping("{articleId}/file")
    public ResponseEntity<byte[]> showArticleFile(@PathVariable long articleId) {
        return new ResponseEntity<>(articleService.findFileById(articleId), HttpStatus.OK);
    }

    @PutMapping("{articleId}")
    public ResponseEntity update(@PathVariable long articleId, @RequestBody ArticleEditRequest articleEditRequest, @UserSession LoggedInUser loggedInUser) {
        articleService.update(articleId, articleEditRequest, loggedInUser);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("{articleId}")
    public ResponseEntity delete(@PathVariable long articleId, @UserSession LoggedInUser loggedInUser) {
        articleService.delete(articleId, loggedInUser);
        return new ResponseEntity(HttpStatus.OK);
    }
}
