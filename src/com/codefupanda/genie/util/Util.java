/*
 * See the file "LICENSE" for the full license governing this code.
 */
package com.codefupanda.genie.util;

import java.util.Calendar;

import android.widget.DatePicker;

/**
 * @author Shashank
 *
 */
public class Util {
	public static java.util.Date getDateFromDatePicket(DatePicker datePicker){
	    int day = datePicker.getDayOfMonth();
	    int month = datePicker.getMonth();
	    int year =  datePicker.getYear();

	    Calendar calendar = Calendar.getInstance();
	    calendar.set(year, month, day);

	    return calendar.getTime();
	}
	
	public static boolean isBlank(String text) {
		return text == null || text.trim().length() == 0;
	}

	public static CharSequence getDateString(int day, int month, int year) {
		return new StringBuilder().append(month + 1)
		.append("-").append(day).append("-").append(year)
		.append(" ");
	}
}
