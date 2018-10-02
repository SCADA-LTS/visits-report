package org.scada_lts.dao;

import org.scada_lts.model.CountInDay;

import java.util.Set;

/**
 * @project count
 * @autor grzegorz.bylica@gmail.com on 27.09.18
 */
public interface ICountInDayDao {

    Set<CountInDay[]> getAllLocation();
}
