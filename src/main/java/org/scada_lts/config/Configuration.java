package org.scada_lts.config;

import org.scada_lts.utils.BeforeDate;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import static org.scada_lts.report_to_libreoffice.PrintLog.error;

/**
 * @project count
 * @author grzegorz.bylica@gmail.com on 03.10.18, kamiljarmusik on 03.03.23
 */
public class Configuration {

    private static Configuration ourInstance = new Configuration();

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

        try(InputStream input = new FileInputStream("conf.properties")) {

            prop.load(input);

            String dbUrl = prop.getProperty("report.db.url");
            String dbDriver = prop.getProperty("report.db.driver");
            String dbuser = prop.getProperty("report.db.user");
            String dbpasswd = prop.getProperty("report.db.password");
            String tmpYear = prop.getProperty("report.year");
            String tmpMonth = prop.getProperty("report.month");
            String tmpDay = prop.getProperty("report.day");
            String tmpBefore = prop.getProperty("report.before");

            String tmpLocalizations = prop.getProperty("report.localizations");
            String type = prop.getProperty("report.type");

            String formatDateForParseInYearlyReport = prop.getProperty("report.formatDateForParseInYearlyReport");
            String formatDateForParseInMonthlyReport = prop.getProperty("report.formatDateForParseInMonthlyReport");
            String formatDateForParseInDailyReport = prop.getProperty("report.formatDateForParseInDailyReport");


            TypeReport tr = type == null ? TypeReport.MONTHLY : TypeReport.typeOf(type);

            BeforeDate beforeDate = new BeforeDate(tmpDay, tmpMonth, tmpYear, tr, Boolean.parseBoolean(tmpBefore));

            String[] localizations = tmpLocalizations.split(",");

            String templateSourceFileYearly = prop.getProperty("report.template_source_yearly");
            String templateSourceFileMonthly = prop.getProperty("report.template_source_monthly");
            String templateSourceFileDaily = prop.getProperty("report.template_source_daily");
            String templateOutDir = prop.getProperty("report.out");


            conf = new Config(
                    dbUrl,
                    dbDriver,
                    dbuser,
                    dbpasswd,
                    beforeDate.getYear(),
                    beforeDate.getMonth(),
                    beforeDate.getDay(),
                    localizations,
                    templateSourceFileYearly,
                    templateSourceFileMonthly,
                    templateSourceFileDaily,
                    templateOutDir,
                    tr.toString(),
                    formatDateForParseInYearlyReport,
                    formatDateForParseInMonthlyReport,
                    formatDateForParseInDailyReport
            );


        } catch (Exception e) {
            error(e.getMessage(), e);
        }
    }


}



