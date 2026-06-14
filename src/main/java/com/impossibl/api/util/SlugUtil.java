package com.impossibl.api.util;

public class SlugUtil {

	private SlugUtil() {
	}

	public static String slugify(String text) {
		return text.toLowerCase().replaceAll("[^a-z0-9]+", "-").replaceAll("^-+|-+$", "");
	}
}
