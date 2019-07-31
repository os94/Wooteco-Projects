package techcourse.myblog.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import techcourse.myblog.domain.exception.ArticleNotFoundException;
import techcourse.myblog.domain.exception.InvalidAccessException;
import techcourse.myblog.domain.exception.MisMatchAuthorException;
import techcourse.myblog.domain.model.Article;
import techcourse.myblog.domain.model.User;
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

    @Transactional(readOnly = true)
    public Article findById(long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException("해당하는 게시글을 찾지 못했습니다."));
    }

    @Transactional(readOnly = true)
    public Article findByIdAsAuthor(long id, User user) {
        Article article = findById(id);
        if (!article.isAuthor(user)) {
            throw new MisMatchAuthorException("작성자만 접근가능합니다.");
        }
        return article;
    }

    @Transactional
    public Article save(Article article) {
        return articleRepository.save(article);
    }

    @Transactional
    public Article updateByIdAsAuthor(long id, Article articleToUpdate) {
        Article article = findById(id);
        if (!article.isAuthor(articleToUpdate.getAuthor())) {
            throw new MisMatchAuthorException("작성자만 접근가능합니다.");
        }
        return article.update(articleToUpdate);
    }

    @Transactional
    public void deleteByIdAsAuthor(long id, User user) {
        if (!findById(id).isAuthor(user)) {
            throw new MisMatchAuthorException("작성자만 접근가능합니다.");
        }
        articleRepository.deleteById(id);
    }
}
