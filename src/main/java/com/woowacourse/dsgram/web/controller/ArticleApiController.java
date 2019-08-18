package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.service.ArticleApiService;
import com.woowacourse.dsgram.service.FileService;
import com.woowacourse.dsgram.service.dto.ArticleRequest;
import com.woowacourse.dsgram.service.vo.FileInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles")
public class ArticleApiController {

    private ArticleApiService articleApiService;
    private FileService fileService;

    public ArticleApiController(ArticleApiService articleApiService, FileService fileService) {
        this.articleApiService = articleApiService;
        this.fileService = fileService;
    }

    @PostMapping
    public ResponseEntity<Article> create(ArticleRequest articleRequest) {
        FileInfo fileInfo = fileService.save(articleRequest.getFile());
        Article article = convertFrom(articleRequest, fileInfo);
        Article savedArticle = articleApiService.create(article);
        return new ResponseEntity<>(savedArticle, HttpStatus.OK);
    }

    private Article convertFrom(ArticleRequest articleRequest, FileInfo fileInfo) {
        return new Article(articleRequest.getContents(), fileInfo.getFileName(), fileInfo.getFilePath());
    }

    @GetMapping("{articleId}/file")
    public ResponseEntity<byte[]> showArticleFile(@PathVariable long articleId) {
        Article article = articleApiService.findById(articleId);
        byte[] base64 = fileService.readFile(article);

        return new ResponseEntity<>(base64, HttpStatus.OK);
    }

}
