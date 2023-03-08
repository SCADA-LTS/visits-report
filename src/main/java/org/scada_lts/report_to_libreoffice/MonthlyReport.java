package org.scada_lts.report_to_libreoffice;


import org.jopendocument.dom.spreadsheet.*;
import org.scada_lts.config.Configuration;
import org.scada_lts.dao.CountInDayDao;
import org.scada_lts.model.CountInDay;
import org.scada_lts.utils.CalculationPositionInCalc;
import org.scada_lts.utils.DataUtils;

import java.awt.*;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

import static org.scada_lts.report_to_libreoffice.PrintLog.error;
import static org.scada_lts.report_to_libreoffice.PrintLog.p;

/**
 * @project count
 * @author grzegorz.bylica@gmail.com on 03.10.18, kamiljarmusik on 03.03.23
 */
public class MonthlyReport extends Report implements Runnable, IReportType {

    @Override
    public void createHeader(Sheet sheet) {
        try {
            p("Creating the Header");

            if (sheet == null)
                throw new Exception("xSheet is null");

            setLocalizationName(sheet);
            setDayName(sheet);
            setTitle(sheet);

        } catch (Exception e) {
            error(e.getMessage(), e);
        }
    }

    @Override
    public void insertData(Sheet sheet) {

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
                        sheet, "V");

            }
        }
    }

    @Override
    public String getReportPath() {
        int year = Configuration.getInstance().getConf().getYear();
        int month = Configuration.getInstance().getConf().getMonth();
        String dirOut = Configuration.getInstance().getConf().getTemplateOutDir();
        String newReport = dirOut + "report_" + year + "_" + month + "_" + new Date().getTime() + ".ods";
        File file = new File(dirOut);
        file.mkdirs();
        return newReport;
    }

    @Override
    public String getTemplatePath() {
        return Configuration.getInstance().getConf().getTemplateSourceFileMonthly();
    }

    private void setTitle(Sheet sheet) throws ParseException {
        //actualize data in title
        String monthInFrance = new SimpleDateFormat("MMMM", Locale.FRANCE).format(Configuration.getInstance().getDate());
        int year = Configuration.getInstance().getConf().getYear();

        p("date:" + Configuration.getInstance().getDate());
        int xMonth = 0;
        int yMonth = 48;

        insertIntoCell(
                xMonth,
                yMonth,
                "(" + monthInFrance + " 20" + year + ")",
                sheet,
                "T"

        );
    }

    private void setDayName(Sheet sheet) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yy.MM.dd");

        int year = Configuration.getInstance().getConf().getYear();
        int month = Configuration.getInstance().getConf().getMonth();


        Date startDate = sdf.parse(year + "." + month + ".01");
        Date endDate = DataUtils.getLastDayOfMonth(startDate);

        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);

        for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {

            String dayOfWeek = new SimpleDateFormat("EEEE", Locale.FRANCE).format(date);
            String firsLetterNameDay = Character.toString(dayOfWeek.toUpperCase().charAt(0));
            int x = CalculationPositionInCalc.getInstance().getLeftPosition(0, date);
            int y = 35;

            insertIntoCell(
                    x,
                    y,
                    firsLetterNameDay,
                    sheet,
                    "T"

            );

            boolean isSunday = (DataUtils.getDay(date) == 0);
            boolean isMonday = (DataUtils.getDay(date) == 1);

            for (int i = 0; 6 > i; i++) {
                if (isMonday || isSunday) {
                    MutableCell<SpreadSheet> cell = sheet.getCellAt(x,37 + i);
                    cell.setBackgroundColor(Color.BLUE);
                }
            }
        }
    }

    private void setLocalizationName(Sheet sheet) {
        for (int i = 0; Configuration.getInstance().getConf().getLocalizations().length > i; i++) {
            insertIntoCell(
                    0,
                    37 + i,
                    Configuration.getInstance().getConf().getLocalizations()[i],
                    sheet,
                    "T"

            );
        }
    }
}
