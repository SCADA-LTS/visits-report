package org.scada_lts.config;

import java.util.Arrays;
import java.util.Objects;

/**
 * @project count
 * @autor grzegorz.bylica@gmail.com on 28.09.18
 */
public class Config {



    private String dbHost;
    private String dbPort;
    private String db;
    private String dbuser;
    private String dbpasswd;
    private int year;
    private int month;
    private String[] localizations;
    private String templateSourceFileYearly;
    private String templateSourceFileMonthly;
    private String templateOutDir;
    private String type; //yearly, monthly


    public Config() {

    }

    public Config(String dbHost, String dbPort, String db, String dbuser, String dbpasswd, int year, int month, String[] localizations, String templateSourceFileYearly, String templateSourceFileMonthly, String templateOutDir, String type) {
        this.dbHost = dbHost;
        this.dbPort = dbPort;
        this.db = db;
        this.dbuser = dbuser;
        this.dbpasswd = dbpasswd;
        this.year = year;
        this.month = month;
        this.localizations = localizations;
        this.templateSourceFileYearly = templateSourceFileYearly;
        this.templateSourceFileMonthly = templateSourceFileMonthly;
        this.templateOutDir = templateOutDir;
        this.type = type;
    }

    public String getDbHost() {
        return dbHost;
    }

    public void setDbHost(String dbHost) {
        this.dbHost = dbHost;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getDbuser() {
        return dbuser;
    }

    public void setDbuser(String dbuser) {
        this.dbuser = dbuser;
    }

    public String getDbpasswd() {
        return dbpasswd;
    }

    public void setDbpasswd(String dbpasswd) {
        this.dbpasswd = dbpasswd;
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

    public String getDbPort() {
        return dbPort;
    }

    public void setDbPort(String dbPort) {
        this.dbPort = dbPort;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public TypeReport getTypeReport() {
        return CheckTypeReport.getInstance().getTypeReportBaseOnStr(type);
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Config config = (Config) o;
        return year == config.year &&
                month == config.month &&
                Objects.equals(dbHost, config.dbHost) &&
                Objects.equals(dbPort, config.dbPort) &&
                Objects.equals(db, config.db) &&
                Objects.equals(dbuser, config.dbuser) &&
                Objects.equals(dbpasswd, config.dbpasswd) &&
                Arrays.equals(localizations, config.localizations) &&
                Objects.equals(templateSourceFileYearly, config.templateSourceFileYearly) &&
                Objects.equals(templateSourceFileMonthly, config.templateSourceFileMonthly) &&
                Objects.equals(templateOutDir, config.templateOutDir) &&
                Objects.equals(type, config.type);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(dbHost, dbPort, db, dbuser, dbpasswd, year, month, templateSourceFileYearly, templateSourceFileMonthly, templateOutDir, type);
        result = 31 * result + Arrays.hashCode(localizations);
        return result;
    }

    @Override
    public String toString() {
        return "Config{" +
                "dbHost='" + dbHost + '\'' +
                ", dbPort='" + dbPort + '\'' +
                ", db='" + db + '\'' +
                ", dbuser='" + dbuser + '\'' +
                ", dbpasswd='" + dbpasswd + '\'' +
                ", year=" + year +
                ", month=" + month +
                ", localizations=" + Arrays.toString(localizations) +
                ", templateSourceFileYearly='" + templateSourceFileYearly + '\'' +
                ", templateSourceFileMonthly='" + templateSourceFileMonthly + '\'' +
                ", templateOutDir='" + templateOutDir + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
