package com.example.solution.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.Locale;

public class DateUtil {
	
	private static final String CSV_DATE_FORMAT = "dd.mm.yy";
	
	public static Date convertStringToUtilDate(String utilDateStr) {
		SimpleDateFormat formatter = new SimpleDateFormat(CSV_DATE_FORMAT	, Locale.ENGLISH);
		Date date = null;
		try {
			java.util.Date utilDate = formatter.parse(utilDateStr);
			date = new Date(utilDate.getTime());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		return date;
	}
}
