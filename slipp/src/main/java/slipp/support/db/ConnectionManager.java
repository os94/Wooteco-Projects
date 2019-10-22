package slipp.support.db;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {

    public static final String DEFAULT_PROPERTIES_PATH = "src/main/resources/db.properties";

    public static DataSource getDataSource() {
        return getDataSource(DEFAULT_PROPERTIES_PATH);
    }

    public static DataSource getDataSource(String directory) {
        Properties properties = readProperties(directory);
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(properties.getProperty("jdbc.driverClass"));
        ds.setUrl(properties.getProperty("jdbc.url"));
        ds.setUsername(properties.getProperty("jdbc.username"));
        ds.setPassword(properties.getProperty("jdbc.password"));
        return ds;
    }

    private static Properties readProperties(String directory) {
        Properties properties = new Properties();
        String resource = directory;
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
