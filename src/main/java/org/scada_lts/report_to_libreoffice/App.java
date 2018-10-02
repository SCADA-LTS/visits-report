package org.scada_lts.report_to_libreoffice;


import com.sun.star.beans.PropertyValue;
import com.sun.star.beans.XPropertySet;

import com.sun.star.container.XIndexAccess;

import com.sun.star.frame.XComponentLoader;

import com.sun.star.frame.XStorable;
import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiComponentFactory;

import com.sun.star.sheet.*;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;

import com.sun.star.table.XCell;
import org.scada_lts.config.Configuration;
import org.scada_lts.dao.CountInDayDao;
import org.scada_lts.model.CountInDay;
import org.scada_lts.utils.CalculationPositionInCalc;
import org.scada_lts.utils.DataUtils;


import java.text.SimpleDateFormat;
import java.util.*;

public class App {

    private static XCellSeries getCellSeries(
            XSpreadsheet xSheet, String aRange) {
        return UnoRuntime.queryInterface(
                XCellSeries.class, xSheet.getCellRangeByName(aRange));
    }

    private static XComponent xComp = null;

    private static void p(String str) {
        System.out.println(str);
    }

    public static void main(String[] args) throws Exception {

        try {
            p("Start report to Libre Office");

            p(Configuration.getInstance().getConf().toString());

            XComponentContext xContext = null;

            try {
                xContext = com.sun.star.comp.helper.Bootstrap.bootstrap();
                p("Connected to a running office ...");
            } catch (Exception e) {
                e.printStackTrace(System.err);
                System.exit(1);
            }

            XSpreadsheetDocument myDoc = openCalc(xContext);

            XSpreadsheet xSheet = null;

            try {
                p("Getting spreadsheet");
                XSpreadsheets xSheets = myDoc.getSheets();
                XIndexAccess oIndexSheets = UnoRuntime.queryInterface(
                        XIndexAccess.class, xSheets);
                xSheet = UnoRuntime.queryInterface(
                        XSpreadsheet.class, oIndexSheets.getByIndex(0));

            } catch (Exception e) {
                p("Couldn't get Sheet " + e);
                e.printStackTrace(System.err);
            }

            p("Creating the Header");

            int year = Configuration.getInstance().getConf().getYear();
            int month = Configuration.getInstance().getConf().getMonth();
            SimpleDateFormat sdf = new SimpleDateFormat("yy.MM.dd");

            Date startDate = sdf.parse(year + "." + month + ".01");
            Date endDate = DataUtils.getInstance().getLastDayOfMonth(startDate);

            Calendar start = Calendar.getInstance();
            start.setTime(startDate);
            Calendar end = Calendar.getInstance();
            end.setTime(endDate);

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

            for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {

                String dayOfWeek = new SimpleDateFormat("EEEE", Locale.FRANCE).format(date);
                String firsLetterNameDay = Character.toString(dayOfWeek.toUpperCase().charAt(0));
                int x = CalculationPositionInCalc.getInstance().getLeftPosition(0, date);
                int y = 35;

                insertIntoCell(
                        x,
                        y,
                        firsLetterNameDay,
                        xSheet,
                        "T"

                );

                boolean isSunday = (DataUtils.getInstance().getDay(date) == 0);
                boolean isMonday = (DataUtils.getInstance().getDay(date) == 1);

                for (int i = 0; 6 > i; i++) {
                    if (isMonday || isSunday) {

                        XCell xCell = xSheet.getCellByPosition(x, 37 + i);

                        XPropertySet xPropSet = UnoRuntime.queryInterface(com.sun.star.beans.XPropertySet.class, xCell);
                        xPropSet.setPropertyValue("CharColor", Integer.valueOf(0x003399));
                        xPropSet.setPropertyValue("IsCellBackgroundTransparent", Boolean.FALSE);
                        xPropSet.setPropertyValue("CellBackColor", Integer.valueOf(0x99CCFF));
                    }
                }
            }

            //actualize data in title

            String monthInFrance = new SimpleDateFormat("MMMM", Locale.FRANCE).format(Configuration.getInstance().getDate());
            p("date:" + Configuration.getInstance().getDate());
            p("month:" + monthInFrance);
            int xMonth = 0;
            int yMonth = 48;

            insertIntoCell(
                    xMonth,
                    yMonth,
                    "(" + monthInFrance + ")",
                    xSheet,
                    "T"

            );


            // get data from database;
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

            XStorable xStorable = UnoRuntime
                    .queryInterface(XStorable.class, myDoc);

            PropertyValue[] propertyValues = new PropertyValue[2];
            propertyValues[0] = new PropertyValue();
            propertyValues[0].Name = "Overwrite";
            propertyValues[0].Value = new Boolean(true);
            propertyValues[1] = new PropertyValue();

            String dirOut = Configuration.getInstance().getConf().getTemplateOutDir();
            String newReport = dirOut + "report_" + year + "_" + month + "_" + new Date().getTime() + ".ods";
            xStorable.storeToURL("file:///" + newReport, propertyValues);

            p("Saved " + newReport);
            xComp.dispose();


            p("done");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.exit(0);


    }

    private static XSpreadsheetDocument openCalc(XComponentContext xContext) {

        XComponentLoader xCLoader;
        XSpreadsheetDocument xSpreadSheetDoc = null;

        try {
            XMultiComponentFactory xMCF = xContext.getServiceManager();

            Object oDesktop = xMCF.createInstanceWithContext(
                    "com.sun.star.frame.Desktop", xContext);

            xCLoader = UnoRuntime.queryInterface(
                    XComponentLoader.class, oDesktop);

            PropertyValue[] szEmptyArgs = new PropertyValue[0];


            String strDoc = "file:"+Configuration.getInstance().getConf().getTemplateSourceFile();

            xComp = xCLoader.loadComponentFromURL(strDoc, "_blank", 0, szEmptyArgs);
            xSpreadSheetDoc = UnoRuntime.queryInterface(
                    XSpreadsheetDocument.class, xComp);

        } catch (Exception e) {
            System.err.println(" Exception " + e);
            e.printStackTrace(System.err);
        }

        return xSpreadSheetDoc;
    }

    private static void insertIntoCell(int CellX, int CellY, String theValue,
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


}
