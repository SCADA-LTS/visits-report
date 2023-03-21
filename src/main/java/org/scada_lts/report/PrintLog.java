package org.scada_lts.report;

import org.apache.commons.logging.LogFactory;

/**
 * @project count
 * @author grzegorz.bylica@gmail.com on 03.10.18, kamiljarmusik on 03.03.23
 */
public final class PrintLog {

    private PrintLog() {}

    private static final org.apache.commons.logging.Log LOG = LogFactory.getLog(PrintLog.class);

    public static void p(Object str) {
        LOG.info(str);
    }

    public static void error(String str, Exception exception) {
        LOG.error(str, exception);
    }

    public static void warn(String str) {
        LOG.warn(str);
    }
}
