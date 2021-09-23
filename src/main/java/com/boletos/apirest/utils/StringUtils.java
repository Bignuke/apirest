package com.boletos.apirest.utils;

public class StringUtils {

	public static String trim(String str) {
		if (str==null)	return "";
		return str.trim();
	}

	public static boolean isEmpty(String str) {
		return trim(str).isEmpty();
	}


}
