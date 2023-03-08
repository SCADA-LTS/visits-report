package org.scada_lts.utils;

import org.scada_lts.config.TypeReport;
import java.util.Calendar;

public class BeforeDate {

    private final int day;
    private final int month;
    private final int year;
    private final boolean before;
    private final TypeReport typeReport;

    public BeforeDate(String tmpDay, String tmpMonth, String tmpYear, TypeReport typeReport, boolean before) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(tmpYear), Integer.parseInt(tmpMonth) - 1, Integer.parseInt(tmpDay));
        if(before) {
            switch (typeReport) {
                case YEARLY:
                    calendar.add(Calendar.YEAR, -1);
                    break;
                case DAILY:
                    calendar.add(Calendar.DAY_OF_MONTH, -1);
                    break;
                default:
                    calendar.add(Calendar.MONTH, -1);
                    break;
            }
        }
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        this.month = calendar.get(Calendar.MONTH);
        this.year = calendar.get(Calendar.YEAR);
        this.before = before;
        this.typeReport = typeReport;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month + 1;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return "BeforeDate{" +
                "day=" + day +
                ", month=" + month +
                ", year=" + year +
                ", before=" + before +
                ", typeReport=" + typeReport +
                '}';
    }
}
