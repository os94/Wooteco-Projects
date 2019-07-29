package techcourse.myblog.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import techcourse.myblog.domain.Article;
import techcourse.myblog.domain.exception.ArticleNotFoundException;
import techcourse.myblog.domain.exception.InvalidAccessException;
import techcourse.myblog.domain.repository.ArticleRepository;

import java.util.List;

import static java.util.Collections.unmodifiableList;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Transactional(readOnly = true)
    public List<Article> findAll() {
        return unmodifiableList(articleRepository.findAll());
    }

    @Transactional
    public Article save(Article article) {
        return articleRepository.save(article);
    }

    @Transactional
    public Article findById(long id) {
        return articleRepository.findById(id).orElseThrow(() -> new ArticleNotFoundException("해당하는 게시글을 찾지 못했습니다."));
    }

    @Transactional
    public Article update(long id, Article articleToUpdate) {
        Article originArticle = articleRepository.findArticleById(id);
        long authorId = originArticle.getAuthor().getId();
        long loginUserId = articleToUpdate.getAuthor().getId();
        validate(authorId, loginUserId);
        originArticle.update(articleToUpdate);
        return originArticle;
    }

    public void validate(long authorId, long loginUserId) {
        if (authorId == loginUserId) {
            return;
        }
        throw new InvalidAccessException("잘못된 접근입니다.");
    }

    @Transactional
    public void deleteById(long id) {
        articleRepository.deleteById(id);
    }
}
