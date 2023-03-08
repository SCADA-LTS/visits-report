package org.scada_lts.config;

import java.util.Arrays;
import java.util.Objects;

/**
 * @project count
 * @author grzegorz.bylica@gmail.com on 03.10.18, kamiljarmusik on 03.03.23
 */
public class Config {

    private String dbUrl;
    private String dbDriver;
    private String dbUser;
    private String dbPassword;
    private int year;
    private int month;
    private int day;
    private String[] localizations;
    private String templateSourceFileYearly;
    private String templateSourceFileMonthly;
    private String templateSourceFileDaily;
    private String templateOutDir;
    private String type; //yearly, monthly
    private String formatDateForParseInMonthlyReport;
    private String formatDateForParseInDayReport;
    private String formatDateForParseInHourReport;

    public Config() {

    }

    public Config(String dbUrl, String dbDriver, String dbuser, String dbpasswd, int year, int month, int day,
                  String[] localizations, String templateSourceFileYearly, String templateSourceFileMonthly,
                  String templateSourceFileDaily, String templateOutDir, String type,
                  String formatDateForParseInMonthlyReport, String formatDateForParseInDayReport,
                  String formatDateForParseInHourReport) {
        this.dbUrl = dbUrl;
        this.dbDriver = dbDriver;
        this.dbUser = dbuser;
        this.dbPassword = dbpasswd;
        this.year = year;
        this.month = month;
        this.day = day;
        this.localizations = localizations;
        this.templateSourceFileYearly = templateSourceFileYearly;
        this.templateSourceFileMonthly = templateSourceFileMonthly;
        this.templateSourceFileDaily = templateSourceFileDaily;
        this.templateOutDir = templateOutDir;
        this.type = type;
        this.formatDateForParseInMonthlyReport = formatDateForParseInMonthlyReport;
        this.formatDateForParseInDayReport = formatDateForParseInDayReport;
        this.formatDateForParseInHourReport = formatDateForParseInHourReport;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String[] getLocalizations() {
        return localizations;
    }

    public void setLocalizations(String[] localizations) {
        this.localizations = localizations;
    }

    public String getTemplateOutDir() {
        return templateOutDir;
    }

    public void setTemplateOutDir(String templateOutDir) {
        this.templateOutDir = templateOutDir;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getTemplateSourceFileYearly() {
        return templateSourceFileYearly;
    }

    public void setTemplateSourceFileYearly(String templateSourceFileYearly) {
        this.templateSourceFileYearly = templateSourceFileYearly;
    }

    public String getTemplateSourceFileMonthly() {
        return templateSourceFileMonthly;
    }

    public void setTemplateSourceFileMonthly(String templateSourceFileMonthly) {
        this.templateSourceFileMonthly = templateSourceFileMonthly;
    }

    public String getTemplateSourceFileDaily() {
        return templateSourceFileDaily;
    }

    public void setTemplateSourceFileDaily(String templateSourceFileDaily) {
        this.templateSourceFileDaily = templateSourceFileDaily;
    }

    public String getFormatDateForParseInHourReport() {
        return formatDateForParseInHourReport;
    }

    public void setFormatDateForParseInHourReport(String formatDateForParseInHourReport) {
        this.formatDateForParseInHourReport = formatDateForParseInHourReport;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public TypeReport getTypeReport() {
        return TypeReport.typeOf(type);
    }

    public String getFormatDateForParseInMonthlyReport() {
        return formatDateForParseInMonthlyReport;
    }

    public void setFormatDateForParseInMonthlyReport(String formatDateForParseInMonthlyReport) {
        this.formatDateForParseInMonthlyReport = formatDateForParseInMonthlyReport;
    }

    public String getFormatDateForParseInDayReport() {
        return formatDateForParseInDayReport;
    }

    public void setFormatDateForParseInDayReport(String formatDateForParseInDayReport) {
        this.formatDateForParseInDayReport = formatDateForParseInDayReport;
    }

    public String getDbDriver() {
        return dbDriver;
    }

    public void setDbDriver(String dbDriver) {
        this.dbDriver = dbDriver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Config)) return false;
        Config config = (Config) o;
        return getYear() == config.getYear() && getMonth() == config.getMonth() && getDay() == config.getDay() && Objects.equals(getDbUrl(), config.getDbUrl()) && Objects.equals(getDbDriver(), config.getDbDriver()) && Objects.equals(getDbUser(), config.getDbUser()) && Objects.equals(getDbPassword(), config.getDbPassword()) && Arrays.equals(getLocalizations(), config.getLocalizations()) && Objects.equals(getTemplateSourceFileYearly(), config.getTemplateSourceFileYearly()) && Objects.equals(getTemplateSourceFileMonthly(), config.getTemplateSourceFileMonthly()) && Objects.equals(templateSourceFileDaily, config.templateSourceFileDaily) && Objects.equals(getTemplateOutDir(), config.getTemplateOutDir()) && Objects.equals(getType(), config.getType()) && Objects.equals(getFormatDateForParseInMonthlyReport(), config.getFormatDateForParseInMonthlyReport()) && Objects.equals(getFormatDateForParseInDayReport(), config.getFormatDateForParseInDayReport()) && Objects.equals(formatDateForParseInHourReport, config.formatDateForParseInHourReport);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getDbUrl(), getDbDriver(), getDbUser(), getDbPassword(), getYear(), getMonth(), getDay(), getTemplateSourceFileYearly(), getTemplateSourceFileMonthly(), templateSourceFileDaily, getTemplateOutDir(), getType(), getFormatDateForParseInMonthlyReport(), getFormatDateForParseInDayReport(), formatDateForParseInHourReport);
        result = 31 * result + Arrays.hashCode(getLocalizations());
        return result;
    }

    @Override
    public String toString() {
        return "Config{" +
                "dbUrl='" + dbUrl + '\'' +
                ", dbDriver='" + dbDriver + '\'' +
                ", dbUser='" + dbUser + '\'' +
                ", dbPassword='" + dbPassword + '\'' +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", localizations=" + Arrays.toString(localizations) +
                ", templateSourceFileYearly='" + templateSourceFileYearly + '\'' +
                ", templateSourceFileMonthly='" + templateSourceFileMonthly + '\'' +
                ", templateSourceFileDaily='" + templateSourceFileDaily + '\'' +
                ", templateOutDir='" + templateOutDir + '\'' +
                ", type='" + type + '\'' +
                ", formatDateForParseInMonthlyReport='" + formatDateForParseInMonthlyReport + '\'' +
                ", formatDateForParseInDayReport='" + formatDateForParseInDayReport + '\'' +
                ", formatDateForParseInHourReport='" + formatDateForParseInHourReport + '\'' +
                '}';
    }
}
