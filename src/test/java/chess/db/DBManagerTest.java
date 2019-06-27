package chess.db;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.assertj.core.api.Assertions.assertThat;

public class DBManagerTest {
    @Test
    void connection_가져오기() {
        assertThat(DBManager.getConnection()).isInstanceOf(Connection.class);
    }
}
