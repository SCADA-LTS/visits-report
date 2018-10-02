package org.scada_lts.report_to_libreoffice;

/**
 * @project count
 * @autor grzegorz.bylica@gmail.com on 02.10.18
 */
public interface IReportType {

    void createHeader();

    void insertData();
}
