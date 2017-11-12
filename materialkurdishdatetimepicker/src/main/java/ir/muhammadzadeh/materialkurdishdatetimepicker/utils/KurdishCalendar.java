/**
 * Kurdish Calendar see: http://code.google.com/p/kurdish-calendar/
   Copyright (C) 2012  Mortezaadi@gmail.com
   KurdishCalendar.java
   
   Kurdish Calendar is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ir.muhammadzadeh.materialkurdishdatetimepicker.utils;

import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * 
 * <strong> Kurdish(Kurdish) calendar </strong>
 * <p>
 * </p>
 * <p>
 * The calendar consists of 12 months, the first six of which are 31 days, the
 * next five 30 days, and the final month 29 days in a normal year and 30 days
 * in a leap year.
 * </p>
 * <p>
 * As one of the few calendars designed in the era of accurate positional
 * astronomy, the Kurdish calendar uses a very complex leap year structure which
 * makes it the most accurate solar calendar in use today. Years are grouped
 * into cycles which begin with four normal years after which every fourth
 * subsequent year in the cycle is a leap year. Cycles are grouped into grand
 * cycles of either 128 years (composed of cycles of 29, 33, 33, and 33 years)
 * or 132 years, containing cycles of of 29, 33, 33, and 37 years. A great grand
 * cycle is composed of 21 consecutive 128 year grand cycles and a final 132
 * grand cycle, for a total of 2820 years. The pattern of normal and leap years
 * which began in 1925 will not repeat until the year 4745!
 * </p>
 * </p> Each 2820 year great grand cycle contains 2137 normal years of 365 days
 * and 683 leap years of 366 days, with the average year length over the great
 * grand cycle of 365.24219852. So close is this to the actual solar tropical
 * year of 365.24219878 days that the Kurdish calendar accumulates an error of
 * one day only every 3.8 million years. As a purely solar calendar, months are
 * not synchronized with the phases of the Moon. </p>
 * <p>
 * </p>
 * 
 * <p>
 * <strong>KurdishCalendar</strong> by extending Default GregorianCalendar
 * provides capabilities such as:
 * </p>
 * <p>
 * </p>
 * 
 * <li>you can set the date in Kurdish by setKurdishDate(kurdishYear,
 * kurdishMonth, kurdishDay) and get the Gregorian date or vice versa</li>
 * <p>
 * </p>
 * <li>determine is the current date is Leap year in kurdish calendar or not by
 * IsKurdishLeapYear()</li>
 * <p>
 * </p>
 * <li>getKurdish short and long Date String getKurdishShortDate() and
 * getKurdishLongDate you also can set delimiter to assign delimiter of returned
 * dateString</li>
 * <p>
 * </p>
 * <li>Parse string based on assigned delimiter</li>
 * <p>
 * </p>
 * <p>
 * </p>
 * <p>
 * </p>
 * <p>
 * <strong> Example </strong>
 * </p>
 * <p>
 * </p>
 * 
 * <pre>
 * {@code
 *       KurdishCalendar kurdishCal = new KurdishCalendar();
 *       System.out.println(kurdishCal.getKurdishShortDate());
 *       
 *       kurdishCal.set(1982, Calendar.MAY, 22);
 *       System.out.println(kurdishCal.getKurdishShortDate());
 *       
 *       kurdishCal.setDelimiter(" , ");
 *       kurdishCal.parse("1361 , 03 , 01");
 *       System.out.println(kurdishCal.getKurdishShortDate());
 *       
 *       kurdishCal.setKurdishDate(1361, 3, 1);
 *       System.out.println(kurdishCal.getKurdishLongDate());
 *       System.out.println(kurdishCal.getTime());
 *       
 *       kurdishCal.addKurdishDate(Calendar.MONTH, 33);
 *       kurdishCal.addKurdishDate(Calendar.YEAR, 5);
 *       kurdishCal.addKurdishDate(Calendar.DATE, 50);
 * 
 * }
 * 
 * <pre>
 * @author Morteza  contact: <a href="mailto:Mortezaadi@gmail.com">Mortezaadi@gmail.com</a>
 * @version 1.1
 */
public class KurdishCalendar extends GregorianCalendar {

	private static final long serialVersionUID = 5541422440580682494L;

	private int kurdishYear;
	private int kurdishMonth;
	private int kurdishDay;
	// use to seperate KurdishDate's field and also Parse the DateString based
	// on this delimiter
	private String delimiter = "/";

	private long convertToMilis(long julianDate) {
		return KurdishCalendarConstants.MILLIS_JULIAN_EPOCH + julianDate * KurdishCalendarConstants.MILLIS_OF_A_DAY
				+ KurdishCalendarUtils.ceil(getTimeInMillis() - KurdishCalendarConstants.MILLIS_JULIAN_EPOCH, KurdishCalendarConstants.MILLIS_OF_A_DAY);
	}

	/**
	 * default constructor
	 * 
	 * most of the time we don't care about TimeZone when we persisting Date or
	 * doing some calculation on date. <strong> Default TimeZone was set to
	 * "GMT" </strong> in order to make developer to work more convenient with
	 * the library; however you can change the TimeZone as you do in
	 * GregorianCalendar by calling setTimeZone()
	 */
	public KurdishCalendar(long millis) {
		setTimeInMillis(millis);
	}

	/**
	 * default constructor
	 * 
	 * most of the time we don't care about TimeZone when we persisting Date or
	 * doing some calculation on date. <strong> Default TimeZone was set to
	 * "GMT" </strong> in order to make developer to work more convenient with
	 * the library; however you can change the TimeZone as you do in
	 * GregorianCalendar by calling setTimeZone()
	 */
	public KurdishCalendar() {
		setTimeZone(TimeZone.getTimeZone("GMT"));
	}

	/**
	 * Calculate kurdish date from current Date and populates the corresponding
	 * fields(kurdishYear, kurdishMonth, kurdishDay)
	 */
	protected void calculateKurdishDate() {
		long julianDate = ((long) Math.floor((getTimeInMillis() - KurdishCalendarConstants.MILLIS_JULIAN_EPOCH)) / KurdishCalendarConstants.MILLIS_OF_A_DAY);
		long KurdishRowDate = KurdishCalendarUtils.julianToKurdish(julianDate);
		long year = KurdishRowDate >> 16;
		int month = (int) (KurdishRowDate & 0xff00) >> 8;
		int day = (int) (KurdishRowDate & 0xff);
		this.kurdishYear = (int) (year > 0 ? year : year - 1);
		this.kurdishYear += 1321;
		this.kurdishMonth = month;
		this.kurdishDay = day;
	}

	/**
	 * 
	 * Determines if the given year is a leap year in kurdish calendar. Returns
	 * true if the given year is a leap year.
	 * 
	 * @return boolean
	 */
	public boolean isKurdishLeapYear() {
		// calculateKurdishDate();
		return KurdishCalendarUtils.isKurdishLeapYear(this.kurdishYear);
	}

	/**
	 * set the kurdish date it converts KurdishDate to the Julian and assigned
	 * equivalent milliseconds to the instance
	 * 
	 * @param kurdishYear
	 * @param kurdishMonth
	 * @param kurdishDay
	 */
	public void setKurdishDate(int kurdishYear, int kurdishMonth, int kurdishDay) {
		kurdishMonth += 1; // TODO
		this.kurdishYear = kurdishYear;
		this.kurdishMonth = kurdishMonth;
		this.kurdishDay = kurdishDay;
		setTimeInMillis(convertToMilis(KurdishCalendarUtils.kurdishToJulian(this.kurdishYear > 0 ? this.kurdishYear : this.kurdishYear + 1, this.kurdishMonth - 1, this.kurdishDay)));
	}

	public int getKurdishYear() {
		// calculateKurdishDate();
		return this.kurdishYear;
	}

	/**
	 * 
	 * @return int kurdish month number
	 */
	public int getKurdishMonth() {
		// calculateKurdishDate();
		return this.kurdishMonth;
	}

	/**
	 * 
	 * @return String kurdish month name
	 */
	public String getKurdishMonthName() {
		// calculateKurdishDate();
		return KurdishCalendarConstants.kurdishMonthNames[this.kurdishMonth];
	}

	/**
	 * 
	 * @return int Kurdish day in month
	 */
	public int getKurdishDay() {
		// calculateKurdishDate();
		return this.kurdishDay;
	}

	/**
	 * 
	 * @return String Name of the day in week
	 */
	public String getKurdishWeekDayName() {
		switch (get(DAY_OF_WEEK)) {
		case SATURDAY:
			return KurdishCalendarConstants.kurdishWeekDays[0];
		case SUNDAY:
			return KurdishCalendarConstants.kurdishWeekDays[1];
		case MONDAY:
			return KurdishCalendarConstants.kurdishWeekDays[2];
		case TUESDAY:
			return KurdishCalendarConstants.kurdishWeekDays[3];
		case WEDNESDAY:
			return KurdishCalendarConstants.kurdishWeekDays[4];
		case THURSDAY:
			return KurdishCalendarConstants.kurdishWeekDays[5];
		default:
			return KurdishCalendarConstants.kurdishWeekDays[6];
		}

	}

	/**
	 * 
	 * @return String of Kurdish Date ex: شنبه 01 خرداد 1361
	 */
	public String getKurdishLongDate() {
		return getKurdishWeekDayName() + "  " + this.kurdishDay + "  " + getKurdishMonthName() + "  " + this.kurdishYear;
	}

	public String getKurdishLongDateAndTime() {
		return getKurdishLongDate() + " ساعت " + get(HOUR_OF_DAY) + ":" + get(MINUTE) + ":" + get(SECOND);
	}

	/**
	 * 
	 * @return String of kurdish date formatted by
	 *         'YYYY[delimiter]mm[delimiter]dd' default delimiter is '/'
	 */
	public String getKurdishShortDate() {
		// calculateKurdishDate();
		return "" + formatToMilitary(this.kurdishYear) + delimiter + formatToMilitary(getKurdishMonth()+1) + delimiter + formatToMilitary(this.kurdishDay);
	}

	public String getKurdishShortDateTime() {
		return "" + formatToMilitary(this.kurdishYear) + delimiter + formatToMilitary(getKurdishMonth()+1) + delimiter + formatToMilitary(this.kurdishDay) + " " + formatToMilitary(this.get(HOUR_OF_DAY)) + ":" + formatToMilitary(get(MINUTE))
				+ ":" + formatToMilitary(get(SECOND));
	}

	private String formatToMilitary(int i) {
		return (i < 9) ? "0" + i : String.valueOf(i);
	}

	/**
	 * add specific amout of fields to the current date for now doesnt handle
	 * before 1 farvardin hejri (before epoch)
	 * 
	 * @param field
	 * @param amount
	 *            <pre>
	 *  Usage:
	 *  {@code
	 *  addKurdishDate(Calendar.YEAR, 2);
	 *  addKurdishDate(Calendar.MONTH, 3);
	 *  }
	 * </pre>
	 * 
	 *            u can also use Calendar.HOUR_OF_DAY,Calendar.MINUTE,
	 *            Calendar.SECOND, Calendar.MILLISECOND etc
	 */
	//
	public void addKurdishDate(int field, int amount) {
		if (amount == 0) {
			return; // Do nothing!
		}

		if (field < 0 || field >= ZONE_OFFSET) {
			throw new IllegalArgumentException();
		}

		if (field == YEAR) {
			setKurdishDate(this.kurdishYear + amount, getKurdishMonth()+1, this.kurdishDay);
			return;
		} else if (field == MONTH) {
			setKurdishDate(this.kurdishYear + ((getKurdishMonth()+1 + amount) / 12), (getKurdishMonth()+1 + amount) % 12, this.kurdishDay);
			return;
		}
		add(field, amount);
		calculateKurdishDate();
	}

	/**
	 * <pre>
	 *    use <code>{@link KurdishDateParser}</code> to parse string 
	 *    and get the Kurdish Date.
	 * </pre>
	 * 
	 * @see KurdishDateParser
	 * @param dateString
	 */
	public void parse(String dateString) {
		KurdishCalendar p = new KurdishDateParser(dateString, delimiter).getKurdishDate();
		setKurdishDate(p.getKurdishYear(), p.getKurdishMonth(), p.getKurdishDay());
	}

	public String getDelimiter() {
		return delimiter;
	}

	/**
	 * assign delimiter to use as a separator of date fields.
	 * 
	 * @param delimiter
	 */
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	@Override
	public String toString() {
		String str = super.toString();
		return str.substring(0, str.length() - 1) + ",KurdishDate=" + getKurdishShortDate() + "]";
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);

	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public void set(int field, int value) {
		super.set(field, value);
		calculateKurdishDate();
	}

	@Override
	public void setTimeInMillis(long millis) {
		super.setTimeInMillis(millis);
		calculateKurdishDate();
	}

	@Override
	public void setTimeZone(TimeZone zone) {
		super.setTimeZone(zone);
		calculateKurdishDate();
	}

}
