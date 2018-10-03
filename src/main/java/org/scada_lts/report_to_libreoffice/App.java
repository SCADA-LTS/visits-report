package org.scada_lts.report_to_libreoffice;

import org.scada_lts.config.Configuration;
import org.scada_lts.config.TypeReport;

public class App extends Log {


    public static void main(String[] args) throws Exception {

        try {
            p("Start report to Libre Office");

            p(Configuration.getInstance().getConf().toString());

            TypeReport tr = Configuration.getInstance().getConf().getTypeReport();

            if (tr == TypeReport.MONTHLY) {
                new MonthlyReport().run();
            } else if (tr == TypeReport.YEARLY) {
                new YearlyReport().run();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }




}
