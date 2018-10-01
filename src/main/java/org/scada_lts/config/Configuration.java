package org.scada_lts.config;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

/**
 * @project count
 * @autor grzegorz.bylica@gmail.com on 27.09.18
 */
public class Configuration {

    private static Configuration ourInstance = new Configuration();

    private static final String CURRENT = "current";

    private Config conf;

    public static Configuration getInstance() {
        return ourInstance;
    }

    public Config getConf() {
        return conf;
    }

    private Configuration() {

        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream("conf.properties");

            prop.load(input);

            String dbHost = prop.getProperty("dbHost");
            String db = prop.getProperty("db");
            String dbuser = prop.getProperty("dbuser");
            String dbpasswd = prop.getProperty("dbpassword");
            String tmpYear = prop.getProperty("year");
            String tmpMonth = prop.getProperty("month");
            String tmpLocalizations = prop.getProperty("localizations");
            int year = getYear(tmpYear);
            int month = getMonth(tmpMonth);
            String[] localizations = tmpLocalizations.split(",");

            String templateSourceFile = prop.getProperty("template_source");
            String templateOutDir = prop.getProperty("template_out");


            conf = new Config(
                    dbHost,
                    db,
                    dbuser,
                    dbpasswd,
                    year,
                    month,
                    localizations,
                    templateSourceFile,
                    templateOutDir
            );

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private int getYear(String str) {
        if (str.equals(CURRENT)) {
          return new Date().getYear();
        } else {
          return Integer.valueOf(str);
        }
    }

    private int getMonth(String str) {
        if (str.equals(CURRENT)) {
            return new Date().getMonth();
        } else {
            return Integer.valueOf(str);
        }
    }

}



