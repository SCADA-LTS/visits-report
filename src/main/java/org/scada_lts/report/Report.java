package org.scada_lts.report;


import org.jopendocument.dom.ODValueType;
import org.jopendocument.dom.spreadsheet.MutableCell;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.scada_lts.utils.FileUtil;

import java.io.File;

import static org.scada_lts.report.PrintLog.error;
import static org.scada_lts.report.PrintLog.p;


/**
 * @project count
 * @author grzegorz.bylica@gmail.com on 03.10.18, kamiljarmusik on 03.03.23
 */
public abstract class Report implements IReportType {

    public final void run() {
        String reportOdsPath = getReportPath();
        String templateOdsPath = getTemplatePath();
        FileUtil.getFileFromJar(templateOdsPath).ifPresent(file -> {
            try {
                SpreadSheet spreadSheet = SpreadSheet.createFromFile(file);
                Sheet sheet = spreadSheet.getSheet(0);
                createHeader(sheet);
                insertData(sheet);
                spreadSheet.saveAs(new File(reportOdsPath));
                p("done");

            } catch (Exception e) {
                error(e.getMessage(), e);
            }
        });

        System.exit(0);

    }

    protected static void insertIntoCell(int x, int y, String theValue,
                                         Sheet sheet, String flag) {


        MutableCell<SpreadSheet> cell = sheet.getCellAt(x, y);

        if (flag.equals("V")) {
            cell.setValue(Double.parseDouble(theValue), ODValueType.FLOAT, false, false);
        } else if (flag.equals("T")) {
            cell.setValue(theValue, ODValueType.STRING, false, false);
        } else {
            cell.setFormula(theValue);
        }

    }
}
