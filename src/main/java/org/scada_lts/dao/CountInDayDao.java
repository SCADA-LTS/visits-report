package org.scada_lts.dao;

import org.scada_lts.config.Configuration;
import org.scada_lts.model.CountInDay;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @project count
 * @autor grzegorz.bylica@gmail.com on 28.09.18
 */
public class CountInDayDao implements ICountInDayDao {

    private String formatPartOfDate(int value) {
        if (value >= 10) {
            return String.valueOf(value);
        } else {
            return "0"+String.valueOf(value);
        }
    }

    private String prepareSQL() {

        int year = Configuration.getInstance().getConf().getYear();
        int month = Configuration.getInstance().getConf().getMonth();

        System.out.println(year);
        System.out.println(month);

        /*return  "SELECT * FROM visitors_by_day_view WHERE \"Date\" ~ '^"
                + formatPartOfDate(year) + "."
                + formatPartOfDate(month) + "'";*/
        String sql = "SELECT * FROM visitors_by_day_view WHERE \"Date\" like '%."
                + formatPartOfDate(month) + "."
                + formatPartOfDate(year) + "'";

        System.out.println(sql);

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
                String formatDate = Configuration.getInstance().getConf().getFormatDateForParseInDayReport();
                DateFormat format = new SimpleDateFormat(formatDate, Locale.ENGLISH);
                Date date = format.parse(strDate);
                countInDay.setDate(date);
                String localizationName = localizations[i];
                countInDay.setLocation(localizationName);
                try {
                    countInDay.setCountInLocalizations(rs.getInt(localizationName));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                counts[i] = countInDay;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return counts;

    }

    @Override
    public Set<CountInDay[]> getAllLocation() {
        Connection connection = new ConnectionFactory().getConnection();

        try {
            Statement stmt = connection.createStatement();
            String sql = prepareSQL();
            ResultSet rs = stmt.executeQuery(sql);
            Set<CountInDay[]> counts = new HashSet<CountInDay[]>();
            while(rs.next())
            {
                CountInDay[] countInDay = extractCountInDayFromResultSet(rs);
                counts.add(countInDay);
            }
            return counts;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
