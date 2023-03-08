package org.scada_lts.dao;

import org.scada_lts.config.Configuration;
import org.scada_lts.model.CountInDay;
import org.scada_lts.model.CountInMonth;
import org.scada_lts.utils.DataUtils;
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
 * @author grzegorz.bylica@gmail.com on 03.10.18, kamiljarmusik on 03.03.23
 */
public class CountInMonthDao implements ICountInDao<CountInMonth[]> {

    @Override
    public Set<CountInMonth[]> getAllLocation() {
        JdbcTemplate jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate();
        return new HashSet<>(jdbcTemplate.query(prepareSQL(), (resultSet, i) -> extractCountInMonthFromResultSet(resultSet)));
    }

    private String prepareSQL() {

        int year = Configuration.getInstance().getConf().getYear();

        p("year: " + year);

        String sql = "SELECT * FROM visitors_by_month_view WHERE \"date\" like '"
                + formatPartOfDate(year) + ".%'";

        p(sql);

        return sql;
    }

    private CountInMonth[] extractCountInMonthFromResultSet(ResultSet rs) throws SQLException {

        String[] localizations = Configuration.getInstance().getConf().getLocalizations();
        int countLocalizations = localizations.length;
        CountInMonth[] counts = new CountInMonth[countLocalizations];

        try {
            for (int i = 0; countLocalizations > i; i++) {

                CountInMonth countInMonth = new CountInMonth();
                String strDate = rs.getString(1);
                // "yy.MM"
                String formtDate = Configuration.getInstance().getConf().getFormatDateForParseInMonthlyReport();
                DateFormat format = new SimpleDateFormat("yy.MM", Locale.ENGLISH);
                Date date = format.parse(strDate);
                int month = DataUtils.getMonth(date);
                countInMonth.setMonth( month );
                String localizationName = localizations[i];
                countInMonth.setLocation(localizationName);
                try {
                    countInMonth.setCountInLocalizations(rs.getInt(localizationName));
                } catch (Exception e) {
                    warn(e.getMessage());
                }

                counts[i] = countInMonth;
            }
        } catch (Exception e) {
            error(e.getMessage(), e);
        }

        return counts;

    }


}
