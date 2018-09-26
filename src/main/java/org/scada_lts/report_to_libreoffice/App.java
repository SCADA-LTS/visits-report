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


import java.util.Date;

public class App {

   public static XComponent xComp = null;

    public static void main(String[] args) throws Exception {

        //get configuration mount and connect database
        //get

        //oooooooooooooooooooooooooooStep 1oooooooooooooooooooooooooooooooooooooooooo
        // call UNO bootstrap method and get the remote component context form
        // the a running office (office will be started if necessary)

        XComponentContext xContext = null;

        // get the remote office component context
        try {
            xContext = com.sun.star.comp.helper.Bootstrap.bootstrap();
            System.out.println("Connected to a running office ...");
        } catch( Exception e) {
            e.printStackTrace(System.err);
            System.exit(1);
        }

        //oooooooooooooooooooooooooooStep 2oooooooooooooooooooooooooooooooooooooooooo
        // open an empty document. In this case it's a calc document.
        // For this purpose an instance of com.sun.star.frame.Desktop
        // is created. The desktop provides the XComponentLoader interface,
        // which is used to open the document via loadComponentFromURL


        //Open document

        //Calc
        XSpreadsheetDocument myDoc = null;
//        XCell oCell = null;

        myDoc = openCalc(xContext);


        //oooooooooooooooooooooooooooStep 3oooooooooooooooooooooooooooooooooooooooooo
        // create cell styles.
        // For this purpose get the StyleFamiliesSupplier and the family
        // CellStyle. Create an instance of com.sun.star.style.CellStyle and
        // add it to the family. Now change some properties


        try {
            XStyleFamiliesSupplier xSFS = UnoRuntime.queryInterface(XStyleFamiliesSupplier.class, myDoc);
            XNameAccess xSF = xSFS.getStyleFamilies();
            XNameAccess xCS = UnoRuntime.queryInterface(
                    XNameAccess.class, xSF.getByName("CellStyles"));
            XMultiServiceFactory oDocMSF = UnoRuntime.queryInterface(XMultiServiceFactory.class, myDoc );
            XNameContainer oStyleFamilyNameContainer = UnoRuntime.queryInterface(
                    XNameContainer.class, xCS);
            XInterface oInt1 = (XInterface) oDocMSF.createInstance(
                    "com.sun.star.style.CellStyle");
            oStyleFamilyNameContainer.insertByName("My Style", oInt1);
            XPropertySet oCPS1 = UnoRuntime.queryInterface(
                    XPropertySet.class, oInt1 );
            oCPS1.setPropertyValue("IsCellBackgroundTransparent", Boolean.FALSE);
            oCPS1.setPropertyValue("CellBackColor",Integer.valueOf(6710932));
            oCPS1.setPropertyValue("CharColor",Integer.valueOf(16777215));
            XInterface oInt2 = (XInterface) oDocMSF.createInstance(
                    "com.sun.star.style.CellStyle");
            oStyleFamilyNameContainer.insertByName("My Style2", oInt2);
            XPropertySet oCPS2 = UnoRuntime.queryInterface(
                    XPropertySet.class, oInt2 );
            oCPS2.setPropertyValue("IsCellBackgroundTransparent", Boolean.FALSE);
            oCPS2.setPropertyValue("CellBackColor",Integer.valueOf(13421823));
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }


        //oooooooooooooooooooooooooooStep 4oooooooooooooooooooooooooooooooooooooooooo
        // get the sheet an insert some data.
        // Get the sheets from the document and then the first from this container.
        // Now some data can be inserted. For this purpose get a Cell via
        // getCellByPosition and insert into this cell via setValue() (for floats)
        // or setFormula() for formulas and Strings



        XSpreadsheet xSheet=null;

        try {
            System.out.println("Getting spreadsheet") ;
            XSpreadsheets xSheets = myDoc.getSheets() ;
            XIndexAccess oIndexSheets = UnoRuntime.queryInterface(
                    XIndexAccess.class, xSheets);
            xSheet = UnoRuntime.queryInterface(
                    XSpreadsheet.class, oIndexSheets.getByIndex(0));

        } catch (Exception e) {
            System.out.println("Couldn't get Sheet " +e);
            e.printStackTrace(System.err);
        }


        // Insert Date se
        //38 B (start) 38 AF (l1)
        //39 B (start) 39 AF (l2)

        System.out.println("Creating the Header") ;


        insertIntoCell(2,37,"100",xSheet,"");
        //insertIntoCell(13,1,"=SUM(B2:M2)",xSheet,"");





        //oooooooooooooooooooooooooooStep 5oooooooooooooooooooooooooooooooooooooooooo
        // apply the created cell style.
        // For this purpose get the PropertySet of the Cell and change the
        // property CellStyle to the appropriate value.


        // change backcolor
        //chgbColor( 3 , 38, 3, 38, "My Style", xSheet );


        XStorable xStorable = (XStorable) UnoRuntime
                .queryInterface(XStorable.class, myDoc);

        PropertyValue[] propertyValues = new PropertyValue[2];
        propertyValues[0] = new PropertyValue();
        propertyValues[0].Name = "Overwrite";
        propertyValues[0].Value = new Boolean(true);
        propertyValues[1] = new PropertyValue();

// Appending the favoured extension to the origin document name
        String myResult = "/opt/PRJ/report-to-libreoffice/template_out/" + "test" + new Date().getTime() + ".ods";
        xStorable.storeToURL("file:///" + myResult, propertyValues);

        System.out.println("Saved " + myResult);
        xComp.dispose();


        System.out.println("done");

        System.exit(0);


    }

    public static XSpreadsheetDocument openCalc(XComponentContext xContext)
    {
        //define variables
        XMultiComponentFactory xMCF = null;
        XComponentLoader xCLoader;
        XSpreadsheetDocument xSpreadSheetDoc = null;

        try {
            // get the servie manager rom the office
            xMCF = xContext.getServiceManager();

            // create a new instance of the desktop
            Object oDesktop = xMCF.createInstanceWithContext(
                    "com.sun.star.frame.Desktop", xContext );

            // query the desktop object for the XComponentLoader
            xCLoader = UnoRuntime.queryInterface(
                    XComponentLoader.class, oDesktop );

            PropertyValue [] szEmptyArgs = new PropertyValue [0];

            //String strDoc = "private:factory/scalc";
            String strDoc = "file:/opt/PRJ/report-to-libreoffice/template/Usagers_2018_Frequentation.ods";

            xComp = xCLoader.loadComponentFromURL(strDoc, "_blank", 0, szEmptyArgs );
            xSpreadSheetDoc = UnoRuntime.queryInterface(
                    XSpreadsheetDocument.class, xComp);

        } catch(Exception e){
            System.err.println(" Exception " + e);
            e.printStackTrace(System.err);
        }

        return xSpreadSheetDoc;
    }

    public static void insertIntoCell(int CellX, int CellY, String theValue,
                                      XSpreadsheet TT1, String flag)
    {
        XCell xCell = null;

        try {
            xCell = TT1.getCellByPosition(CellX, CellY);
        } catch (com.sun.star.lang.IndexOutOfBoundsException ex) {
            System.err.println("Could not get Cell");
            ex.printStackTrace(System.err);
        }

        if (flag.equals("V")) {
            xCell.setValue((new Float(theValue)).floatValue());
        } else {
            xCell.setFormula(theValue);
        }

    }

    public static void chgbColor( int x1, int y1, int x2, int y2,
                                  String template, XSpreadsheet TT )
    {
        XCellRange xCR = null;
        try {
            xCR = TT.getCellRangeByPosition(x1,y1,x2,y2);
        } catch (com.sun.star.lang.IndexOutOfBoundsException ex) {
            System.err.println("Could not get CellRange");
            ex.printStackTrace(System.err);
        }

        XPropertySet xCPS = UnoRuntime.queryInterface(
                XPropertySet.class, xCR );

        try {
            xCPS.setPropertyValue("CellStyle", template);
        } catch (Exception e) {
            System.err.println("Can't change colors chgbColor" + e);
            e.printStackTrace(System.err);
        }
    }





}
