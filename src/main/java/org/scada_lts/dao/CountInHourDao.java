package org.scada_lts.dao;

import org.scada_lts.config.Configuration;
import org.scada_lts.model.CountInHour;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import static org.scada_lts.report_to_libreoffice.PrintLog.*;
import static org.scada_lts.utils.DataUtils.formatPartOfDate;

/**
 * @project count
 * @author kamiljarmusik on 04.03.23
 */
public class CountInHourDao implements ICountInDao<CountInHour[]> {

    private String prepareSQL() {

        int year = Configuration.getInstance().getConf().getYear();
        int month = Configuration.getInstance().getConf().getMonth();
        int day = Configuration.getInstance().getConf().getDay();

        p("year: " + year);
        p("month: " + month);
        p("day: " + day);
        /*return  "SELECT * FROM visitors_by_day_view WHERE \"Date\" ~ '^"
                + formatPartOfDate(year) + "."
                + formatPartOfDate(month) + "'";*/
        String sql = "SELECT * FROM visitors_by_hours_view WHERE \"Date\" like '" + formatPartOfDate(year) + "."
                + formatPartOfDate(month) + "."
                + formatPartOfDate(day) +"'";

        p(sql);

        return sql;
    }

    private CountInHour[] extractCountInHourFromResultSet(ResultSet rs) throws SQLException {

        String[] localizations = Configuration.getInstance().getConf().getLocalizations();
        int countLocalizations = localizations.length;
        CountInHour[] counts = new CountInHour[countLocalizations];

        try {
            for (int i = 0; countLocalizations > i; i++) {

                CountInHour countInDay = new CountInHour();
                String strDate = rs.getString(1);
                // eg. yy.MM.dd
                String formatDate = Configuration.getInstance().getConf().getFormatDateForParseInHourReport();
                DateFormat format = new SimpleDateFormat(formatDate, Locale.ENGLISH);
                Date date = format.parse(strDate);
                countInDay.setDate(date);
                String localizationName = localizations[i];
                countInDay.setLocation(localizationName);
                countInDay.setHour(rs.getInt(2));
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
    public Set<CountInHour[]> getAllLocation() {
        JdbcTemplate jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate();
        return new HashSet<>(jdbcTemplate.query(prepareSQL(), (resultSet, i) -> extractCountInHourFromResultSet(resultSet)));
    }
}
