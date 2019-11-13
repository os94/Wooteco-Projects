package sql;

import nextstep.jdbc.JdbcTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import slipp.support.db.ConnectionManager;
import sql.dto.HobbyDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DataSetTest {

    private final JdbcTemplate jdbcTemplate =
            JdbcTemplate.getInstance(ConnectionManager.getDataSource("src/test/java/sql/db.properties"));

    @Test
    @DisplayName("Coding as a Hobby")
    void test_hobby() {
        HobbyDto yesFixture = new HobbyDto("Yes", 80.8);
        HobbyDto noFixture = new HobbyDto("No", 19.2);

        String sql = "select hobby, round((count(*) * 100 / (select count(*) from survey_results_public)), 1) as percent\n" +
                "from survey_results_public\n" +
                "group by hobby\n" +
                "order by hobby desc;";

        List<HobbyDto> result = jdbcTemplate.query(
                sql,
                rs -> new HobbyDto(rs.getString("hobby"), rs.getDouble("percent"))
        );

        assertThat(result.contains(yesFixture)).isTrue();
        assertThat(result.contains(noFixture)).isTrue();
    }
}
