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
        this.articles.add(article);
    }

    public Article findById(int id) {
        return this.articles.stream()
                .filter(article -> id == article.getId())
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
    }

    public int newArticleId() {
        return this.articles.stream()
                .mapToInt(Article::getId)
                .max()
                .orElse(-1) + 1;
    }

    public void update(int articleId, Article article) {
        Article articleToUpdate = findById(articleId);
        this.articles.set(this.articles.indexOf(articleToUpdate), article);
    }

    public void remove(int articleId) {
        int indexToRemove = this.articles.stream()
                .filter(article -> article.getId() == articleId)
                .map(article -> this.articles.indexOf(article))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        this.articles.remove(indexToRemove);
    }
}
