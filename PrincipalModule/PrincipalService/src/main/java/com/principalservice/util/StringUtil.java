package com.principalservice.util;

public class StringUtil {

	public static String addParameter(String cadena, String add) {
		
		if(cadena.trim().length() == 0) {
			cadena += "?" + add;
		}else {
			cadena += "&" + add;
		}
	
		return cadena;
	}
	
}
