package org.scada_lts.config;

import java.util.Arrays;
import java.util.Objects;

/**
 * @project count
 * @autor grzegorz.bylica@gmail.com on 28.09.18
 */
public class Config {

    private String dbHost;
    private String db;
    private String dbuser;
    private String dbpasswd;
    private int year;
    private int month;
    private String[] localizations;

    public Config() {

    }

    public Config(String dbHost, String db, String dbuser, String dbpasswd, int year, int month, String[] localizations) {
        this.dbHost = dbHost;
        this.db = db;
        this.dbuser = dbuser;
        this.dbpasswd = dbpasswd;
        this.year = year;
        this.month = month;
        this.localizations = localizations;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Config config = (Config) o;
        return year == config.year &&
                month == config.month &&
                Objects.equals(dbHost, config.dbHost) &&
                Objects.equals(db, config.db) &&
                Objects.equals(dbuser, config.dbuser) &&
                Objects.equals(dbpasswd, config.dbpasswd) &&
                Arrays.equals(localizations, config.localizations);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(dbHost, db, dbuser, dbpasswd, year, month);
        result = 31 * result + Arrays.hashCode(localizations);
        return result;
    }

    @Override
    public String toString() {
        return "Config{" +
                "dbHost='" + dbHost + '\'' +
                ", db='" + db + '\'' +
                ", dbuser='" + dbuser + '\'' +
                ", dbpasswd='" + dbpasswd + '\'' +
                ", year=" + year +
                ", month=" + month +
                ", localizations=" + Arrays.toString(localizations) +
                '}';
    }
}
