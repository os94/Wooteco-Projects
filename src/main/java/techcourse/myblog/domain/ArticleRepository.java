package techcourse.myblog.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {

}
