package sql;

import nextstep.jdbc.JdbcTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import slipp.support.db.ConnectionManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class DataSetTest {

    private static final String TABLE_NAME = "survey_results_public";
    private final JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance(ConnectionManager.getDataSource("src/test/java/sql/db.properties"));

    @Test
    @DisplayName("Coding as a Hobby")
    void coding_Hobby_Percentage_Test() {
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME;
        Optional<Object> result = jdbcTemplate.queryForObject(
                sql,
                rs -> rs.getObject("count(*)")
        );

        assertThat(result.isPresent()).isTrue();
        assertThat((long) result.get()).isEqualTo(98855L);
    }
}
