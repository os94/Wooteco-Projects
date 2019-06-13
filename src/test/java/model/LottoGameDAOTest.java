package model;

import lotto.model.dao.LottoGameDAO;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LottoGameDAOTest {
    @Test
    void connection_연결_확인() {
        LottoGameDAO lottoGameDAO = new LottoGameDAO();
        Connection connection = lottoGameDAO.getConnection();
        assertNotNull(connection);
    }
}
