package org.scada_lts.report_to_libreoffice;


import com.sun.star.awt.Rectangle;

import com.sun.star.beans.PropertyValue;
import com.sun.star.beans.XPropertySet;

import com.sun.star.chart.XDiagram;
import com.sun.star.chart.XChartDocument;

import com.sun.star.container.XIndexAccess;
import com.sun.star.container.XNameAccess;
import com.sun.star.container.XNameContainer;

import com.sun.star.document.XEmbeddedObjectSupplier;

import com.sun.star.frame.XComponentLoader;

import com.sun.star.frame.XStorable;
import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiServiceFactory;
import com.sun.star.lang.XMultiComponentFactory;

import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XInterface;
import com.sun.star.uno.XComponentContext;

import com.sun.star.sheet.XCellRangeAddressable;
import com.sun.star.sheet.XSpreadsheet;
import com.sun.star.sheet.XSpreadsheets;
import com.sun.star.sheet.XSpreadsheetDocument;

import com.sun.star.style.XStyleFamiliesSupplier;

import com.sun.star.table.XCell;
import com.sun.star.table.XCellRange;
import org.scada_lts.config.Config;
import org.scada_lts.config.Configuration;
import org.scada_lts.dao.CountInDayDao;
import org.scada_lts.model.CountInDay;


import java.util.Date;
import java.util.Set;

public class App {

    public static XComponent xComp = null;

    public static void p(String str) {
        System.out.println(str);
    }

    public static void main(String[] args) throws Exception {

        p("Start report to libreoffice");

        p(Configuration.getInstance().getConf().toString());

        XComponentContext xContext = null;

        try {
            xContext = com.sun.star.comp.helper.Bootstrap.bootstrap();
            p("Connected to a running office ...");
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.exit(1);
        }

        XSpreadsheetDocument myDoc = null;

        myDoc = openCalc(xContext);

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


        // get complet data from database;

        Set<CountInDay[]> data = new CountInDayDao().getAllLocation();

        for (CountInDay[] counts : data) {
            for (CountInDay count : counts) {
                p(count.toString());
            }
        }


        insertIntoCell(2, 37, "100", xSheet, "");


        XStorable xStorable = (XStorable) UnoRuntime
                .queryInterface(XStorable.class, myDoc);

        PropertyValue[] propertyValues = new PropertyValue[2];
        propertyValues[0] = new PropertyValue();
        propertyValues[0].Name = "Overwrite";
        propertyValues[0].Value = new Boolean(true);
        propertyValues[1] = new PropertyValue();

        // from config path
        String newReport = "/opt/PRJ/report-to-libreoffice/template_out/" + "report" + new Date().getTime() + ".ods";
        xStorable.storeToURL("file:///" + newReport, propertyValues);

        p("Saved " + newReport);
        xComp.dispose();


        p("done");

        System.exit(0);


    }

    public static XSpreadsheetDocument openCalc(XComponentContext xContext) {

        XMultiComponentFactory xMCF = null;
        XComponentLoader xCLoader;
        XSpreadsheetDocument xSpreadSheetDoc = null;

        try {
            xMCF = xContext.getServiceManager();

            //TODO check how work in console maybe is not necessary Desktop
            Object oDesktop = xMCF.createInstanceWithContext(
                    "com.sun.star.frame.Desktop", xContext);

            xCLoader = UnoRuntime.queryInterface(
                    XComponentLoader.class, oDesktop);

            PropertyValue[] szEmptyArgs = new PropertyValue[0];

            //TODO get path from config
            String strDoc = "file:/opt/PRJ/report-to-libreoffice/template/Usagers_2018_Frequentation.ods";

            xComp = xCLoader.loadComponentFromURL(strDoc, "_blank", 0, szEmptyArgs);
            xSpreadSheetDoc = UnoRuntime.queryInterface(
                    XSpreadsheetDocument.class, xComp);

        } catch (Exception e) {
            System.err.println(" Exception " + e);
            e.printStackTrace(System.err);
        }

        return xSpreadSheetDoc;
    }

    public static void insertIntoCell(int CellX, int CellY, String theValue,
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
        } else {
            xCell.setFormula(theValue);
        }

    }


}
