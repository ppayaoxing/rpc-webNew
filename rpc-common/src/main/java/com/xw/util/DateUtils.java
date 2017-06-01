package com.xw.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String getCurrentDate() {
		return sdf.format(new Date());
	}

}
