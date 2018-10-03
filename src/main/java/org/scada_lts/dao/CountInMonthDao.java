package org.scada_lts.dao;

import org.scada_lts.config.Configuration;
import org.scada_lts.model.CountInMonth;
import org.scada_lts.utils.DataUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * @project count
 * @autor grzegorz.bylica@gmail.com on 02.10.18
 */
public class CountInMonthDao implements ICountInMonthDao {

    @Override
    public Set<CountInMonth[]> getAllLocation() {
        Connection connection = new ConnectionFactory().getConnection();

        try {
            Statement stmt = connection.createStatement();
            String sql = prepareSQL();
            ResultSet rs = stmt.executeQuery(sql);
            Set<CountInMonth[]> counts = new HashSet<CountInMonth[]>();
            while(rs.next())
            {
                CountInMonth[] countInMonth = extractCountInMonthFromResultSet(rs);
                counts.add(countInMonth);
            }
            return counts;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

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

        return "SELECT * FROM visitors_by_month_view WHERE \"date\" ~ '^"
                + formatPartOfDate(year) + "'";
    }

    private CountInMonth[] extractCountInMonthFromResultSet(ResultSet rs) throws SQLException {

        String[] localizations = Configuration.getInstance().getConf().getLocalizations();
        int countLocalizations = localizations.length;
        CountInMonth[] counts = new CountInMonth[countLocalizations];

        try {
            for (int i = 0; countLocalizations > i; i++) {

                CountInMonth countInMonth = new CountInMonth();
                String strDate = rs.getString(1);
                DateFormat format = new SimpleDateFormat("yy.MM", Locale.ENGLISH);
                Date date = format.parse(strDate);
                int month = DataUtils.getInstance().getMonth(date);
                countInMonth.setMonth( month );
                String localizationName = localizations[i];
                countInMonth.setLocation(localizationName);
                try {
                    countInMonth.setCountInLocalizations(rs.getInt(localizationName));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                counts[i] = countInMonth;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return counts;

    }


}
