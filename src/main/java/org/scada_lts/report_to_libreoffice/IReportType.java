package org.scada_lts.report_to_libreoffice;


import org.jopendocument.dom.spreadsheet.Sheet;

/**
 * @project count
 * @author grzegorz.bylica@gmail.com on 03.10.18, kamiljarmusik on 03.03.23
 */
public interface IReportType {
    void createHeader(Sheet sheet);
    void insertData(Sheet sheet);
    String getTemplatePath();
    String getReportPath();
}
