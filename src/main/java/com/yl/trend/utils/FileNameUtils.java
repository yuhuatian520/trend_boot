package com.yl.trend.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileNameUtils {

	public static String getFileNewName(String url,String filename) {
		
		DateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmSSS");
		String format = fmt.format(new Date());
		

		url = url+format+filename;
		
		return url;
		
	}
}
