package org.scada_lts.report_to_libreoffice;

import com.sun.star.beans.PropertyValue;
import com.sun.star.container.XIndexAccess;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XStorable;
import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.sheet.XCellSeries;
import com.sun.star.sheet.XSpreadsheet;
import com.sun.star.sheet.XSpreadsheetDocument;
import com.sun.star.sheet.XSpreadsheets;
import com.sun.star.table.XCell;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
import org.scada_lts.config.Configuration;
import org.scada_lts.config.TypeReport;

import java.util.Date;

/**
 * @project count
 * @autor grzegorz.bylica@gmail.com on 02.10.18
 */
public class Report extends Log {

    protected XComponentContext xContext = null;

    protected static XCellSeries getCellSeries(
            XSpreadsheet xSheet, String aRange) {
        return UnoRuntime.queryInterface(
                XCellSeries.class, xSheet.getCellRangeByName(aRange));
    }

    private static XComponent xComp = null;

    protected XSpreadsheet xSheet = null;

    protected void connect() {

        try {
            xContext = com.sun.star.comp.helper.Bootstrap.bootstrap();
            p("Connected to a running office ...");
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.exit(1);
        }

    }

    protected void getSpreadSheet(XSpreadsheetDocument doc ) {
        try {
            p("Getting spreadsheet");
            XSpreadsheets xSheets = doc.getSheets();
            XIndexAccess oIndexSheets = UnoRuntime.queryInterface(
                    XIndexAccess.class, xSheets);
            xSheet = UnoRuntime.queryInterface(
                    XSpreadsheet.class, oIndexSheets.getByIndex(0));

        } catch (Exception e) {
            p("Couldn't get Sheet " + e);
            e.printStackTrace(System.err);
        }
    }


    protected static XSpreadsheetDocument openCalc(XComponentContext xContext) {

        XComponentLoader xCLoader;
        XSpreadsheetDocument xSpreadSheetDoc = null;

        try {
            XMultiComponentFactory xMCF = xContext.getServiceManager();

            Object oDesktop = xMCF.createInstanceWithContext(
                    "com.sun.star.frame.Desktop", xContext);

            xCLoader = UnoRuntime.queryInterface(
                    XComponentLoader.class, oDesktop);

            PropertyValue[] szEmptyArgs = new PropertyValue[0];

            TypeReport tr = Configuration.getInstance().getConf().getTypeReport();

            String strDoc = "file:";

            if (tr == TypeReport.YEARLY) {
                strDoc = strDoc + Configuration.getInstance().getConf().getTemplateSourceFileYearly();
            } else if (tr == TypeReport.MONTHLY) {
                strDoc = strDoc + Configuration.getInstance().getConf().getTemplateSourceFileMonthly();
            } else {
                new RuntimeException("ERROR we don't have type of report");
            }

            xComp = xCLoader.loadComponentFromURL(strDoc, "_blank", 0, szEmptyArgs);
            xSpreadSheetDoc = UnoRuntime.queryInterface(
                    XSpreadsheetDocument.class, xComp);

        } catch (Exception e) {
            System.err.println(" Exception " + e);
            e.printStackTrace(System.err);
        }

        return xSpreadSheetDoc;
    }

    protected static void insertIntoCell(int CellX, int CellY, String theValue,
                                       XSpreadsheet TT1, String flag) {
        XCell xCell = null;

        try {
            xCell = TT1.getCellByPosition(CellX, CellY);
        } catch (com.sun.star.lang.IndexOutOfBoundsException ex) {
            p("Could not get Cell");
            ex.printStackTrace(System.err);
        }

        if (flag.equals("V")) {
            xCell.setValue((new Float(theValue)).floatValue());
        } else if (flag.equals("T")) {
            com.sun.star.text.XText xText = UnoRuntime.queryInterface(com.sun.star.text.XText.class, xCell);
            xText.setString(theValue);
        } else {
            xCell.setFormula(theValue);
        }

    }

    protected void save(XSpreadsheetDocument doc) {
        try {
            XStorable xStorable = UnoRuntime
                    .queryInterface(XStorable.class, doc);

            PropertyValue[] propertyValues = new PropertyValue[2];
            propertyValues[0] = new PropertyValue();
            propertyValues[0].Name = "Overwrite";
            propertyValues[0].Value = new Boolean(true);
            propertyValues[1] = new PropertyValue();

            int year = Configuration.getInstance().getConf().getYear();
            int month = Configuration.getInstance().getConf().getMonth();


            String dirOut = Configuration.getInstance().getConf().getTemplateOutDir();
            String newReport = dirOut + "report_" + year + "_" + month + "_" + new Date().getTime() + ".ods";
            xStorable.storeToURL("file:///" + newReport, propertyValues);

            p("Saved " + newReport);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            xComp.dispose();
        }

    }
}
