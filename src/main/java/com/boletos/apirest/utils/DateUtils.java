package com.boletos.apirest.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.joda.time.DateTime;

public class DateUtils {

	public static Locale LOCALE_BR = new Locale("pt", "BR");

	public enum PadraoData {		

		YYYYMMDD("yyyy-MM-dd"),
		DDMMYYYY_BR("dd/MM/yyyy"),
		DDMMYYYY_BR_HHMMSS_DP("dd/MM/yyyy HH:mm:ss");
		


		private String padrao;
		private PadraoData(String padrao) {
			this.padrao = padrao;
		}
		public String getPadrao() {
			return padrao;
		}
	}

	private DateUtils() {}


	public static Date dataAtual() {
		Date date = new Date();
	    TimeZone zone = TimeZone.getTimeZone("America/Sao_Paulo");
	    
	    if (zone.inDaylightTime(date)) {// a partir de 2019, nao temos o horario de verao
	    	return new DateTime(date).minusHours(1).toDate();
	    }
		return date;
	}

	
	public static String formatar(Date date, PadraoData padraoData) {
		if (date == null)
			return "";
		return new SimpleDateFormat(padraoData.getPadrao()).format(date);
	}
	
	public static Date converter(String date, PadraoData padraoData) {
		if (!StringUtils.isEmpty(date))
			try {
				return new SimpleDateFormat(padraoData.getPadrao()).parse(date);
			} catch (ParseException e) {}
		return null;
	}

	public static String formatarBankly(Date date) {
		if (date == null)
			return "";
		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(date);
	}
	
	public static Date converterBankly(String date) {
		if (!StringUtils.isEmpty(date)) {
			if (!date.contains(".")) {
				int index = date.indexOf("+");
				date = date.substring(0, index) + ".000" + date.substring(index);
			}
			try {
				System.out.println(date);
				return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(date);
			} catch (ParseException e) {
				System.err.println(e);
			}
		}
		return null;
	}

	public static Date addSegundos(Date data, int segundos) {
		if (data == null || segundos <= 0)
			return data;
		
		DateTime dateTime = new DateTime(data);
		return dateTime.plusSeconds(segundos).toDate();
	}


}
