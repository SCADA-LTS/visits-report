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
import org.scada_lts.config.TypeReport;
import org.scada_lts.dao.CountInDayDao;
import org.scada_lts.model.CountInDay;
import org.scada_lts.utils.CalculationPositionInCalc;
import org.scada_lts.utils.DataUtils;


import java.text.SimpleDateFormat;
import java.util.*;

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

            p("done");

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.exit(0);


    }




}
