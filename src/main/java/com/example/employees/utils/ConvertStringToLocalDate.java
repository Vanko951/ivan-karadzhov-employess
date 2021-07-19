package com.example.employees.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public final class ConvertStringToLocalDate {

	private ConvertStringToLocalDate() {
		// No-op; won't be called
	}
	
	/**
	 * Convert string of any knowns date and dateTime formats to date.
	 */
	public static LocalDate convertStringToDate(String date) {
		LocalDate convertedDate = null;
		
		final List<DateTimeFormatter> knownPatterns = new ArrayList<>();
		knownPatterns.add(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		knownPatterns.add(DateTimeFormatter.ofPattern("yyyy-MM-dd'Z'"));
		knownPatterns.add(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
		knownPatterns.add(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm.ss'Z'"));
		knownPatterns.add(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
		knownPatterns.add(DateTimeFormatter.ofPattern("yyyy-MM-dd' 'HH:mm:ss"));
		knownPatterns.add(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX"));
		knownPatterns.add(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
		knownPatterns.add(DateTimeFormatter.ofPattern("yyyy.MM.dd'Z'"));
		knownPatterns.add(DateTimeFormatter.ofPattern("yyyy.MM.dd'T'HH:mm:ss'Z'"));
		knownPatterns.add(DateTimeFormatter.ofPattern("yyyy.MM.dd'T'HH:mm.ss'Z'"));
		knownPatterns.add(DateTimeFormatter.ofPattern("yyyy.MM.dd'T'HH:mm:ss"));
		knownPatterns.add(DateTimeFormatter.ofPattern("yyyy.MM.dd' 'HH:mm:ss"));
		knownPatterns.add(DateTimeFormatter.ofPattern("yyyy.MM.dd'T'HH:mm:ssXXX"));
		knownPatterns.add(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		knownPatterns.add(DateTimeFormatter.ofPattern("yyyy/MM/dd'Z'"));
		knownPatterns.add(DateTimeFormatter.ofPattern("yyyy/MM/dd'T'HH:mm:ss'Z'"));
		knownPatterns.add(DateTimeFormatter.ofPattern("yyyy/MM/dd'T'HH:mm.ss'Z'"));
		knownPatterns.add(DateTimeFormatter.ofPattern("yyyy/MM/dd'T'HH:mm:ss"));
		knownPatterns.add(DateTimeFormatter.ofPattern("yyyy/MM/dd' 'HH:mm:ss"));
		knownPatterns.add(DateTimeFormatter.ofPattern("yyyy/MM/dd'T'HH:mm:ssXXX"));
		
		for (final DateTimeFormatter pattern : knownPatterns) {
			try {
				return LocalDate.parse(date, pattern);
			} catch (final Exception e) {
				e.getStackTrace();
			}
		}
		
		return convertedDate;
	}
}
