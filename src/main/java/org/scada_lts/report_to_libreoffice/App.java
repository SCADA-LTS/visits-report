package org.scada_lts.report_to_libreoffice;


import com.sun.star.uno.UnoRuntime;

public class App {


    public static void main(String[] args) throws Exception {

        com.sun.star.frame.XDesktop xDesktop = null;
        xDesktop = getDesktop();

        // create text document
        com.sun.star.text.XTextDocument xTextDocument = null;
        xTextDocument = openTextdocument(xDesktop);

    }

    public static com.sun.star.text.XTextDocument openTextdocument(
            com.sun.star.frame.XDesktop xDesktop )
    {
        com.sun.star.text.XTextDocument aTextDocument = null;

        try {
            com.sun.star.lang.XComponent xComponent = openNewDocument(xDesktop,
                     "scalc");
            aTextDocument = UnoRuntime.queryInterface(
                    com.sun.star.text.XTextDocument.class, xComponent);
        }
        catch( Exception e) {
            e.printStackTrace(System.err);
        }

        return aTextDocument;
    }

    protected static com.sun.star.lang.XComponent openNewDocument(
            com.sun.star.frame.XDesktop xDesktop,
            String sDocumentType )
    {

        com.sun.star.lang.XComponent xComponent = null;
        com.sun.star.frame.XComponentLoader xComponentLoader = null;
        com.sun.star.beans.PropertyValue xEmptyArgs[] =
                new com.sun.star.beans.PropertyValue[0];

        try {
            xComponentLoader = UnoRuntime.queryInterface(
                    com.sun.star.frame.XComponentLoader.class, xDesktop);

            xComponent = xComponentLoader.loadComponentFromURL("file:/opt/PRJ/report-to-libreoffice/template/Usagers_2018_Frequentation.ods", "_blank", 0, xEmptyArgs);
        }
        catch( Exception e) {
            e.printStackTrace(System.err);
        }

        return xComponent ;
    }

    public static com.sun.star.frame.XDesktop getDesktop() {
        com.sun.star.frame.XDesktop xDesktop = null;
        com.sun.star.lang.XMultiComponentFactory xMCF = null;

        try {
            com.sun.star.uno.XComponentContext xContext = null;

            // get the remote office component context
            xContext = com.sun.star.comp.helper.Bootstrap.bootstrap();

            // get the remote office service manager
            xMCF = xContext.getServiceManager();
            if( xMCF != null ) {
                System.out.println("Connected to a running office ...");

                Object oDesktop = xMCF.createInstanceWithContext(
                        "com.sun.star.frame.Desktop", xContext);
                xDesktop = UnoRuntime.queryInterface(
                        com.sun.star.frame.XDesktop.class, oDesktop);
            }
            else
                System.out.println( "Can't create a desktop. No connection, no remote office servicemanager available!" );
        }
        catch( Exception e) {
            e.printStackTrace(System.err);
            System.exit(1);
        }


        return xDesktop;
    }


}
