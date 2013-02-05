package de.fuberlin.livevoting.server.intern.util;

import java.text.SimpleDateFormat;

public class DateFormatUtil {

	private static String dateFormatString = "dd.MM.yyyy H:m";
	private static SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatString);
	
	public static SimpleDateFormat getDateFormat() {
		return dateFormat;
	}
}
