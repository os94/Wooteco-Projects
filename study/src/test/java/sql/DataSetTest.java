package sql;

import nextstep.jdbc.JdbcTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import slipp.support.db.ConnectionManager;
import sql.dto.CodingYearsDto;
import sql.dto.HobbyDto;

import java.util.Arrays;
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

    @Test
    @DisplayName("Years of Professional Coding Experience by Developer Type")
    void test_years() {
        List<CodingYearsDto> expected = makeYearsFixture();

        String sql = "SELECT \n" +
                "    SUBSTRING_INDEX(SUBSTRING_INDEX(survey_results_public.DevType, ';', numbers.n), ';', - 1) AS DeveloperType,\n" +
                "    ROUND(\n" +
                "      AVG(\n" +
                "        IF(survey_results_public.YearsCodingProf LIKE '30 or more years',\n" +
                "           30,\n" +
                "           SUBSTRING_INDEX(survey_results_public.YearsCodingProf, '-', 1))),\n" +
                "        1) AS AvgOfYears\n" +
                "FROM\n" +
                "    (SELECT 1 n UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9 UNION ALL SELECT 10 UNION ALL SELECT 11 UNION ALL SELECT 12 UNION ALL SELECT 13 UNION ALL SELECT 14 UNION ALL SELECT 15 UNION ALL SELECT 16 UNION ALL SELECT 17) as numbers\n" +
                "        INNER JOIN survey_results_public " +
                "           ON CHAR_LENGTH(survey_results_public.DevType) - CHAR_LENGTH(REPLACE(survey_results_public.DevType, ';', '')) >= numbers.n - 1\n" +
                "WHERE\n" +
                "    survey_results_public.YearsCodingProf NOT LIKE 'NA'\n" +
                "        AND survey_results_public.DevType NOT LIKE 'NA'\n" +
                "GROUP BY DeveloperType\n" +
                "ORDER BY AvgOfYears DESC;";

        List<CodingYearsDto> result = jdbcTemplate.query(
                sql,
                rs -> new CodingYearsDto(rs.getString("DeveloperType"), rs.getDouble("AvgOfYears"))
        );

        expected.forEach(codingYearsDto -> result.contains(codingYearsDto));
    }

    private List<CodingYearsDto> makeYearsFixture() {
        return Arrays.asList(
                new CodingYearsDto("Engineering manager", 10.2),
                new CodingYearsDto("C-suite executive (CEO, CTO, etc.)", 10.1),
                new CodingYearsDto("Product manager", 8.8),
                new CodingYearsDto("DevOps specialist", 8.0),
                new CodingYearsDto("Desktop or enterprise applications developer", 7.7),
                new CodingYearsDto("Embedded applications or devices developer", 7.5),
                new CodingYearsDto("Marketing or sales professional", 7.2),
                new CodingYearsDto("Data or business analyst", 7.2),
                new CodingYearsDto("Database administrator", 6.9),
                new CodingYearsDto("QA or test developer", 5.7),
                new CodingYearsDto("Game or graphics developer", 4.6),
                new CodingYearsDto("Student", 1.2)
        );
    }
}
