package org.scada_lts.model;

import java.util.Date;
import java.util.Objects;

/**
 * @project count
 * @autor grzegorz.bylica@gmail.com on 28.09.18
 */
public class CountInDay {

    private Date date;
    private int countInLocalizations;
    private String location;

    public CountInDay() {
    }

    public CountInDay(Date date, int countInLocalizations, String location) {
        this.date = date;
        this.countInLocalizations = countInLocalizations;
        this.location = location;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountInDay that = (CountInDay) o;
        return countInLocalizations == that.countInLocalizations &&
                Objects.equals(date, that.date) &&
                Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, countInLocalizations, location);
    }

    @Override
    public String toString() {
        return "CountInDay{" +
                "date=" + date +
                ", countInLocalizations=" + countInLocalizations +
                ", location='" + location + '\'' +
                '}';
    }

}
