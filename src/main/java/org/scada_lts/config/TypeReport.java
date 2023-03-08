package org.scada_lts.config;

import java.util.stream.Stream;

/**
 * @project count
 * @author grzegorz.bylica@gmail.com on 03.10.18, kamiljarmusik on 03.03.23
 */
public enum TypeReport {
    YEARLY,
    MONTHLY,
    DAILY;

    public static TypeReport typeOf(String name) {
        if(name == null)
            return TypeReport.MONTHLY;
        return Stream.of(TypeReport.values())
                .filter(a -> a.name().equals(name.toUpperCase()))
                .findAny()
                .orElse(TypeReport.MONTHLY);
    }
}
