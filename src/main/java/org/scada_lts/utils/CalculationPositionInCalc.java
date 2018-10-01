package org.scada_lts.utils;

import java.util.Date;

/**
 * @project count
 * @autor grzegorz.bylica@gmail.com on 01.10.18
 */
public class CalculationPositionInCalc {
    private static CalculationPositionInCalc ourInstance = new CalculationPositionInCalc();

    public static CalculationPositionInCalc getInstance() {
        return ourInstance;
    }

    private CalculationPositionInCalc() {
    }

    public int getLeftPosition(int offset, Date date) {
        return DataUtils.getInstance().getDayOfMonth(date) + offset;
    }

    public int getTopPosition(int offset, int indexLocalization) {
        return indexLocalization + offset;
    }

}
