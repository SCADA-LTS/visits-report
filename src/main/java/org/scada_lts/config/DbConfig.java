package org.scada_lts.config;

import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.sql.Driver;
import java.util.Properties;


/**
 * @project count
 * @author kamiljarmusik
 */
public final class DbConfig {

    private DbConfig() {}

    public static DataSource dataSource(String dbUrl, String dbUser, String dbPass, String dbDriverClassName) {
        try {
            Class<? extends Driver> clazz = (Class<? extends Driver>) Class.forName(dbDriverClassName);
            SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
            dataSource.setConnectionProperties(DbConfig.pass(dbUser, dbPass));
            dataSource.setDriverClass(clazz);
            dataSource.setUrl(dbUrl);
            return dataSource;
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    private static Properties pass(String user, String pass) {
        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", pass);
        properties.setProperty("sslmode", "disable");
        return properties;
    }
}
