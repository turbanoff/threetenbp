/*
 * Copyright (c) 2007-2013, Stephen Colebourne & Michael Nascimento Santos
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-310 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.threeten.bp.chrono;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.threeten.bp.chrono.ISOChronology.ERA_BCE;
import static org.threeten.bp.chrono.ISOChronology.ERA_CE;
import static org.threeten.bp.temporal.ChronoField.ERA;
import static org.threeten.bp.temporal.ChronoField.YEAR;
import static org.threeten.bp.temporal.ChronoField.YEAR_OF_ERA;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.threeten.bp.DateTimeException;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.Month;
import org.threeten.bp.chrono.Chronology;
import org.threeten.bp.chrono.ChronoLocalDate;
import org.threeten.bp.chrono.Era;
import org.threeten.bp.chrono.HijrahChronology;
import org.threeten.bp.chrono.ISOChronology;
import org.threeten.bp.temporal.ChronoField;
import org.threeten.bp.temporal.TemporalAdjusters;

/**
 * Test.
 */
@Test
public class TestISOChrono {

    //-----------------------------------------------------------------------
    // Chrono.ofName("ISO")  Lookup by name
    //-----------------------------------------------------------------------
    @Test
    public void test_chrono_byName() {
        Chronology<ISOChronology> c = ISOChronology.INSTANCE;
        Chronology<?> test = Chronology.of("ISO");
        Assert.assertNotNull(test, "The ISO calendar could not be found byName");
        Assert.assertEquals(test.getId(), "ISO", "ID mismatch");
        Assert.assertEquals(test.getCalendarType(), "iso8601", "Type mismatch");
        Assert.assertEquals(test, c);
    }

    //-----------------------------------------------------------------------
    // Lookup by Singleton
    //-----------------------------------------------------------------------
    @Test
    public void instanceNotNull() {
        assertNotNull(ISOChronology.INSTANCE);
    }

    //-----------------------------------------------------------------------
    // Era creation
    //-----------------------------------------------------------------------
    @Test
    public void test_eraOf() {
        assertEquals(ISOChronology.INSTANCE.eraOf(0), ERA_BCE);
        assertEquals(ISOChronology.INSTANCE.eraOf(1), ERA_CE);
    }

    //-----------------------------------------------------------------------
    // creation, toLocalDate()
    //-----------------------------------------------------------------------
    @DataProvider(name="samples")
    Object[][] data_samples() {
        return new Object[][] {
            {ISOChronology.INSTANCE.date(1, 7, 8), LocalDate.of(1, 7, 8)},
            {ISOChronology.INSTANCE.date(1, 7, 20), LocalDate.of(1, 7, 20)},
            {ISOChronology.INSTANCE.date(1, 7, 21), LocalDate.of(1, 7, 21)},

            {ISOChronology.INSTANCE.date(2, 7, 8), LocalDate.of(2, 7, 8)},
            {ISOChronology.INSTANCE.date(3, 6, 27), LocalDate.of(3, 6, 27)},
            {ISOChronology.INSTANCE.date(3, 5, 23), LocalDate.of(3, 5, 23)},
            {ISOChronology.INSTANCE.date(4, 6, 16), LocalDate.of(4, 6, 16)},
            {ISOChronology.INSTANCE.date(4, 7, 3), LocalDate.of(4, 7, 3)},
            {ISOChronology.INSTANCE.date(4, 7, 4), LocalDate.of(4, 7, 4)},
            {ISOChronology.INSTANCE.date(5, 1, 1), LocalDate.of(5, 1, 1)},
            {ISOChronology.INSTANCE.date(1727, 3, 3), LocalDate.of(1727, 3, 3)},
            {ISOChronology.INSTANCE.date(1728, 10, 28), LocalDate.of(1728, 10, 28)},
            {ISOChronology.INSTANCE.date(2012, 10, 29), LocalDate.of(2012, 10, 29)},
        };
    }

    @Test(dataProvider="samples")
    public void test_toLocalDate(ChronoLocalDate<ISOChronology> isoDate, LocalDate iso) {
        assertEquals(LocalDate.from(isoDate), iso);
    }

    @Test(dataProvider="samples")
    public void test_fromCalendrical(ChronoLocalDate<ISOChronology> isoDate, LocalDate iso) {
        assertEquals(ISOChronology.INSTANCE.date(iso), isoDate);
    }

    @DataProvider(name="badDates")
    Object[][] data_badDates() {
        return new Object[][] {
            {2012, 0, 0},

            {2012, -1, 1},
            {2012, 0, 1},
            {2012, 14, 1},
            {2012, 15, 1},

            {2012, 1, -1},
            {2012, 1, 0},
            {2012, 1, 32},

            {2012, 12, -1},
            {2012, 12, 0},
            {2012, 12, 32},
        };
    }

    @Test(dataProvider="badDates", expectedExceptions=DateTimeException.class)
    public void test_badDates(int year, int month, int dom) {
        ISOChronology.INSTANCE.date(year, month, dom);
    }

    @Test
    public void test_date_withEra() {
        int year = 5;
        int month = 5;
        int dayOfMonth = 5;
        ChronoLocalDate<ISOChronology> test = ISOChronology.INSTANCE.date(ERA_BCE, year, month, dayOfMonth);
        assertEquals(test.getEra(), ERA_BCE);
        assertEquals(test.get(ChronoField.YEAR_OF_ERA), year);
        assertEquals(test.get(ChronoField.MONTH_OF_YEAR), month);
        assertEquals(test.get(ChronoField.DAY_OF_MONTH), dayOfMonth);

        assertEquals(test.get(YEAR), 1 + (-1 * year));
        assertEquals(test.get(ERA), 0);
        assertEquals(test.get(YEAR_OF_ERA), year);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test(expectedExceptions=DateTimeException.class)
    public void test_date_withEra_withWrongEra() {
        ISOChronology.INSTANCE.date((Era) HijrahChronology.ERA_AH, 1, 1, 1);
    }

    //-----------------------------------------------------------------------
    // with(DateTimeAdjuster)
    //-----------------------------------------------------------------------
    @Test
    public void test_adjust1() {
        ChronoLocalDate<ISOChronology> base = ISOChronology.INSTANCE.date(1728, 10, 28);
        ChronoLocalDate<ISOChronology> test = base.with(TemporalAdjusters.lastDayOfMonth());
        assertEquals(test, ISOChronology.INSTANCE.date(1728, 10, 31));
    }

    @Test
    public void test_adjust2() {
        ChronoLocalDate<ISOChronology> base = ISOChronology.INSTANCE.date(1728, 12, 2);
        ChronoLocalDate<ISOChronology> test = base.with(TemporalAdjusters.lastDayOfMonth());
        assertEquals(test, ISOChronology.INSTANCE.date(1728, 12, 31));
    }

    //-----------------------------------------------------------------------
    // ISODate.with(Local*)
    //-----------------------------------------------------------------------
    @Test
    public void test_adjust_toLocalDate() {
        ChronoLocalDate<ISOChronology> isoDate = ISOChronology.INSTANCE.date(1726, 1, 4);
        ChronoLocalDate<ISOChronology> test = isoDate.with(LocalDate.of(2012, 7, 6));
        assertEquals(test, ISOChronology.INSTANCE.date(2012, 7, 6));
    }

    @Test
    public void test_adjust_toMonth() {
        ChronoLocalDate<ISOChronology> isoDate = ISOChronology.INSTANCE.date(1726, 1, 4);
        assertEquals(ISOChronology.INSTANCE.date(1726, 4, 4), isoDate.with(Month.APRIL));
    }

    //-----------------------------------------------------------------------
    // LocalDate.with(ISODate)
    //-----------------------------------------------------------------------
    @Test
    public void test_LocalDate_adjustToISODate() {
        ChronoLocalDate<ISOChronology> isoDate = ISOChronology.INSTANCE.date(1728, 10, 29);
        LocalDate test = LocalDate.MIN.with(isoDate);
        assertEquals(test, LocalDate.of(1728, 10, 29));
    }

    @Test
    public void test_LocalDateTime_adjustToISODate() {
        ChronoLocalDate<ISOChronology> isoDate = ISOChronology.INSTANCE.date(1728, 10, 29);
        LocalDateTime test = LocalDateTime.MIN.with(isoDate);
        assertEquals(test, LocalDateTime.of(1728, 10, 29, 0, 0));
    }

    //-----------------------------------------------------------------------
    // isLeapYear()
    //-----------------------------------------------------------------------
    @DataProvider(name="leapYears")
    Object[][] leapYearInformation() {
        return new Object[][] {
                {2000, true},
                {1996, true},
                {1600, true},

                {1900, false},
                {2100, false},
        };
    }

    @Test(dataProvider="leapYears")
    public void test_isLeapYear(int year, boolean isLeapYear) {
        assertEquals(ISOChronology.INSTANCE.isLeapYear(year), isLeapYear);
    }

    //-----------------------------------------------------------------------
    // toString()
    //-----------------------------------------------------------------------
    @Test
    public void test_now() {
        assertEquals(LocalDate.from(ISOChronology.INSTANCE.dateNow()), LocalDate.now());
    }

    //-----------------------------------------------------------------------
    // toString()
    //-----------------------------------------------------------------------
    @DataProvider(name="toString")
    Object[][] data_toString() {
        return new Object[][] {
            {ISOChronology.INSTANCE.date(1, 1, 1), "0001-01-01"},
            {ISOChronology.INSTANCE.date(1728, 10, 28), "1728-10-28"},
            {ISOChronology.INSTANCE.date(1728, 10, 29), "1728-10-29"},
            {ISOChronology.INSTANCE.date(1727, 12, 5), "1727-12-05"},
            {ISOChronology.INSTANCE.date(1727, 12, 6), "1727-12-06"},
        };
    }

    @Test(dataProvider="toString")
    public void test_toString(ChronoLocalDate<ISOChronology> isoDate, String expected) {
        assertEquals(isoDate.toString(), expected);
    }

    //-----------------------------------------------------------------------
    // equals()
    //-----------------------------------------------------------------------
    @Test
    public void test_equals_true() {
        assertTrue(ISOChronology.INSTANCE.equals(ISOChronology.INSTANCE));
    }

    @Test
    public void test_equals_false() {
        assertFalse(ISOChronology.INSTANCE.equals(HijrahChronology.INSTANCE));
    }

}