package org.scada_lts.model;

import java.util.Objects;

/**
 * @project count
 * @autor grzegorz.bylica@gmail.com on 02.10.18
 */
public class CountInMonth {

    private int month;
    private int countInLocalizations;
    private String location;

    public CountInMonth() {
    }

    public CountInMonth(int month, int countInLocalizations, String location) {
        this.month = month;
        this.countInLocalizations = countInLocalizations;
        this.location = location;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
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
        CountInMonth that = (CountInMonth) o;
        return month == that.month &&
                countInLocalizations == that.countInLocalizations &&
                Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(month, countInLocalizations, location);
    }

    @Override
    public String toString() {
        return "CountInMonth{" +
                "month=" + month +
                ", countInLocalizations=" + countInLocalizations +
                ", location='" + location + '\'' +
                '}';
    }
}
