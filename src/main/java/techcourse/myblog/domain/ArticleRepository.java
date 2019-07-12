package techcourse.myblog.domain;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

@Repository
public class ArticleRepository {
    private static final int INITIAL_VALUE = -1;
    private static final int INCREMENT_VALUE = 1;

    private List<Article> articles = new ArrayList<>();

    public List<Article> findAll() {
        return unmodifiableList(this.articles);
    }

    public void add(Article article) {
        this.articles.add(article);
    }

    public Article findById(int id) {
        return this.articles.stream()
                .filter(article -> article.matchId(id))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
    }

    public int newArticleId() {
        return this.articles.stream()
                .mapToInt(Article::getId)
                .max()
                .orElse(INITIAL_VALUE) + INCREMENT_VALUE;
    }

    public void update(int id, Article article) {
        Article articleToUpdate = findById(id);
        this.articles.set(this.articles.indexOf(articleToUpdate), article);
    }

    public void remove(int id) {
        int indexToRemove = this.articles.stream()
                .filter(article -> article.matchId(id))
                .map(article -> this.articles.indexOf(article))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        this.articles.remove(indexToRemove);
    }
}
