package org.scada_lts.dao;

import java.util.Set;

/**
 * @project count
 * @author grzegorz.bylica@gmail.com on 03.10.18, kamiljarmusik on 03.03.23
 */
public interface ICountInDao<T> {
    Set<T> getAllLocation();
}
