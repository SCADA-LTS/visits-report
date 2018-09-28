package org.scada_lts.dao;

import org.postgresql.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @project count
 * @autor grzegorz.bylica@gmail.com on 27.09.18
 */
public class ConnectionFactory {

    public static final String URL = "jdbc:postgresql://localhost:5432/test";
    public static final String USER = "postgres";
    public static final String PASS = "password";

    public static Connection getConnection()
    {
        try {
            DriverManager.registerDriver(new Driver());
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException ex) {
            throw new RuntimeException("Error connecting to the database", ex);
        }
    }
}
