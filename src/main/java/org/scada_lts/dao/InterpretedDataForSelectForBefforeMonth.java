package org.scada_lts.dao;

import org.scada_lts.utils.DataUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @project count
 * @autor grzegorz.bylica@gmail.com on 02.10.18
 */
public class InterpretedDataForSelectForBefforeMonth {

    private Date befforeMonthData;

    public InterpretedDataForSelectForBefforeMonth() {
        befforeMonthData = DataUtils.getInstance().getBefforeMonth();
    }

    public String getYearInFormatDataBase() {
        DateFormat format = new SimpleDateFormat("yy", Locale.ENGLISH);
        String yearName = format.format(befforeMonthData);
        return yearName;
    }

    public String getMonthInFormatDataBase() {
        DateFormat format = new SimpleDateFormat("MM", Locale.ENGLISH);
        String monthName = format.format(befforeMonthData);
        return monthName;
    }

}
