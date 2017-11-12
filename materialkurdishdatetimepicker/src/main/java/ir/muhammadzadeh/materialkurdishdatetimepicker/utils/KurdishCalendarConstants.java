/**
 * Persian Calendar see: http://code.google.com/p/persian-calendar/
   Copyright (C) 2012  Mortezaadi@gmail.com
   KurdishCalendarConstants.java
   
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


public class KurdishCalendarConstants {

	// 00:00:00 UTC (Gregorian) Julian day 0,
	// 0 milliseconds since 1970-01-01
	public static final long MILLIS_JULIAN_EPOCH = -210866803200000L;
	// Milliseconds of a day calculated by 24L(hours) * 60L(minutes) *
	// 60L(seconds) * 1000L(mili);
	public static final long MILLIS_OF_A_DAY = 86400000L;

	/**
	 * The JDN of 1 Farvardin 1; Equivalent to March 19, 622 A.D.
	 */
	public static final long PERSIAN_EPOCH = 1948321;

	public static final String[] kurdishMonthNames = {
			"خاکەلێوە",
			"گوڵان",
			"جۆزەردان",
			"پوشەپەر",
			"خەرمانان",
			"گەلاوێژ",
			"رەزبەر",
			"گەڵارێزان",
			"سەرماوەز",
			"بەفرانبار",
			"رێبەندان",
			"رەشەمێ"
	};

	public static final String[] kurdishWeekDays = {
			"شەممە",
			"یەکشەممە",
			"دووشەممە",
			"سێ شەممە",
			"چوارشەممە",
			"پێنج شەممە",
			"هەینی"
	};

}
