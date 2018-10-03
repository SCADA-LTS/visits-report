package org.scada_lts.dao;

import org.scada_lts.model.CountInMonth;

import java.util.Set;

/**
 * @project count
 * @autor grzegorz.bylica@gmail.com on 02.10.18
 */
public interface ICountInMonthDao {
    Set<CountInMonth[]> getAllLocation();
}
