package com.scada_lts.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scada_lts.config.TypeReport;
import org.scada_lts.utils.BeforeDate;

@RunWith(Parameterized.class)
public class BeforeDateTest {

    @Parameterized.Parameters(name= "{index}: \nsubject: {0}, dayExpected: {1}, monthExpected: {2}, yearExpected: {3} \n")
    public static Object[][] data() {
        return new Object[][] {
            new Object[] {
                new BeforeDate("01", "01", "2021", TypeReport.DAILY, true),
                31,
                12,
                2020
            }, new Object[] {
                new BeforeDate("01", "01", "2021", TypeReport.MONTHLY, true),
                1,
                12,
                2020
            }, new Object[] {
                new BeforeDate("01", "01", "2021", TypeReport.YEARLY, true),
                1,
                1,
                2020
            }, new Object[] {
                new BeforeDate("01", "01", "2021", TypeReport.DAILY, false),
                1,
                1,
                2021
            }, new Object[] {
                new BeforeDate("01", "01", "2021", TypeReport.MONTHLY, false),
                1,
                1,
                2021
            }, new Object[] {
                new BeforeDate("01", "01", "2021", TypeReport.YEARLY, false),
                1,
                1,
                2021
            }, new Object[] {
                new BeforeDate("01", "01", "2021", TypeReport.DAILY, true),
                31,
                12,
                2020
            }, new Object[] {
                new BeforeDate("01", "02", "2021", TypeReport.DAILY, true),
                31,
                1,
                2021
            }, new Object[] {
                new BeforeDate("01", "02", "2021", TypeReport.MONTHLY, true),
                1,
                1,
                2021
            }, new Object[] {
                new BeforeDate("01", "02", "2021", TypeReport.YEARLY, true),
                1,
                2,
                2020
            }, new Object[] {
                new BeforeDate("28", "03", "2021", TypeReport.DAILY, true),
                27,
                3,
                2021
            }, new Object[] {
                new BeforeDate("28", "03", "2021", TypeReport.MONTHLY, true),
                28,
                2,
                2021
            }, new Object[] {
                new BeforeDate("28", "03", "2021", TypeReport.YEARLY, true),
                28,
                3,
                2020
            }, new Object[] {
                new BeforeDate("28", "12", "2021", TypeReport.DAILY, true),
                27,
                12,
                2021
            }, new Object[] {
                new BeforeDate("28", "12", "2021", TypeReport.MONTHLY, true),
                28,
                11,
                2021
            }, new Object[] {
                new BeforeDate("28", "12", "2021", TypeReport.YEARLY, true),
                28,
                12,
                2020
            }
        };
    }

    private final BeforeDate subject;
    private final int yearExpected;
    private final int monthExpected;
    private final int dayExpected;

    public BeforeDateTest(BeforeDate subject, int dayExpected, int monthExpected, int yearExpected) {
        this.subject = subject;
        this.yearExpected = yearExpected;
        this.monthExpected = monthExpected;
        this.dayExpected = dayExpected;
    }

    @Test
    public void when_getDay() {
        Assert.assertEquals(yearExpected, subject.getYear());
    }

    @Test
    public void when_getMonth() {
        Assert.assertEquals(monthExpected, subject.getMonth());
    }

    @Test
    public void when_getYear() {
        Assert.assertEquals(dayExpected, subject.getDay());
    }
}