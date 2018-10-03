package org.scada_lts.config;

import org.scada_lts.dao.InterpretedDataForSelectForBefforeMonth;
import org.scada_lts.utils.DataUtils;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * @project count
 * @autor grzegorz.bylica@gmail.com on 27.09.18
 */
public class Configuration {

    private static Configuration ourInstance = new Configuration();

    private static final String BEFORE = "before";

    private boolean monthBeffore = false;

    private Config conf;

    public static Configuration getInstance() {
        return ourInstance;
    }

    public Config getConf() {
        return conf;
    }

    public Date getDate() throws ParseException {
        int year = conf.getYear();
        int month = conf.getMonth();

        SimpleDateFormat sdf = new SimpleDateFormat("yy.MM.dd");

        return sdf.parse(year + "." + month + ".01");
    }

    private Configuration() {

        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream("conf.properties");

            prop.load(input);

            String dbHost = prop.getProperty("dbHost");
            String dbPort = prop.getProperty("dbPort");
            String db = prop.getProperty("db");
            String dbuser = prop.getProperty("dbuser");
            String dbpasswd = prop.getProperty("dbpassword");
            String tmpYear = prop.getProperty("year");
            String tmpMonth = prop.getProperty("month");
            String tmpLocalizations = prop.getProperty("localizations");
            String type = prop.getProperty("type");

            TypeReport tr = CheckTypeReport.getInstance().getTypeReportBaseOnStr(type);

            int month = 0;
            int year = 0;
            if (tr == TypeReport.YEARLY) {
                month = 0;
                year = getYear(tmpYear);
            } else if (tr == TypeReport.MONTHLY) {
                month = getMonth(tmpMonth);
                year = getYear(tmpYear);
            }

            String[] localizations = tmpLocalizations.split(",");

            String templateSourceFileYearly = prop.getProperty("template_source_yearly");
            String templateSourceFileMonthly = prop.getProperty("template_source_monthly");
            String templateOutDir = prop.getProperty("template_out");


            conf = new Config(
                    dbHost,
                    dbPort,
                    db,
                    dbuser,
                    dbpasswd,
                    year,
                    month,
                    localizations,
                    templateSourceFileYearly,
                    templateSourceFileMonthly,
                    templateOutDir,
                    type
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
        if (monthBeffore) {
            String tmpYear = new InterpretedDataForSelectForBefforeMonth().getYearInFormatDataBase();
            return Integer.parseInt(tmpYear);
        } else {
            return Integer.valueOf(str);
        }
    }

    private int getMonth(String str) {

        if (str.equals(BEFORE)) {
            String tmpMonth = new InterpretedDataForSelectForBefforeMonth().getMonthInFormatDataBase();
            monthBeffore = true;
            return Integer.parseInt(tmpMonth);
        } else {
            return Integer.valueOf(str);
        }
    }

}



