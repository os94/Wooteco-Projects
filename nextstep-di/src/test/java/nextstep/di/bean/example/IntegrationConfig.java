package nextstep.di.bean.example;

import nextstep.annotation.Bean;
import nextstep.annotation.ComponentScan;
import nextstep.annotation.Configuration;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "di.bean.example")
public class IntegrationConfig {
    @Bean
    public DataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("org.h2.Driver");
        ds.setUrl("\"jdbc:h2:~/jwp-framework;MVCC=TRUE;DB_CLOSE_ON_EXIT=FALSE\"");
        ds.setUsername("sa");
        ds.setPassword("");
        return ds;
    }

    @Bean
    public MyJdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new MyJdbcTemplate(dataSource);
    }
}
