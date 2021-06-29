package com.prictice.util.other;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * <P> Description: 日期处理的支持类 </P>
 * 
 * @ClassName: DateUtil
 * @author 钟晓飞
 */
public class LklDateUtil {

	private LklDateUtil() {
	};

	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	/**
	 * yyyyMMddHHmmss
	 */
	public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	public static String YYYYMMDD = "yyyyMMdd";

	/**
	 * 
	 * <p>将日期字符串转化成日期类型</p>
	 * 
	 * @return
	 * @author #{刘晓辉 13421849365}
	 */
	public static Date parseDate(String dateStr, String parterm) {
		Date date = null;
		if (dateStr == null) {
			date = null;
		} else {
			DateFormat df = new SimpleDateFormat(parterm);
			try {
				date = df.parse(dateStr);
			} catch (ParseException e) {
				date = null;
			}
		}
		return date;
	}

	/**
	 * <p> 根据日期字符串获取当年日期为星期几 </p>
	 * 
	 * @param dateStr 比如0320 即当前年3月20号
	 * @return 当前的日期是星期几
	 * @author 钟晓飞
	 */
	public static String getWeekDayByDate(String dateStr) {
		if (StringUtils.isNotEmpty(dateStr)) {
			final int year = Calendar.getInstance().get(Calendar.YEAR);
			final int month = Integer.valueOf(dateStr.substring(0, 2)) - 1;
			final int day = Integer.valueOf(dateStr.substring(2, 4));
			final Calendar cal = Calendar.getInstance();
			cal.set(year, month, day);
			final int i = cal.get(Calendar.DAY_OF_WEEK);
			switch (i) {
				case 1:
					dateStr = "星期日";
					break;
				case 2:
					dateStr = "星期一";
					break;
				case 3:
					dateStr = "星期二";
					break;
				case 4:
					dateStr = "星期三";
					break;
				case 5:
					dateStr = "星期四";
					break;
				case 6:
					dateStr = "星期五";
					break;
				case 7:
					dateStr = "星期六";
					break;
			}
		}
		return dateStr;
	}

	/**
	 * <p> 获取两个日期点相差的天数 </p>
	 * 
	 * @param calendarA 日期点A
	 * @param calendarB 日期点B
	 * @return 日期相差的天数
	 * @author 钟晓飞
	 */
	public static int getIntervalDays(final Calendar calendarA, final Calendar calendarB) {
		final long mills = calendarA.getTimeInMillis() > calendarB.getTimeInMillis() ? calendarA.getTimeInMillis()
				- calendarB.getTimeInMillis() : calendarB.getTimeInMillis() - calendarA.getTimeInMillis();
		return (int) (mills / 1000L / 3600L / 24L);
	}

	/**
	 * <p>获取当前日期前一天，格式为YYYYMMDD</p>
	 */
	public static String getCurrentChnDateFront() {
		return new SimpleDateFormat("yyyyMMdd").format(addDays(new Date(), -1));
	}

	/**
	 * 获取当前日期字符串，格式为yyyyMMdd
	 */
	public static String getCurrentDate() {
		return new SimpleDateFormat("yyyyMMdd").format(new Date());
	}

	/**
	 * 获取当前时间字符串，格式为HHmmss
	 * 
	 * @return String
	 */
	public static String getCurrentTime() {
		return new SimpleDateFormat("HHmmss").format(new Date());
	}

	/**
	 * 获取当前时间字符串，格式为HHmmssSSSSSS
	 * 
	 * @return String
	 */
	public static String getCurrentTimeMillis() {
		return new SimpleDateFormat("HHmmssSSSSSS").format(new Date());
	}

	public static String getCurrentTime1() {
		return new SimpleDateFormat("HH:mm:ss").format(new Date());
	}

	public static String getCurrentDateTime() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}

	/**
	 * 获取对应Date的日期字符串，格式为yyyyMMddHHmmss
	 * 
	 * @param date
	 * @return
	 */

	public static String getDateTime(final Date date) {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(date);
	}

	/**
	 * 获取对应Date的时间字符串，格式为HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String getTime1(final Date date) {
		return new SimpleDateFormat("HH:mm:ss").format(date);
	}

	public static String getyyyyMMddHHmmssSSS() {
		return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
	}

	/**
	 * 获取当前的毫秒时间，为long值
	 * 
	 * @return long
	 */
	public static long getCurrentLongTime() {
		return new Date().getTime();
	}

	/**
	 * 获取当前时间的字符串，格式为yyyy-MM-dd HH:mm:ss
	 * 
	 * @return String
	 */
	public static String getCurrentPrettyDateTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	/**
	 * 获取对应Date的日期字符串，格式为yyyyMMdd
	 * 
	 * @param date 源Date
	 * @return
	 */
	public static String getDate(final Date date) {
		return new SimpleDateFormat("yyyyMMdd").format(date);
	}

	/**
	 * 获取对应Date的时间字符串，格式为HHmmss
	 * 
	 * @param date 源Date
	 * @return String
	 */
	public static String getTime(final Date date) {
		return new SimpleDateFormat("HHmmss").format(date);
	}

	/**
	 * 获取对应Date的日期字符串，格式为yyyy-MM-dd
	 * 
	 * @param date 源Date
	 * @return String
	 */
	public static String getPrettyDate(final Date date) {
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}

	/**
	 * 获取对应Date的字符串，格式为yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date 源Date
	 * @return String
	 */
	public static String getPrettyDateTime(final Date date) {
		if (date == null) {
			return null;
		}
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

	/**
	 * 获取Sybase的日期转换函数表达式，用于SQL中，格式是yyyy/MM/dd HH:mm:ss，没办法，Sybase不支持yyyy-MM-dd HH:mm:ss
	 * 
	 * @param field 要转换成yyyy/MM/dd HH:mm:ss格式字符串的日期类型字段名
	 * @return String
	 */
	public static String getSybaseConvertSQL(final String field) {
		return "(CONVERT(varchar(12), " + field + ", 111) + ' ' + CONVERT(varchar(12), " + field + ", 108))";
	}

	/**
	 * 获取Sybase的日期转换函数表达式，用于SQL中，格式是yyyy/MM/dd HH:mm:ss，没办法，Sybase不支持yyyy-MM-dd HH:mm:ss
	 * 
	 * @param field 要转换成yyyy/MM/dd HH:mm:ss格式字符串的日期类型字段名
	 * @return String
	 */
	public static String getSybaseYYYYMMDD(final String field) {
		return "(CONVERT(varchar(12), " + field + ", 112))";
	}

	/**
	 * 获取一年的所有日期列表
	 * 
	 * @param year 年份
	 * @return ArrayList
	 */
	@SuppressWarnings("deprecation")
	public static List<Date> getAllDates(final String year) {
		final ArrayList<Date> list = new ArrayList<Date>();
		int intYear;
		try {
			intYear = Integer.parseInt(year);
		} catch (final NumberFormatException e) { // 如果不是合法的年份，返回空的列表
			return list;
		}
		intYear = intYear - 1900; // 需要减去1900
		for (int month = 0; month < 12; month++) {
			for (int day = 1; day < 32; day++) {
				final Date date = new Date(intYear, month, day);
				if (!list.contains(date)) {
					list.add(date);
				}
			}
		}
		Collections.sort(list);
		return list;
	}

	/*
	 * 获取当前日期，格式为XXXX年XX月XX日
	 */
	public static String getCurrentChnDate() {
		String strDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
		strDate = strDate.substring(0, 4) + "年" + strDate.substring(4, 6) + "月" + strDate.substring(6) + "日";
		return strDate;
	}

	/*
	 * 将日期转换为格式XXXX年XX月XX日
	 */
	public static String getChnDate(final Date date) {
		String strDate = new SimpleDateFormat("yyyyMMdd").format(date);
		strDate = strDate.substring(0, 4) + "年" + strDate.substring(4, 6) + "月" + strDate.substring(6) + "日";
		return strDate;
	}

	/*
	 * 将字符串'yyyyMMdd'日期转换为格式XXXX年XX月XX日
	 */
	public static String getChnDate(final String strDate) {
		return strDate.substring(0, 4) + "年" + strDate.substring(4, 6) + "月" + strDate.substring(6) + "日";
	}

	/**
	 * 检查日期字符串是否合法
	 * 
	 * @param dateStr 日期字符串
	 * @param pattern 日期格式
	 * @return 布尔
	 */
	public static boolean isValidDate(final String dateStr, final String pattern) {
		final SimpleDateFormat df = new SimpleDateFormat(pattern);
		// 来强调严格遵守该格式
		// df.setLenient(false);
		df.setLenient(true);
		try {
			df.parse(dateStr);
		} catch (final ParseException e) {
			return false;
		}
		return true;
	}

	/**
	 * 检查日期字符串是否合法
	 *
	 * @param dateStr 日期字符串
	 * @param pattern 日期格式
	 * @return 布尔
	 */
	public String toStringDate(final String dateStr) {
		final SimpleDateFormat df = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
		Date date = null;
		try {
			date = df.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date.toString();
	}

	public static int getMaxdayInAMonth(final int year, final int month) {
		final Calendar time = Calendar.getInstance();
		time.clear();
		time.set(Calendar.YEAR, year); // year 为 int
		time.set(Calendar.MONTH, month - 1);// 注意,Calendar对象默认一月为0
		final int day = time.getActualMaximum(Calendar.DAY_OF_MONTH);// 本月份的天数
		// 注：在使用set方法之前，必须先clear一下，否则很多信息会继承自系统当前时间
		return day;

	}

	// Calendar转化为Date
	public static Date CalendarToDate() {
		final Calendar cal = Calendar.getInstance();
		final Date date = cal.getTime();
		return date;
	}

	// Date转化为Calendar
	public static Calendar DateToCalendar() {
		final Date date = new Date();
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	/**
	 * 按日加减日期
	 *
	 * @param date：日期
	 * @param num：要加减的日数
	 * @return：成功，则返回加减后的日期；失败，则返回null
	 */
	public static Date addDays(final Date date, final int num) {
		if (date == null) {
			return null;
		}

		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, num);

		return c.getTime();
	}

	/**
	 * 按月加减日期
	 *
	 * @param date：日期
	 * @param num：要加减的月数
	 * @return：成功，则返回加减后的日期；失败，则返回null
	 */
	public static Date addMonths(final Date date, final int num) {
		if (date == null) {
			return null;
		}
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, num);
		return c.getTime();
	}

	/**
	 * 按年加减日期
	 *
	 * @param date：日期
	 * @param num：要加减的年数
	 * @return：成功，则返回加减后的日期；失败，则返回null
	 */
	public static Date addYears(final Date date, final int num) {
		if (date == null) {
			return null;
		}
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, num);
		return c.getTime();
	}

	/**
	 * 按秒 加减日期
	 *
	 * @param date：日期
	 * @param num：要加减的秒
	 * @return：成功，则返回加减后的日期；失败，则返回null
	 */
	public static Date addSeconds(final Date date, final int num) {
		if (date == null) {
			return null;
		}
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.SECOND, num);
		return c.getTime();
	}

	public static Date getDate(final String dateStr, final String dateFormat) throws Exception {
		return new SimpleDateFormat(dateFormat).parse(dateStr);
	}

	public static Date getDate(final String dateStr) throws Exception {
		return new SimpleDateFormat("yyyyMMddHHmmss").parse(dateStr);
	}

	public static Date convertTimerToDate(final Timestamp time) throws Exception {
		return new Date(time.getTime());
	}

	public static long DateDiff(final Date date1, final Date date2) throws Exception {
		return date1.getTime() - date2.getTime();
	}

	/**
	 * 获取当前日期上一个月的第一天，格式为yyyy-MM-dd hh:ms:ss
	 *
	 * @return String
	 */
	public static String getLastMonthFirstDay(final Date date) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return getPrettyDateTime(calendar.getTime());
	}

	/**
	 * 获取当前日期上一个月的第一天，格式为yyyy-MM-dd hh:ms:ss
	 *
	 * @return String
	 */
	public static String getLastMonthEndDay(final Date date) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return getPrettyDateTime(calendar.getTime());
	}

	public static String getISO8601Fmt() {
		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date());
	}

	/**
	 *
	 * <p> 转换日期字符串从原格式到目标格式 </p> <p> 如：传入
	 * dateStr:1211,srcFormat:yyMM,destFormat:yyyyMM;返回格式为:20121211 </p>
	 *
	 * @param dateStr 待转换的日期字符串
	 * @param srcFormat 原日期字符串格式
	 * @param destFormat 目标日期字符串格式
	 * @return
	 * @author 钟晓飞
	 * @throws ParseException
	 */
	public static String getFormatDateStr(String dateStr, String srcFormat, String destFormat) throws ParseException {
		if (dateStr == null || dateStr.length() == 0)
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat(srcFormat);
		Date date = sdf.parse(dateStr);
		sdf.applyPattern(destFormat);
		return sdf.format(date);
	}

	/**
	 *
	 * <p>获取两个日期相差的天数</p>
	 *
	 * @param smdate
	 * @param bdate
	 * @return
	 * @throws ParseException
	 * @author 程学新 2014-6-9 下午12:59:03
	 */
	public static int daysBetween(Date smdate, Date bdate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			smdate = sdf.parse(sdf.format(smdate));
			bdate = sdf.parse(sdf.format(bdate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	public static String converDateToStr(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";
		if (aDate != null) {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}
		return (returnValue);
	}

	/**
	 * Return default datePattern (yyyy-MM-dd)
	 *
	 * @return a string representing the date pattern on the UI
	 */
	public static String getDatePattern() {
		return "yyyy-MM-dd";
	}

	public static String getDateTimePattern() {
		return LklDateUtil.getDatePattern() + " HH:mm:ss.S";
	}

	/**
	 * This method generates a string representation of a date's date/time in the format you specify
	 * on input
	 *
	 * @param aMask the date pattern the string is in
	 * @param aDate a date object
	 * @return a formatted string representation of the date
	 *
	 * @see SimpleDateFormat
	 */
	public static String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date based on the System Property
	 * 'dateFormat' in the format you specify on input
	 * 
	 * @param aDate A date to convert
	 * @return a string representation of the date
	 */
	public static String convertDateToString(Date aDate) {
		return getDateTime(getDatePattern(), aDate);
	}

	/**
	 * <p>getAge</p>
	 * 
	 * @param certNo
	 * @return
	 * @author 杨玲玲 2014-9-28 下午4:48:30
	 */
	public static int getAge(String certNo) {
		Calendar c = Calendar.getInstance();
		String now = new SimpleDateFormat("yyyyMMdd").format(c.getTime());
		String certNoStr = certNo.substring(6, 14);
		Long l = Long.valueOf(now) - Long.valueOf(certNoStr);
		String s = String.valueOf(l).substring(0, 2);
		int age = Integer.parseInt(s);
		return age;
	}
	
	 /**
     * 返回当前日期的制定格式的字符串表达式
     * regx 可以为 yyyy-MM-dd  ,yyyy-MM-dd HH:MM, yyyy-MM-dd hh:mm:ss .....
     * @param regx
     * @return
     */
    public static String getNowDate(String regx){
    	SimpleDateFormat df=new SimpleDateFormat(regx==null?"yyyy-MM-dd HH:mm:ss":regx);
    	return df.format(new Date());
    }
    
    public static Date dateChange(String dateStr){
     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     Date date = null;
     try {
		   date = sdf.parse(dateStr);
	     } catch (ParseException e) {
		   e.printStackTrace();
	  }
     return date;
    }
    
    public static Date tomorrowDate(String dateStr){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
   		   date = sdf.parse(dateStr);
   		   Calendar cal = Calendar.getInstance();
   		            cal.setTime(date);
   		            cal.add(Calendar.DATE, 1);
   	     } catch (ParseException e) {
   		   e.printStackTrace();
   	  }
        return date;
    }
    
    /**
	 * <p>获取当前日期前一天，格式为yyyy-MM-dd HH:mm:ss</p>
	 */
	public static String getLast24HDate() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(addDays(new Date(), -1));
	}
	
}
