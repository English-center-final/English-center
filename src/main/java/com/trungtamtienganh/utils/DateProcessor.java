package com.trungtamtienganh.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateProcessor {

	private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY");

	public static String toDateString(LocalDate date) {

		if (date == null)
			return null;

		return date.format(dtf);
	}
	
	public static LocalDate toLocalDate(String dateString) {

		if (dateString == null)
			return null;

		String[] x = dateString.split("/");

		LocalDate localDate = LocalDate.parse(x[2] + "-" + x[1] + "-" + x[0]);

		return localDate;
	}
	
}
