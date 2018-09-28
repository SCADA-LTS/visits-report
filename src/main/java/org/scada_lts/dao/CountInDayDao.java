package org.scada_lts.dao;

import org.scada_lts.config.Config;
import org.scada_lts.config.Configuration;
import org.scada_lts.model.CountInDay;

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
 * @autor grzegorz.bylica@gmail.com on 28.09.18
 */
public class CountInDayDao implements ICountInDayDao {

    private static final String SELECT = "SELECT * FROM visitors_by_day_view order by 1";

    private CountInDay[] extractCountInDayFromResultSet(ResultSet rs) throws SQLException {

        String[] localizations = Configuration.getInstance().getConf().getLocalizations();
        int countLocalizations = localizations.length;
        CountInDay[] counts = new CountInDay[countLocalizations];

        try {
            for (int i = 0; countLocalizations > i; i++) {

                CountInDay countInDay = new CountInDay();
                String strDate = rs.getString(1);
                DateFormat format = new SimpleDateFormat("yy.MM.dd", Locale.ENGLISH);
                Date date = format.parse(strDate);
                countInDay.setDate(date);
                String localizationName = localizations[i];
                countInDay.setLocation(localizationName);
                countInDay.setCountInLocalizations(rs.getInt(localizationName));

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
            ResultSet rs = stmt.executeQuery(SELECT);
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
