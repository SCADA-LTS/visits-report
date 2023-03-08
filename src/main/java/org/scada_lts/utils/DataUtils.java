package org.scada_lts.utils;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * @project count
 * @author grzegorz.bylica@gmail.com on 01.10.18, kamiljarmusik on 03.03.23
 */
public final class DataUtils {

    private DataUtils() {
    }

    public static int getDayOfMonth(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static Date getFirstDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH,1);
        return cal.getTime();
    }

    public static Date getLastDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    public static Date getBeforeMonth(Calendar cal) {
        cal.add(Calendar.MONTH, -1);
        return cal.getTime();
    }

    public static Date getBeforeDay(Calendar cal) {
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    public static Date getBeforeYear(Calendar cal) {
        cal.add(Calendar.YEAR, -1);
        return cal.getTime();
    }

    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH);
    }

    public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }


    public static int getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK) -1;
    }

    public static int getHourOfDay(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    public static String formatPartOfDate(int value) {
        if (value >= 10) {
            return String.valueOf(value);
        } else {
            return "0"+String.valueOf(value);
        }
    }
}
