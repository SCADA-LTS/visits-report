package org.scada_lts.report_to_libreoffice;

import org.jopendocument.dom.spreadsheet.Sheet;
import org.scada_lts.config.Configuration;
import org.scada_lts.dao.YearlyReportDao;
import org.scada_lts.model.CountInMonth;
import org.scada_lts.utils.CalculationPositionInCalc;

import java.io.File;
import java.util.Date;
import java.util.Set;

import static org.scada_lts.report_to_libreoffice.PrintLog.error;
import static org.scada_lts.report_to_libreoffice.PrintLog.p;

/**
 * @project count
 * @author grzegorz.bylica@gmail.com on 03.10.18, kamiljarmusik on 03.03.23
 */
public class YearlyReport extends Report implements IReportType, Runnable{

    @Override
    public void createHeader(Sheet sheet) {
        try {
            p("Creating the Header");

            if (sheet == null) throw new Exception("sheet is null");

            int year = Configuration.getInstance().getConf().getYear();

            for (int i = 0; Configuration.getInstance().getConf().getLocalizations().length > i; i++) {
                insertIntoCell(
                        0,
                        37 + i,
                        Configuration.getInstance().getConf().getLocalizations()[i],
                        sheet,
                        "T"

                );
            }

            //actualize data in title

            p("date:" + Configuration.getInstance().getDate());
            int xMonth = 0;
            int yMonth = 48;

            insertIntoCell(
                    xMonth,
                    yMonth,
                    "(20" + year + ")",
                    sheet,
                    "T"

            );

        } catch (Exception e) {
            error(e.getMessage(), e);
        }

    }

    @Override
    public void insertData(Sheet sheet) {

        Set<CountInMonth[]> data = new YearlyReportDao().getAllLocation();

        p("count day in range:" + data.size());

        for (CountInMonth[] counts : data) {
            for (int i = 0; counts.length > i; i++) {

                int x = 1 + counts[i].getMonth();
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
        String dirOut = Configuration.getInstance().getConf().getTemplateOutDir();
        String newReport = dirOut + "report_" + year + "_" + new Date().getTime() + ".ods";
        File file = new File(dirOut);
        file.mkdirs();
        return newReport;
    }

    @Override
    public String getTemplatePath() {
        return Configuration.getInstance().getConf().getTemplateSourceFileYearly();
    }
}
