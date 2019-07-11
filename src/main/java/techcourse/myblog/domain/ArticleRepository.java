package techcourse.myblog.domain;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

@Repository
public class ArticleRepository {
    private List<Article> articles = new ArrayList<>();

    public List<Article> findAll() {
        return unmodifiableList(this.articles);
    }

    public void add(Article article) {
        this.articles.add(newArticleId(), article);
    }

    public Article findById(int id) {
        return articles.stream()
                .filter(article -> id == article.getId())
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
    }

    public int newArticleId() {
        return articles.stream()
                .mapToInt(Article::getId)
                .max()
                .orElse(-1) + 1;
    }

    public void update(int articleId, Article article) {
        Article articleToUpdate = findById(articleId);
        articles.set(articles.indexOf(articleToUpdate), article);
    }
}
