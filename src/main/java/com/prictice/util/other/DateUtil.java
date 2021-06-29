package com.prictice.util.other;

import org.apache.commons.lang3.time.DateUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class DateUtil extends DateUtils {
    /**定义常量**/
    public static final String DATE_JFP_STR="yyyyMM";
    public static final String DATE_FULL_STR = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_SMALL_STR = "yyyy-MM-dd";
    public static final String DATE_KEY_STR = "yyMMddHHmmss";
    
    
    /**
     * 使用预设格式提取字符串日期
     * @param strDate 日期字符串
     * @return
     */
    public static Date parse(String strDate) {
        return parse(strDate,DATE_FULL_STR);
    }
     
    /**
     * 使用用户格式提取字符串日期
     * @param strDate 日期字符串
     * @param pattern 日期格式
     * @return
     */
    public static Date parse(String strDate, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 使用用户格式提取字符串日期
     * @param date 日期字符串
     * @param pattern 日期格式
     * @return
     */
    public static Date parse(Date date, String pattern) {   
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(date.toString());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 使用用户格式提取字符串日期
     * @param date 日期字符串
     * @param pattern 日期格式
     * @return
     */
    public static String format(Date date, String pattern) {   
        SimpleDateFormat df = new SimpleDateFormat(pattern);
         return df.format(date);
    }
    
     
    /**
     * 两个时间比较
     * @param date1
     * @return
     */
    public static int compareDateWithNow(Date date1){
        Date date2 = new Date();
        int rnum =date1.compareTo(date2);
        return rnum;
    }
     
    /**
     * 两个时间比较(时间戳比较)
     * @param date1
     * @return
     */
    public static int compareDateWithNow(long date1){
        long date2 = dateToUnixTimestamp();
        if(date1>date2){
            return 1;
        }else if(date1<date2){
            return -1;
        }else{
            return 0;
        }
    }
     
 
    /**
     * 获取系统当前日期
     * @return
     */
    public static String getCurrDate() {
        SimpleDateFormat df = new SimpleDateFormat(DATE_SMALL_STR);
        return df.format(new Date());
    }
    
    /**
     * 获取系统当前日期
     * @return
     */
    public static Date getCurrSysDate() {
        SimpleDateFormat df = new SimpleDateFormat(DATE_SMALL_STR);
        Date date = null;
        try {
            date = df.parse(df.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
        
    }
    
    /**
     * 获取系统时间
     * @return
     */
    public static String getCurrTime() {
        SimpleDateFormat df = new SimpleDateFormat(DATE_FULL_STR);
        return df.format(new Date());
    }
     
    /**
     * 获取系统当前日期
     * @return
     */
    public static Date getCurrSysTime() {
        SimpleDateFormat df = new SimpleDateFormat(DATE_FULL_STR);
        Date date = null;
        try {
            date = df.parse(df.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
        
    }
     
    /**
     * 获取系统当前计费期
     * @return
     */
    public static String getJFPTime() {
        SimpleDateFormat df = new SimpleDateFormat(DATE_JFP_STR);
        return df.format(new Date());
    }
     
    /**
     * 将指定的日期转换成Unix时间戳
     * @param date 需要转换的日期 yyyy-MM-dd HH:mm:ss
     * @return long 时间戳
     */
    public static long dateToUnixTimestamp(String date) {
        long timestamp = 0;
        try {
            timestamp = new SimpleDateFormat(DATE_FULL_STR).parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp;
    }
     
    /**
     * 将指定的日期转换成Unix时间戳
     * @param date 需要转换的日期 yyyy-MM-dd
     * @return long 时间戳
     */
    public static long dateToUnixTimestamp(String date, String dateFormat) {
        long timestamp = 0;
        try {
            timestamp = new SimpleDateFormat(dateFormat).parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp;
    }
     
    /**
     * 将当前日期转换成Unix时间戳
     * @return long 时间戳
     */
    public static long dateToUnixTimestamp() {
        long timestamp = System.currentTimeMillis();
        return timestamp;
    }
     
     
    /**
     * 将Unix时间戳转换成日期
     * @param long timestamp 时间戳
     * @return String 日期字符串
     */
    public static String unixTimestampToDate(long timestamp) {
        SimpleDateFormat sd = new SimpleDateFormat(DATE_FULL_STR);
        sd.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return sd.format(new Date(timestamp));
    }
    
    public static Date getFollowDate(int days){
        Date currdt = new Date();
        Date followdt = null;
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FULL_STR);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, days);//
        currdt = calendar.getTime();
        try {
            followdt = sdf.parse(sdf.format(currdt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return followdt;
    }
    /**
     * 转化格式(开始时间)成时间戳
     * @param Object
     * @return TimeStamp
     */
    public static Timestamp startTime(String startTime){
        
        String str=startTime.toString()+" 00:00:00";
        return Timestamp.valueOf(str);
    }
    /**
     * 转化格式(截止时间)成时间戳
     * @param Object
     * @return TimeStamp
     */
    public static Timestamp endTime(String endTime){
        
        String str=endTime.toString()+" 23:59:59";
        return Timestamp.valueOf(str);
    }
    
    
    /**
     * 获取当期日期上一个月
     * @return    yyyyMM
     */
    public static String getLastMonth() {
        SimpleDateFormat df = new SimpleDateFormat(DATE_JFP_STR);
        return df.format(addMonths(new Date(),-1));
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
     * 获取当前日期上一个月的第一天
     * 
     * @return Date
     */
    public static Date getLastMonthFirstDay(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取当前日期上一个月的最后一天
     * 
     * @return Date
     */
    public static Date getLastMonthEndDay(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }
    
    /**
     * 两个时间比较(时间戳比较)
     * @param date
     * @return
     */
    public static boolean compareDate(Date date1,Date date2){
        long d1 = date1.getTime();
        long d2 = date2.getTime();
        if(d1 > d2){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * @两个时间比较(时间戳比较)
     * @d1>=d2时候返回true
     * @param date
     * @return
     */
    public static boolean comDate(Date date1,Date date2){
        long d1 = date1.getTime();
        long d2 = date2.getTime();
        if(d1 >= d2){
            return true;
        }else{
            return false;
        }
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
    
    
    /*
     * 将日期转换为格式XXXX年XX月XX日
     */
    public static String getChnDate(final Date date) {
        String strDate = new SimpleDateFormat("yyyyMMdd").format(date);
        strDate = strDate.substring(0, 4) + "年" + strDate.substring(4, 6) + "月" + strDate.substring(6) + "日";
        return strDate;
    }
    
    
    public static Date calculateEndDate(Date processDate,int onWorkDays) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar batchDay = Calendar.getInstance();
        batchDay.setTime(processDate);
        long times = 24*60*60*1000L*onWorkDays;
        Date date = df.parse(df.format(batchDay.getTimeInMillis()+times));
        return date;
    }
    
    /**
     * 获取息费减免有效日期(yyyymmdd)
     * @param date
     * @param onWorkDays
     * @return
     * @throws Exception
     */
    public static String getValidDate(Date date,int validDays) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        long times = 24*60*60*1000L*validDays;
        String validDate = df.format(calendar.getTimeInMillis()+times);
        return validDate;
    }
    
    /**
     * 计算有效天数(yyyymmdd)
     * @param date(申请时间)
     * @param onWorkDays(当前业务日期)
     * @return
     * @throws Exception
     */
    public static long getValidDays(Date date1,Date date2) throws Exception {
        long count = 0;
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        count = (calendar2.getTimeInMillis()-calendar1.getTimeInMillis())/(24*60*60*1000);
        return count;
    }
    
    
    public static Date getCurrProcessTime(Date bizDate) {
        Date currTime = new Date();
        DateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
        DateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        String crTime = sdfTime.format(currTime);
        String crDate = sdfDate.format(bizDate);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currdate = null;
        try {
            currdate = df.parse(crDate.concat(" ").concat(crTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return currdate;
    }
    
    public static String getCCFollowDate(int days,Date date){
        if (date == null) {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        Date followDate = calendar.getTime();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(followDate);
    }
    
    public static Date getAfterFewMinutes(Date date,int minute){
        if (date == null) {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);
        Date followDate = calendar.getTime();
        return followDate;
    }
    
    /** 
     * @Description 当前时间+有效期推算出减免到日期
     * @param 
     * @return
     */
    public static Date calculateDerateDate(Date currDate,int validDays){
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currDate);
        calendar.add(Calendar.DATE, validDays-1);
        currDate = calendar.getTime();
        return currDate;
    }
    
    // 获得本月第一天
    public static Date getMonthBeg(Date date) {  
        Calendar cal = Calendar.getInstance(); 
        cal.setTime(date);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);  
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));  
        return  cal.getTime();  
    } 

    // 获得本月最后一天  
    public static Date getMonthEnd(Date date) {  
        Calendar cal = Calendar.getInstance(); 
        cal.setTime(date);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);  
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));    
        return cal.getTime();  
    } 
    
    public static void main(String[] args) {
      /*  Date date = calculateDerateDate(new Date(),3);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      	System.out.println(getMonthBeg(new Date()));*/
    	System.out.println(getCurrProcessTime(new Date()).getTime());
    	System.out.println(new Date().getTime());
    }

}
