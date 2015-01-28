package com.star72.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author larry
 */
public class StarDateUtils {
	
	/**
	 * 默认转换模式
	 */
	public static final String DEFAULT_PATTERN = "yyyy-MM-dd";
	
	private StringBuffer buffer = new StringBuffer();
	private static String ZERO = "0";
	private static StarDateUtils date;
	public static SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
	public static SimpleDateFormat format1 = new SimpleDateFormat(
			"yyyyMMdd HH:mm:ss");
	
	/**
	 * 字符串转日期:使用默认模式yyyy-MM-dd
	 * @param str 待转换的文本:2012-01-01
	 * @return
	 */
	public static Date parse2Date(String str) {
		return parse2Date(str, DEFAULT_PATTERN);
	}
	
	/**
	 * 字符串转日期
	 * @param str 待转换的文本:2012-01-01
	 * @param pattern 模式:yyyy-MM-dd
	 * @return
	 */
	public static Date parse2Date(String str, String pattern) {
		DateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取两个时间的差值秒
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static Integer getSecondBetweenDate(Date d1,Date d2){
		Long second=(d2.getTime()-d1.getTime())/1000;
		return second.intValue();
	}
	
	/**
	 * 
	 * @param date
	 *            指定比较日期
	 * @param compareDate
	 * @return
	 */
	public static boolean isInDate(Date date, Date compareDate) {
		if (compareDate.after(getStartDate(date))
				&& compareDate.before(getFinallyDate(date))) {
			return true;
		} else {
			return false;
		}

	}

	public String getNowString() {
		Calendar calendar = getCalendar();
		buffer.delete(0, buffer.capacity());
		buffer.append(getYear(calendar));

		if (getMonth(calendar) < 10) {
			buffer.append(ZERO);
		}
		buffer.append(getMonth(calendar));

		if (getDate(calendar) < 10) {
			buffer.append(ZERO);
		}
		buffer.append(getDate(calendar));
		if (getHour(calendar) < 10) {
			buffer.append(ZERO);
		}
		buffer.append(getHour(calendar));
		if (getMinute(calendar) < 10) {
			buffer.append(ZERO);
		}
		buffer.append(getMinute(calendar));
		if (getSecond(calendar) < 10) {
			buffer.append(ZERO);
		}
		buffer.append(getSecond(calendar));
		return buffer.toString();
	}

	private static int getDateField(Date date,int field) {
		Calendar c = getCalendar();
		c.setTime(date);
		return c.get(field);
	}
	public static int getYearsBetweenDate(Date begin,Date end){
		int bYear=getDateField(begin,Calendar.YEAR);
		int eYear=getDateField(end,Calendar.YEAR);
		return eYear-bYear;
	}
	
	public static int getMonthsBetweenDate(Date begin,Date end){
		int bMonth=getDateField(begin,Calendar.MONTH);
		int eMonth=getDateField(end,Calendar.MONTH);
		return eMonth-bMonth;
	}
	public static int getWeeksBetweenDate(Date begin,Date end){
		int bWeek=getDateField(begin,Calendar.WEEK_OF_YEAR);
		int eWeek=getDateField(end,Calendar.WEEK_OF_YEAR);
		return eWeek-bWeek;
	}
	public static int getDaysBetweenDate(Date begin,Date end){
		int bDay=getDateField(begin,Calendar.DAY_OF_YEAR);
		int eDay=getDateField(end,Calendar.DAY_OF_YEAR);
		return eDay-bDay;
	}
	
	
	public static void main(String args[]){
		System.out.println(getSpecficDateStart(new Date(), 288));
	}

	/**
	 * 获取date年后的amount年的第一天的开始时间
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficYearStart(Date date,int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, amount);
		cal.set(Calendar.DAY_OF_YEAR, 1);
		return getStartDate(cal.getTime());
	}

	/**
	 * 获取date年后的amount年的最后一天的终止时间
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficYearEnd(Date date,int amount) {
		Date temp = getStartDate(getSpecficYearStart(date,amount + 1));
		Calendar cal = Calendar.getInstance();
		cal.setTime(temp);
		cal.add(Calendar.DAY_OF_YEAR, -1);
		return getFinallyDate(cal.getTime());
	}

	/**
	 * 获取date月后的amount月的第一天的开始时间
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficMonthStart(Date date,int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, amount);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return getStartDate(cal.getTime());
	}

	/**
	 * 获取当前自然月后的amount月的最后一天的终止时间
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficMonthEnd(Date date,int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getSpecficMonthStart(date,amount + 1));
		cal.add(Calendar.DAY_OF_YEAR, -1);
		return getFinallyDate(cal.getTime());
	}

	/**
	 * 获取date周后的第amount周的开始时间（这里星期一为一周的开始）
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficWeekStart(Date date,int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.setFirstDayOfWeek(Calendar.MONDAY); /* 设置一周的第一天为星期一 */
		cal.add(Calendar.WEEK_OF_MONTH, amount);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return getStartDate(cal.getTime());
	}

	/**
	 * 获取date周后的第amount周的最后时间（这里星期日为一周的最后一天）
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficWeekEnd(Date date,int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY); /* 设置一周的第一天为星期一 */
		cal.add(Calendar.WEEK_OF_MONTH, amount);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return getFinallyDate(cal.getTime());
	}
	
	public static Date getSpecficDateStart(Date date,int amount){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, amount);
		return getStartDate(cal.getTime());
	}

	/**
	 * 得到指定日期的一天的的最后时刻23:59:59
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFinallyDate(Date date) {
		String temp = format.format(date);
		temp += " 23:59:59";

		try {
			return format1.parse(temp);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 得到指定日期的一天的开始时刻00:00:00
	 * 
	 * @param date
	 * @return
	 */
	public static Date getStartDate(Date date) {
		String temp = format.format(date);
		temp += " 00:00:00";

		try {
			return format1.parse(temp);
		} catch (Exception e) {
			return null;
		}
	}

	private int getYear(Calendar calendar) {
		return calendar.get(Calendar.YEAR);
	}

	private int getMonth(Calendar calendar) {
		return calendar.get(Calendar.MONDAY) + 1;
	}

	private int getDate(Calendar calendar) {
		return calendar.get(Calendar.DATE);
	}

	private int getHour(Calendar calendar) {
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	private int getMinute(Calendar calendar) {
		return calendar.get(Calendar.MINUTE);
	}

	private int getSecond(Calendar calendar) {
		return calendar.get(Calendar.SECOND);
	}

	private static Calendar getCalendar() {
		return Calendar.getInstance();
	}

	public static StarDateUtils getDateInstance() {
		if (date == null) {
			date = new StarDateUtils();
		}
		return date;
	}
	
	public static int getCurrentYear() {
		return getInfo(Calendar.YEAR);
	}
	
	public static int getCurrentMonth() {
		return getInfo(Calendar.MONTH) + 1;
	}
	
	public static int getCurrentDay() {
		return getInfo(Calendar.DATE);
	}
	
	public static int getCurrentDayOfWeek() {
		return getInfo(Calendar.DAY_OF_WEEK);
	}
	
	public static int getCurrentDayOfMonth() {
		return getInfo(Calendar.DAY_OF_MONTH);
	}
	
	public static int getCurrentDayOfYear() {
		return getInfo(Calendar.DAY_OF_YEAR);
	}
	
	private static int getInfo(int type) {
		Calendar cal = Calendar.getInstance();
		return cal.get(type);
	}
	
	public static int getYear(Date date) {
		return getInfo(date, Calendar.YEAR);
	}
	
	public static int getMonth(Date date) {
		return getInfo(date, Calendar.MONTH) + 1;
	}
	
	public static int getDay(Date date) {
		return getInfo(date, Calendar.DATE);
	}
	
	public static int getDayOfWeek(Date date) {
		return getInfo(date, Calendar.DAY_OF_WEEK);
	}
	
	public static int getDayOfMonth(Date date) {
		return getInfo(date, Calendar.DAY_OF_MONTH);
	}
	
	public static int getDayOfYear(Date date) {
		return getInfo(date, Calendar.DAY_OF_YEAR);
	}
	
	private static int getInfo(Date date, int type) {
		Calendar calendar = parseToCalendar(date);
		return calendar.get(type);
	}
	
	private static Calendar parseToCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}
}
