package com.trungtamtienganh.utils;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;

public class CommonFuc {

	private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
	private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

	public static String toSlug(String input) {

		if (input == null)
			return "";

		String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
		String normalized = Normalizer.normalize(nowhitespace, Form.NFD);
		String slug = NONLATIN.matcher(normalized).replaceAll("");
		return slug.toLowerCase(Locale.ENGLISH);
	}

	public static List<String> shuffleOrder(List<String> inputs) {

		List<String> result = new ArrayList<>();

		Random random = new Random();

		while (inputs.size() > 0) {

			int indexRamdom = random.nextInt(inputs.size());

			result.add(inputs.get(indexRamdom));
			inputs.remove(indexRamdom);
		}

		return result;
	}

	public static List<String> shuffleOrder(String input) {

		List<String> result = new ArrayList<>();

		StringBuffer tempt = new StringBuffer(input.replaceAll(" ", ""));
		while (tempt.length() > 0) {

			int indexRandom = new Random().nextInt(tempt.length());
			result.add(tempt.charAt(indexRandom) + "");
			tempt.deleteCharAt(indexRandom);
		}

		return result;
	}

	public static String getDurationString(long seconds) {

		long hours = seconds / 3600;
		long minutes = (seconds % 3600) / 60;
		seconds = seconds % 60;

		return twoDigitString(hours) + ":" + twoDigitString(minutes) + ":" + twoDigitString(seconds);
	}

	public static String twoDigitString(long number) {

		if (number == 0)
			return "00";

		if (number / 10 == 0)
			return "0" + number;

		return String.valueOf(number);
	}

}
