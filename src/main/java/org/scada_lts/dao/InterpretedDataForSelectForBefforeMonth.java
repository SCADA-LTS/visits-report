package org.scada_lts.dao;

import org.scada_lts.utils.DataUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @project count
 * @author grzegorz.bylica@gmail.com on 03.10.18, kamiljarmusik on 03.03.23
 */
public final class InterpretedDataForSelectForBefforeMonth {

    private InterpretedDataForSelectForBefforeMonth() {
    }

    public static String getYearInFormatDataBase(Calendar cal) {
        DateFormat format = new SimpleDateFormat("yy", Locale.ENGLISH);
        return format.format(DataUtils.getBeforeYear(cal));
    }

    public static String getMonthInFormatDataBase(Calendar cal) {
        DateFormat format = new SimpleDateFormat("MM", Locale.ENGLISH);
        return format.format(DataUtils.getBeforeMonth(cal));
    }

    public static String getDayInFormatDataBase(Calendar cal) {
        DateFormat format = new SimpleDateFormat("dd", Locale.ENGLISH);
        return format.format(DataUtils.getBeforeDay(cal));
    }
}
