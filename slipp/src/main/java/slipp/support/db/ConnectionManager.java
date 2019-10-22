package slipp.support.db;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {

    public static DataSource getDataSource() {
        Properties properties = readProperties();
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(properties.getProperty("jdbc.driverClass"));
        ds.setUrl(properties.getProperty("jdbc.url"));
        ds.setUsername(properties.getProperty("jdbc.username"));
        ds.setPassword(properties.getProperty("jdbc.password"));
        return ds;
    }

    private static Properties readProperties() {
        Properties properties = new Properties();
        String resource = "src/main/resources/db.properties";
        try {
            properties.load(new FileReader(resource));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static Connection getConnection() {
        try {
            return getDataSource().getConnection();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
