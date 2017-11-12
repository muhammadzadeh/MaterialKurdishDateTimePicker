/**
 * Persian Calendar see: http://code.google.com/p/persian-calendar/
   Copyright (C) 2012  Mortezaadi@gmail.com
   KurdishDateParser.java
   
   Persian Calendar is free software: you can redistribute it and/or modify
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


public class KurdishDateParser {

	private String dateString;
	private String delimiter = "/";

	/**
	 * <pre>
	 * construct parser with date string assigned
	 * the default delimiter is '/'.
	 * 
	 * To assign deferment delimiter use:
	 * {@link #KurdishDateParser(String dateString, String delimiter)}
	 * 
	 *                     Example
	 *                     
	 *  {@code
	 *    KurdishCalendar pCal =
	 *     new KurdishDateParser("1361/3/1").getKurdishDate();
	 *  }
	 * </pre>
	 * 
	 * @param dateString
	 */
	public KurdishDateParser(String dateString) {
		this.dateString = dateString;
	}

	/**
	 * <pre>
	 * construct parser with date string assigned
	 * the default delimiter is '/'. with this constructor
	 * you can set different delimiter to parse the date
	 * based on this delimiter.
	 * see also:
	 * {@link #KurdishDateParser(String dateString)}
	 * 
	 *                     Example
	 *                     
	 *  {@code
	 *    KurdishCalendar pCal =
	 *     new KurdishDateParser("1361-3-1","-").getKurdishDate();
	 *  }
	 * </pre>
	 * 
	 * @param dateString
	 * @param delimiter
	 */
	public KurdishDateParser(String dateString, String delimiter) {
		this(dateString);
		this.delimiter = delimiter;
	}

	/**
	 * Produce the KurdishCalendar object from given DateString throws Exception
	 * if couldn't parse the text.
	 * 
	 * @return KurdishCalendar object
	 * @exception RuntimeException
	 */
	public KurdishCalendar getKurdishDate() {

		checkDateStringInitialValidation();

		String tokens[] = splitDateString(normalizeDateString(dateString));
		int year = Integer.parseInt(tokens[0]);
		int month = Integer.parseInt(tokens[1]);
		int day = Integer.parseInt(tokens[2]);

		checkPersianDateValidation(year, month, day);

		KurdishCalendar pCal = new KurdishCalendar();
		pCal.setKurdishDate(year, month, day);

		return pCal;
	}

	/**
	 * validate the given date
	 * 
	 * @param year
	 * @param month
	 * @param day
	 */
	private void checkPersianDateValidation(int year, int month, int day) {
		if (year < 1)
			throw new RuntimeException("year is not valid");
		if (month < 1 || month > 12)
			throw new RuntimeException("month is not valid");
		if (day < 1 || day > 31)
			throw new RuntimeException("day is not valid");
		if (month > 6 && day == 31)
			throw new RuntimeException("day is not valid");
		if (month == 12 && day == 30 && !KurdishCalendarUtils.isKurdishLeapYear(year))
			throw new RuntimeException("day is not valid " + year + " is not a leap year");
	}

	/**
	 * planned for further calculation before parsing the text
	 * 
	 * @param dateString
	 * @return
	 */
	private String normalizeDateString(String dateString) {
		// dateString = dateString.replace("-", delimiter);
		return dateString;
	}

	private String[] splitDateString(String dateString) {
		String tokens[] = dateString.split(delimiter);
		if (tokens.length != 3)
			throw new RuntimeException("wrong date:" + dateString + " is not a Persian Date or can not be parsed");
		return tokens;
	}

	private void checkDateStringInitialValidation() {
		if (dateString == null)
			throw new RuntimeException("input didn't assing please use setDateString()");
		// if(dateString.length()>10)
		// throw new RuntimeException("wrong date:" + dateString +
		// " is not a Persian Date or can not be parsed" );
	}

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

}
