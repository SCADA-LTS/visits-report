package org.scada_lts.report_to_libreoffice;


public class App {

    private static final String VERSION = " 1.0.0 M1";


    public static void main(String[] args) throws Exception {

        System.out.print("Report to LibreOffice " + VERSION + "\n");

        try {
            // get the remote office component context
            com.sun.star.uno.XComponentContext xContext =
                    com.sun.star.comp.helper.Bootstrap.bootstrap();

            System.out.println("Connected to a running office ...");

            com.sun.star.lang.XMultiComponentFactory xMCF =
                    xContext.getServiceManager();

            String available = (xMCF != null ? "available" : "not available");
            System.out.println( "remote ServiceManager is " + available );
        }
        catch (java.lang.Exception e){
            e.printStackTrace();
        }
        finally {
            System.out.print("END " + VERSION + "\n");
            System.exit(0);
        }



    }

}
