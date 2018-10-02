package org.scada_lts.report_to_libreoffice;

import com.sun.star.beans.XPropertySet;
import com.sun.star.sheet.XSpreadsheetDocument;
import com.sun.star.table.XCell;
import com.sun.star.uno.UnoRuntime;
import org.scada_lts.config.Configuration;
import org.scada_lts.dao.CountInDayDao;
import org.scada_lts.model.CountInDay;
import org.scada_lts.utils.CalculationPositionInCalc;
import org.scada_lts.utils.DataUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

/**
 * @project count
 * @autor grzegorz.bylica@gmail.com on 02.10.18
 */
public class YearlyReport extends Report implements IReportType, Runnable{
    @Override
    public void run() {
        try {

            connect();

            XSpreadsheetDocument doc = openCalc(xContext);

            getSpreadSheet(doc);

            createHeader();

            insertData();

            save(doc);


        } catch (Exception e) {
            e.printStackTrace();
        }

        System.exit(0);
    }

    @Override
    public void createHeader() {
        try {
            p("Creating the Header");


            SimpleDateFormat sdf = new SimpleDateFormat("yy.MM.dd");

            int year = Configuration.getInstance().getConf().getYear();

            if (xSheet == null) new Exception("xSheet is null");

            for (int i = 0; Configuration.getInstance().getConf().getLocalizations().length > i; i++) {
                insertIntoCell(
                        0,
                        37 + i,
                        Configuration.getInstance().getConf().getLocalizations()[i],
                        xSheet,
                        "T"

                );
            }

            //actualize data in title

            p("date:" + Configuration.getInstance().getDate());
            p("year:" + year);
            int xMonth = 0;
            int yMonth = 48;

            insertIntoCell(
                    xMonth,
                    yMonth,
                    "(" + year + ")",
                    xSheet,
                    "T"

            );

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void insertData() {

        Set<CountInDay[]> data = new CountInDayDao().getAllLocation();

        p("count day in range:" + data.size());

        for (CountInDay[] counts : data) {
            for (int i = 0; counts.length > i; i++) {

                int x = CalculationPositionInCalc.getInstance().getLeftPosition(0, counts[i].getDate());
                int y = CalculationPositionInCalc.getInstance().getTopPosition(37, i);


                p(counts[i].toString());

                insertIntoCell(
                        x,
                        y,
                        String.valueOf(counts[i].getCountInLocalizations()),
                        xSheet, "");

            }
        }

    }
}
