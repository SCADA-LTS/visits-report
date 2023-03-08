package org.scada_lts.report_to_libreoffice;

import org.scada_lts.config.Configuration;
import org.scada_lts.config.TypeReport;

import static org.scada_lts.report_to_libreoffice.PrintLog.error;
import static org.scada_lts.report_to_libreoffice.PrintLog.p;

public class App {

    public static void main(String[] args) throws Exception {

        try {
            p("Start report to Libre Office");

            p(Configuration.getInstance().getConf().toString());

            TypeReport tr = Configuration.getInstance().getConf().getTypeReport();

            switch (tr) {
                case DAILY:
                    new DailyReport().run();
                    break;
                case MONTHLY:
                    new MonthlyReport().run();
                    break;
                case YEARLY:
                    new YearlyReport().run();
                    break;
                default:
            }

        } catch (Exception e) {
            error(e.getMessage(), e);
        }


    }




}
