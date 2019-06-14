package model;

import lotto.model.dao.DBManager;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DBManagerTest {
    @Test
    void connection_연결_확인() {
        Connection connection = DBManager.getConnection();
        assertNotNull(connection);
    }
}
