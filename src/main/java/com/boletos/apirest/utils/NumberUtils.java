package com.boletos.apirest.utils;

public class NumberUtils {

	
	public static int parse(String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			System.err.println(e);
			return 0;
		}
	}
	
	
}
