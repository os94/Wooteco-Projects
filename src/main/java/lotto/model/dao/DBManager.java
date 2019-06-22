package lotto.model.dao;

import java.sql.*;

public class DBManager {
    private static Connection connection;

    private DBManager() {
    }

    public static Connection getConnection() {
        if (connection != null) {
            return connection;
        }

        String server = "localhost:3306";
        String database = "lotto";
        String userName = "sean";
        String password = "1234";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver load 오류 : " + e.getMessage());
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + server + "/" + database
                    + "?serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false", userName, password);
            System.out.println("정상적으로 연결되었습니다.");
        } catch (SQLException e) {
            System.err.println("연결 오류 : " + e.getMessage());
            e.printStackTrace();
        }

        return connection;
    }

    public static void close(PreparedStatement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            System.err.println("close 오류 : " + e.getMessage());
        }
    }
}
