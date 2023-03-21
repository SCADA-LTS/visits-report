package org.scada_lts.dao;

import org.scada_lts.config.Configuration;
import org.scada_lts.model.CountInDay;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.scada_lts.report.PrintLog.*;
import static org.scada_lts.utils.DataUtils.formatPartOfDate;

/**
 * @project count
 * @author grzegorz.bylica@gmail.com on 03.10.18, kamiljarmusik on 03.03.23
 */
public class MonthlyReportDao implements IReportDao<CountInDay[]> {

    private String prepareSQL() {

        int year = Configuration.getInstance().getConf().getYear();
        int month = Configuration.getInstance().getConf().getMonth();

        p("year: " + year);
        p("month: " + month);

        /*return  "SELECT * FROM visitors_by_day_view WHERE \"Date\" ~ '^"
                + formatPartOfDate(year) + "."
                + formatPartOfDate(month) + "'";*/
        String sql = "SELECT * FROM visitors_by_day_view WHERE \"Date\" like '%."
                + formatPartOfDate(month) + "."
                + formatPartOfDate(year) + "'";

        p(sql);

        return sql;
    }

    private CountInDay[] extractCountInDayFromResultSet(ResultSet rs) throws SQLException {

        String[] localizations = Configuration.getInstance().getConf().getLocalizations();
        int countLocalizations = localizations.length;
        CountInDay[] counts = new CountInDay[countLocalizations];

        try {
            for (int i = 0; countLocalizations > i; i++) {

                CountInDay countInDay = new CountInDay();
                String strDate = rs.getString(1);
                // eg. yy.MM.dd
                String formatDate = Configuration.getInstance().getConf().getFormatDateForParseInMonthlyReport();
                DateFormat format = new SimpleDateFormat(formatDate, Locale.ENGLISH);
                Date date = format.parse(strDate);
                countInDay.setDate(date);
                String localizationName = localizations[i];
                countInDay.setLocation(localizationName);
                try {
                    countInDay.setCountInLocalizations(rs.getInt(localizationName));
                } catch (Exception e) {
                    warn(e.getMessage());
                }

                counts[i] = countInDay;
            }
        } catch (Exception e) {
            error(e.getMessage(), e);
        }

        return counts;

    }

    @Override
    public Set<CountInDay[]> getAllLocation() {
        JdbcTemplate jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate();
        return new HashSet<>(jdbcTemplate.query(prepareSQL(), (resultSet, i) -> extractCountInDayFromResultSet(resultSet)));
    }
}
