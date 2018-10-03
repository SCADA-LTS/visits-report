package org.scada_lts.config;

/**
 * @project count
 * @autor grzegorz.bylica@gmail.com on 03.10.18
 */
public class CheckTypeReport {

    private static final String YEARLY = "yearly";
    private static final String MONTHLY = "monthly";

    private static CheckTypeReport ourInstance = new CheckTypeReport();

    public static CheckTypeReport getInstance() {
        return ourInstance;
    }


    private CheckTypeReport() {
        //
    }

    public TypeReport getTypeReportBaseOnStr(String type) {
        if (type.trim().equals(YEARLY)) {
            return TypeReport.YEARLY;
        } else if (type.trim().equals(MONTHLY)) {
            return TypeReport.MONTHLY;
        }
        new RuntimeException("Not set type of report");
        return null;
    }
}
