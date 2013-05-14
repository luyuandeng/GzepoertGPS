package com.gzeport.app.gps.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class StringUtil {
	
	
	private static long tmpID = 0;
	 
	private static boolean tmpIDlocked = false;

	// 判断字符串不为空
	public static boolean isNotEmpty(Object obj) {
		if (obj != null ) {
			String tmp = String.valueOf(obj);
				if (!tmp.trim().equals(""))
					return true;
			}
		return false;
	}
	
	// 将字符串转化为整数
	public static int parseInteger(String str) {
		if (isNotEmpty(str))
			return Integer.parseInt(str);
		return 0;
	}
	
	// 将字符串转化为长整型
	public static long parseLong(String str) {
		if (isNotEmpty(str))
			return Long.parseLong(str);
		return 0;
	}

	// 将字符串转化为双精度
	public static double parseDouble(String str) {
		if (isNotEmpty(str))
			return Double.parseDouble(str);
		return 0;
	}

	// 将字符串转换为日期
	public static Date parseDate(String str, String format)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = sdf.parse(str);
		return date;
	}
	
	// 将字符串转换为日期
	public static Date parseShortDate(String str ){
		 
		Date date=null;
		String format="yyyy-mm-dd";
		try {
			date = parseDate(str,format);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	// 将日期转换为字符串
	public static String parseDateToString(Date date, String format)
		  {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	// 将字符串转换为特定格式的字符串
	public static String parseFormatString(String source, String format) {

		String result = "";
		SimpleDateFormat sdf = null;
		SimpleDateFormat local = new SimpleDateFormat(format);
		Date date = null;
		if(source.toUpperCase().indexOf("CST") != -1){
			sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
			sdf.setTimeZone(TimeZone.getTimeZone("CST"));
			
			try {
				date = sdf.parse(source);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			result = local.format(date);
		}else{
			try {
				date = local.parse(source);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			result = local.format(date);
		}
 
		return result;
	}

	// 获取字符串长度
	public static int getTrimLength(String str) {
		if (isNotEmpty(str.trim())) {
			return str.trim().length();
		}
		return 0;
	}

	/**
	 * 字符串替换
	 * @param strFrom 要替换的目标子串
	 * @param strTo 替换后的子串
	 * @param strSource 原字符串
	 * @return 替换后的字符串
	 */

	public static String replace(String strSource, String strFrom, String strTo) {

		String strDest = "";
		int intFromLen = strFrom.length();
		int intPos;
		while ((intPos = strSource.indexOf(strFrom)) != -1) {
			strDest = strDest + strSource.substring(0, intPos);
			strDest = strDest + strTo;
			strSource = strSource.substring(intPos + intFromLen);
		}

		strDest = strDest + strSource;
		return strDest;
	}

	 
	public static long getUniqueId() {
		long ltime = 0;
		while (true) {
			if (tmpIDlocked == false) {
				tmpIDlocked = true;
				ltime = Long.valueOf(new SimpleDateFormat("yyMMddhhmmssSSS")
						.format(new Date()).toString()) * 10000;
				if (tmpID < ltime) {
					tmpID = ltime;
				} else {
					tmpID = tmpID + 1;
					ltime = tmpID;
				}
				tmpIDlocked = false;
				return ltime;
			}
		}
	 }
}
