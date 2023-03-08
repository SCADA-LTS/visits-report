package org.scada_lts.dao;

import org.scada_lts.config.Configuration;
import org.scada_lts.config.DbConfig;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @project count
 * @author grzegorz.bylica@gmail.com on 03.10.18, kamiljarmusik on 03.03.23
 */
public final class JdbcTemplateFactory {

    private JdbcTemplateFactory() {}

    public static JdbcTemplate getJdbcTemplate()
    {
        try {
            String dbUrl = Configuration.getInstance().getConf().getDbUrl();
            String dbUser = Configuration.getInstance().getConf().getDbUser();
            String dbPassword = Configuration.getInstance().getConf().getDbPassword();
            String dbDriverClassName = Configuration.getInstance().getConf().getDbDriver();
            DataSource dataSource = DbConfig.dataSource(dbUrl, dbUser, dbPassword, dbDriverClassName);
            return new JdbcTemplate(dataSource);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }
}
