package org.scada_lts.report_to_libreoffice;

import org.jopendocument.dom.spreadsheet.MutableCell;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.scada_lts.config.Configuration;
import org.scada_lts.dao.DailyReportDao;
import org.scada_lts.model.CountInHour;
import org.scada_lts.utils.CalculationPositionInCalc;

import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

import static org.scada_lts.report_to_libreoffice.PrintLog.error;
import static org.scada_lts.report_to_libreoffice.PrintLog.p;

/**
 * @project count
 * @author kamiljarmusik on 03.03.23
 */
public class DailyReport extends Report implements IReportType, Runnable{

    @Override
    public void createHeader(Sheet sheet) {
        try {
            p("Creating the Header");

            if (sheet == null) throw new Exception("sheet is null");

            String monthInFrance = new SimpleDateFormat("MMMM", Locale.FRANCE).format(Configuration.getInstance().getDate());
            int year = Configuration.getInstance().getConf().getYear();
            int day = Configuration.getInstance().getConf().getDay();

            for (int i = 0; Configuration.getInstance().getConf().getLocalizations().length > i; i++) {
                insertIntoCell(
                        0,
                        37 + i,
                        Configuration.getInstance().getConf().getLocalizations()[i],
                        sheet,
                        "T"

                );
            }


            for (int x = 1; x < 25; x++) {
                for (int i = 0; 6 > i; i++) {
                    if (x < 8 || x > 15) {
                        MutableCell<SpreadSheet> cell = sheet.getCellAt(x, 37 + i);
                        cell.setBackgroundColor(Color.BLUE);
                    }
                }
            }


            //actualize data in title

            p("date:" + Configuration.getInstance().getDate());
            int xMonth = 0;
            int yMonth = 48;

            insertIntoCell(
                    xMonth,
                    yMonth,
                    "(" + day + " " + monthInFrance + " 20" + year + ")",
                    sheet,
                    "T"

            );

        } catch (Exception e) {
            error(e.getMessage(), e);
        }

    }

    @Override
    public void insertData(Sheet sheet) {

        Set<CountInHour[]> data = new DailyReportDao().getAllLocation();

        p("count day in range:" + data.size());

        for (CountInHour[] counts : data) {
            for (int i = 0; counts.length > i; i++) {

                int x = counts[i].getHour();
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
        int day = Configuration.getInstance().getConf().getDay();
        String dirOut = Configuration.getInstance().getConf().getTemplateOutDir();
        String newReport = dirOut + "report_" + year + "_" + month + "_" + day + "_" + new Date().getTime() + ".ods";
        File file = new File(dirOut);
        file.mkdirs();
        return newReport;
    }

    @Override
    public String getTemplatePath() {
        return Configuration.getInstance().getConf().getTemplateSourceFileDaily();
    }
}
