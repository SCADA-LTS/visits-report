package org.scada_lts.model;

import java.util.Date;
import java.util.Objects;

/**
 * @project count
 * @author kamiljarmusik on 03.03.23
 */
public class CountInHour {

    private Date date;
    private int countInLocalizations;
    private String location;
    private int hour;

    public CountInHour() {
    }

    public CountInHour(Date date, int countInLocalizations, String location, int hour) {
        this.date = date;
        this.countInLocalizations = countInLocalizations;
        this.location = location;
        this.hour = hour;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCountInLocalizations() {
        return countInLocalizations;
    }

    public void setCountInLocalizations(int countInLocalizations) {
        this.countInLocalizations = countInLocalizations;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CountInHour)) return false;
        CountInHour that = (CountInHour) o;
        return getCountInLocalizations() == that.getCountInLocalizations() && getHour() == that.getHour() && Objects.equals(getDate(), that.getDate()) && Objects.equals(getLocation(), that.getLocation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDate(), getCountInLocalizations(), getLocation(), getHour());
    }

    @Override
    public String toString() {
        return "CountInHour{" +
                "date=" + date +
                ", countInLocalizations=" + countInLocalizations +
                ", location='" + location + '\'' +
                ", hour=" + hour +
                '}';
    }
}
