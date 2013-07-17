package net.therncway.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static long date2second(String time){
		long temp = 0;
		try {
			temp = sdf.parse(time).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return temp;
	}
	
	public static String second2Date(long time){
		return sdf.format(new Date(time));
	}
}
