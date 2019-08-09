package com.newlandpay.newretail.appstore.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author chenkai
 * @Description: 基于JAVA8的新日期api实现
 * @date 2017/4/7
 */
public class NewDateUtils {

    public static final String DEFAULT_FMT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_FMT_T = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DEFAULT_FMT_SHORT = "yyyyMMddHHmmss";

    private static ZoneId DEFAULT_ZONEID = ZoneId.systemDefault();


    //获取整点时间
    public static LocalDateTime wholeTime(){
        LocalDateTime nowTime = LocalDateTime.now();
        LocalDateTime retTime = LocalDateTime.of(nowTime.getYear(), nowTime.getMonthValue(), nowTime.getDayOfMonth(),
                nowTime.getHour(), 0 ,0);
        return retTime;
    }
    //获取零点时间
    public static LocalDateTime wholeDay(){
        LocalDateTime nowTime = LocalDateTime.now();
        LocalDateTime retTime = LocalDateTime.of(nowTime.getYear(), nowTime.getMonthValue(), nowTime.getDayOfMonth(),
                0, 0 ,0);
        return retTime;
    }

    public static LocalDateTime minuteDelta( int delta){
        LocalDateTime nowTime = LocalDateTime.now();
        return minuteDelta(nowTime, delta);
    }

    public static LocalDateTime minuteDelta(LocalDateTime time, int delta){
        return time.plusMinutes(delta);
    }

    //当前时间的若干小时之前或之后
    public static LocalDateTime hoursDelta(int delta){
        LocalDateTime nowTime = LocalDateTime.now();
        return hoursDelta(nowTime, delta);
    }

    //指定时间的若干小时之前或之后
    public static LocalDateTime hoursDelta(LocalDateTime time, int hoursDelta){
        return time.plusHours(hoursDelta);

    }

    //当前时间的若干天之前或之后
    public static LocalDateTime daysDelta(int daysDelta){
        LocalDateTime nowTime = LocalDateTime.now();
        return daysDelta(nowTime, daysDelta);
    }
    //指定时间的若干天之前或之后
    public static LocalDateTime daysDelta(LocalDateTime time, int daysDelta){
            return time.plusDays(daysDelta);
    }


    //当前时间的若干月之前或之后
    public static LocalDateTime monthsDelta(int monthsDelta){
        LocalDateTime nowTime = LocalDateTime.now();
        return monthsDelta(nowTime, monthsDelta);
    }
    //指定时间的若干月之前或之后
    public static LocalDateTime monthsDelta(LocalDateTime time, int monthsDelta){
        return time.plusMonths(monthsDelta);
    }








    public static boolean isToday(LocalDate date){
        return LocalDate.now().equals(date);
    }

    public static boolean beforeToday(LocalDate date){
        return LocalDate.now().isAfter(date);
    }
    public static boolean beforeNow(LocalDateTime datetime){
        return LocalDateTime.now().isAfter(datetime);
    }

    public static boolean afterToday(LocalDate date){
        return !beforeToday(date);
    }
    public static boolean afterNow(LocalDateTime datetime){
        return !beforeNow(datetime);
    }

    public static LocalDate lastDay(){
        return LocalDate.now().minusDays(1);
    }

    public static int compareDays(LocalDate startDay, LocalDate endDay){
        Period period = Period.between(startDay, endDay);
        return period.getDays();
    }

    public static String format(){
        return format(null, null);
    }
    public static String format(String pattern){
        return format(null, pattern);
    }

    public static String format(LocalDateTime dateTime){
        return format(dateTime, null);
    }
    public static String format(LocalDateTime dateTime, String pattern){
        if(dateTime==null)
            dateTime = LocalDateTime.now();

        if(pattern==null || pattern.trim().equals(""))
            pattern = DEFAULT_FMT;
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(fmt);
    }

    public static String formatDate(Date date, String pattern){
        return format(toLocalDateTime(date), pattern);
    }
    public static String formatDate(Date date){
        return format(toLocalDateTime(date), DEFAULT_FMT);
    }


    public static LocalDateTime converToLocalDateTime(String strDate){
        return converToLocalDateTime(strDate, null);
    }

    public static LocalDateTime converToLocalDateTime(String strDate,String pattern ){
        if(pattern==null || pattern.trim().equals(""))
            pattern = DEFAULT_FMT;
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(strDate, fmt);
    }

    public static Date converToDate(String strDate,String pattern ){
        LocalDateTime localDateTime = converToLocalDateTime(strDate, pattern);
        return toDate(localDateTime);
    }

    /**
     * 把java.util.Date转化为java.time.LocalDate
     * @param date
     * @return
     */
    public static LocalDate toLocalDate(Date date){
        Instant instant = date.toInstant();
        return instant.atZone(DEFAULT_ZONEID).toLocalDate();
    }

    /**
     * 把java.util.Date转化为java.time.LocalDateTime
     * @param date
     * @return
     */
    public static LocalDateTime toLocalDateTime(Date date){
        Instant instant = date.toInstant();
        return instant.atZone(DEFAULT_ZONEID).toLocalDateTime();
    }

    //   LocalDateTime <---> ZoneDateTime <--->  Instant <---> Date
    public static Date toDate(LocalDateTime localDateTime){
        Instant instant = localDateTime.atZone(DEFAULT_ZONEID).toInstant();
        return Date.from(instant);
    }

    public static LocalDateTime fromEpochMilli(long epochMilli){
        Instant instant = Instant.ofEpochMilli(epochMilli);
        return instant.atZone(DEFAULT_ZONEID).toLocalDateTime();
    }

    public static String convertFormat(String srcDate, String srcPattern, String tgtPattern){
        DateTimeFormatter df = DateTimeFormatter.ofPattern(srcPattern);
        LocalDateTime l = LocalDateTime.parse(srcDate, df);
        df = DateTimeFormatter.ofPattern(tgtPattern);
        return df.format(l);
    }

    public static long timestampSecond(){
        return Instant.now().getEpochSecond();
    }
}
