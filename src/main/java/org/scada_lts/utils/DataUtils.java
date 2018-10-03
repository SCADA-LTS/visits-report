package org.scada_lts.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * @project count
 * @autor grzegorz.bylica@gmail.com on 01.10.18
 */
public class DataUtils {
    private static DataUtils ourInstance = new DataUtils();

    public static DataUtils getInstance() {
        return ourInstance;
    }

    private DataUtils() {
    }

    public int getDayOfMonth(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public Date getFirstDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH,1);
        return cal.getTime();
    }

    public Date getLastDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }



    public Date getBefforeMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        return cal.getTime();
    }

    public int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH);
    }

    public int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }


    public int getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK) -1;
    }

}
