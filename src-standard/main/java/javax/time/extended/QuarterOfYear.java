/*
 * Copyright (c) 2007-2011, Stephen Colebourne & Michael Nascimento Santos
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
package javax.time.extended;

import javax.time.CalendricalException;
import javax.time.DateTimes;
import javax.time.MonthOfYear;
import javax.time.calendrical.CalendricalObject;
import javax.time.calendrical.DateTimeBuilder;

/**
 * A quarter-of-year, such as 'Q2'.
 * <p>
 * {@code QuarterOfYear} is an enum representing the 4 quarters of the year -
 * Q1, Q2, Q3 and Q4. These are defined as January to March, April to June,
 * July to September and October to December.
 * <p>
 * The calendrical framework requires date-time fields to have an {@code int} value.
 * The {@code int} value follows the quarter, from 1 (Q1) to 4 (Q4).
 * It is recommended that applications use the enum rather than the {@code int} value
 * to ensure code clarity.
 * <p>
 * <b>Do not use {@code ordinal()} to obtain the numeric representation of {@code QuarterOfYear}.
 * Use {@code getValue()} instead.</b>
 * <p>
 * This enum represents a common concept that is found in many calendar systems.
 * As such, this enum may be used by any calendar system that has the quarter-of-year
 * concept defined exactly equivalent to the ISO calendar system.
 * <p>
 * This is an immutable and thread-safe enum.
 */
public enum QuarterOfYear implements CalendricalObject {

    /**
     * The singleton instance for the first quarter-of-year, from January to March.
     * This has the numeric value of {@code 1}.
     */
    Q1,
    /**
     * The singleton instance for the second quarter-of-year, from April to June.
     * This has the numeric value of {@code 2}.
     */
    Q2,
    /**
     * The singleton instance for the third quarter-of-year, from July to September.
     * This has the numeric value of {@code 3}.
     */
    Q3,
    /**
     * The singleton instance for the fourth quarter-of-year, from October to December.
     * This has the numeric value of {@code 4}.
     */
    Q4;

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code QuarterOfYear} from an {@code int} value.
     * <p>
     * {@code QuarterOfYear} is an enum representing the 4 quarters of the year.
     * This factory allows the enum to be obtained from the {@code int} value.
     * The {@code int} value follows the quarter, from 1 (Q1) to 4 (Q4).
     * <p>
     * An exception is thrown if the value is invalid. The exception uses the
     * {@link ISOChronology} quarter-of-year rule to indicate the failed rule.
     *
     * @param quarterOfYear  the quarter-of-year to represent, from 1 (Q1) to 4 (Q4)
     * @return the QuarterOfYear singleton, not null
     * @throws CalendricalException if the quarter-of-year is invalid
     */
    public static QuarterOfYear of(int quarterOfYear) {
        switch (quarterOfYear) {
            case 1: return Q1;
            case 2: return Q2;
            case 3: return Q3;
            case 4: return Q4;
            default: throw new CalendricalException("Invalid value for QuarterOfYear: " + quarterOfYear);
        }
    }

    /**
     * Obtains an instance of {@code QuarterOfYear} from a month-of-year.
     * <p>
     * {@code QuarterOfYear} is an enum representing the 4 quarters of the year.
     * This factory allows the enum to be obtained from the {@code MonthOfYear} value.
     * <p>
     * January to March are Q1, April to June are Q2, July to September are Q3
     * and October to December are Q4.
     *
     * @param quarterOfYear  the quarter-of-year to represent, from 1 (Q1) to 4 (Q4)
     * @return the QuarterOfYear singleton, not null
     */
    public static QuarterOfYear ofMonth(MonthOfYear monthOfYear) {
        DateTimes.checkNotNull(monthOfYear, "MonthOfYear must not be null");
        return of(monthOfYear.ordinal() / 3 + 1);
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code QuarterOfYear} from a calendrical.
     * <p>
     * A calendrical represents some form of date and time information.
     * This factory converts the arbitrary calendrical to an instance of {@code QuarterOfYear}.
     * 
     * @param calendrical  the calendrical to convert, not null
     * @return the quarter-of-year, not null
     * @throws CalendricalException if unable to convert to a {@code QuarterOfYear}
     */
    public static QuarterOfYear from(CalendricalObject calendrical) {
        if (calendrical instanceof QuarterOfYear) {
            return (QuarterOfYear) calendrical;
        }
        return of((int) QuarterYearField.QUARTER_OF_YEAR.getValueFrom(calendrical));
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the quarter-of-year {@code int} value.
     * <p>
     * The values are numbered following the ISO-8601 standard,
     * from 1 (Q1) to 4 (Q4).
     *
     * @return the quarter-of-year, from 1 (Q1) to 4 (Q4)
     */
    public int getValue() {
        return ordinal() + 1;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the textual representation, such as 'Q1' or '4th quarter'.
     * <p>
     * This method is notionally specific to {@link ISOChronology} as it uses
     * the quarter-of-year rule to obtain the text. However, it is expected that
     * the text will be equivalent for all quarter-of-year rules, thus this aspect
     * of the implementation should be irrelevant to applications.
     * <p>
     * If no textual mapping is found then the {@link #getValue() numeric value} is returned.
     *
     * @param locale  the locale to use, not null
     * @return the short text value of the month-of-year, not null
     */
//    public String getText(TextStyle style, Locale locale) {
//        return QUARTER_OF_YEAR.getText(getValue(), style, locale);
//    }

    //-----------------------------------------------------------------------
    /**
     * Gets the next quarter-of-year.
     * <p>
     * This calculates based on the time-line, thus it rolls around the end of
     * the week. The next quarter after Q4 is Q1.
     *
     * @return the next quarter-of-year, not null
     */
    public QuarterOfYear next() {
        return roll(1);
    }

    /**
     * Gets the previous quarter-of-year.
     * <p>
     * This calculates based on the time-line, thus it rolls around the end of
     * the year. The previous quarter before Q1 is Q4.
     *
     * @return the previous quarter-of-year, not null
     */
    public QuarterOfYear previous() {
        return roll(-1);
    }

    /**
     * Rolls the quarter-of-year, adding the specified number of quarters.
     * <p>
     * This calculates based on the time-line, thus it rolls around the end of
     * the year from Q4 to Q1. The quarters to roll by may be negative.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * @param quarters  the quarters to roll by, positive or negative
     * @return the resulting quarter-of-year, not null
     */
    public QuarterOfYear roll(int quarters) {
        return values()[(ordinal() + (quarters % 4 + 4)) % 4];
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the first of the three months that this quarter refers to.
     * <p>
     * Q1 will return January.<br />
     * Q2 will return April.<br />
     * Q3 will return July.<br />
     * Q4 will return October.
     * <p>
     * To obtain the other two months of the quarter, simply use {@link MonthOfYear#next()}
     * on the returned month.
     *
     * @return the first month in the quarter, not null
     */
    public MonthOfYear getFirstMonthOfQuarter() {
        switch (this) {
            case Q1: return MonthOfYear.JANUARY;
            case Q2: return MonthOfYear.APRIL;
            case Q3: return MonthOfYear.JULY;
            case Q4: return MonthOfYear.OCTOBER;
            default: throw new IllegalStateException("Unreachable");
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Extracts date-time information in a generic way.
     * <p>
     * This method exists to fulfill the {@link CalendricalObject} interface.
     * This implementation returns the following types:
     * <ul>
     * <li>QuarterOfYear
     * <li>DateTimeBuilder, using {@link QuarterYearField#QUARTER_OF_YEAR}
     * </ul>
     * 
     * @param <R> the type to extract
     * @param type  the type to extract, null returns null
     * @return the extracted object, null if unable to extract
     */
    @SuppressWarnings("unchecked")
    @Override
    public <R> R extract(Class<R> type) {
        if (type == DateTimeBuilder.class) {
            return (R) new DateTimeBuilder(QuarterYearField.QUARTER_OF_YEAR, getValue());
        } else if (type == QuarterOfYear.class) {
            return (R) this;
        }
        return null;
    }

}