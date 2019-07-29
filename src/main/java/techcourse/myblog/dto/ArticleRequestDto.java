package techcourse.myblog.dto;

import org.apache.commons.lang3.StringUtils;
import techcourse.myblog.domain.Article;
import techcourse.myblog.domain.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ArticleRequestDto {
    private static final String CONTENTS_BLANK_ERROR = "내용을 입력해주세요.";
    private static final String TITLE_BLANK_ERROR = "제목을 입력해주세요.";
    private static final String DEFAULT_COVER_URL = "/images/pages/index/study.jpg";

    @NotBlank(message = TITLE_BLANK_ERROR)
    private String title;

    @NotNull
    private String coverUrl;

    @NotBlank(message = CONTENTS_BLANK_ERROR)
    private String contents;

    public ArticleRequestDto(String title, String coverUrl, String contents) {
        this.title = title;
        this.coverUrl = coverUrl;
        if (StringUtils.isBlank(coverUrl)) {
            this.coverUrl = DEFAULT_COVER_URL;
        }
        this.contents = contents;
    }

    public Article toEntity(User author) {
        Article article = new Article(title, coverUrl, contents);
        article.setAuthor(author);
        return article;
    }

    public String getTitle() {
        return title;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public String getContents() {
        return contents;
    }
}
