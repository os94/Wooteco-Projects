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
        return this.articles.get(id);
    }
}
