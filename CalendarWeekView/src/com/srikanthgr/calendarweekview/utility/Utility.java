package com.srikanthgr.calendarweekview.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Srikanth G.R
 *
 */
public class Utility {

	private static final String DATE_TIME_FORMAT = "yyyy/MM/dd";
	private static final String DATE_TIME_FORMAT_MILLI = "dd/MM/yyyy";
	private static final String DATE_TIME_FORMAT_AM_PM = "HH:mm aa";

	public static String getDateTimeStr(String p_time_in_millis) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT_MILLI);
		Date l_time = new Date(Long.parseLong(p_time_in_millis));
		return sdf.format(l_time);
	}

	public static Long getMilliSeconds(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
		Date date1 = sdf.parse(date);
		System.out.println(date1.getTime());
		return date1.getTime();
	}

	public static String startTimeEndTime(String startTime) {
		SimpleDateFormat sdfrr = new SimpleDateFormat(DATE_TIME_FORMAT_AM_PM);
		Date l_time = new Date(Long.parseLong(startTime));
		String time = sdfrr.format(l_time);
		return time;

	}
}
