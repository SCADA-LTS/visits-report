package org.scada_lts.dao;

import org.postgresql.Driver;
import org.scada_lts.config.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @project count
 * @autor grzegorz.bylica@gmail.com on 27.09.18
 */
public class ConnectionFactory {

    private final String URL = preparedURL();
    private final String USER = Configuration.getInstance().getConf().getDbuser();
    private final String PASS = Configuration.getInstance().getConf().getDbpasswd();

    private String preparedURL() {
        return "jdbc:postgresql://" +
                Configuration.getInstance().getConf().getDbHost() +
                ":" + Configuration.getInstance().getConf().getDbPort() + "/" +
                Configuration.getInstance().getConf().getDb();
    }

    public Connection getConnection()
    {
        try {
            DriverManager.registerDriver(new Driver());
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException ex) {
            throw new RuntimeException("Error connecting to the database", ex);
        }
    }
}
