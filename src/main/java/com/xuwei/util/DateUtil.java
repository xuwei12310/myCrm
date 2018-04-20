package com.xuwei.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Description:日期处理函数
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-7
 * @author lys
 * @vesion 1.0
 */
public class DateUtil {

	/**
	 * @Description: 计算两日期相差的天数
	 * @Create: 2012-12-21 下午3:45:44
	 * @author lys
	 * @update logs
	 * @param fDate
	 * @param oDate
	 * @return
	 */
	public static int daysOfTwo(Date fDate, Date oDate) {
		long intervalMilli = oDate.getTime() - fDate.getTime();
	    int distDay= (int)(intervalMilli / (24 * 60 * 60 * 1000));
		return distDay;
	}
	
	/**
	 * @Description: 计算两日期相差的分钟数
	 * @Create: 2014-9-28 下午10:39:33
	 * @author jcf
	 * @update logs
	 * @param fDate
	 * @param oDate
	 * @return
	 */
	public static long minutesOfTwo(Date oDate, java.sql.Timestamp fDate) {
		long intervalMilli = oDate.getTime() - fDate.getTime();
	    long minutes= intervalMilli / (60 * 1000);
		return minutes;
	}

	/**
	 * @Description: 按pattern格式化输出Date
	 * @Create: 2012-12-21 下午3:53:48
	 * @author lys
	 * @update logs
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String dateToString(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/**
	 * @Description: 日期按yyyy-MM-dd格式化
	 * @Create: 2012-12-21 下午3:55:39
	 * @author lys
	 * @update logs
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date) {
		return dateToString(date, "yyyy-MM-dd");
	}

	/**
	 * @Description: 将类型是pattern的日期字符串转化成Date型数据
	 * @Create: 2013-1-14 下午11:58:24
	 * @author lys
	 * @update logs
	 * @param pattern
	 * @param dateString
	 * @return
	 * @throws ParseException
	 */
	public static Date toDate(String pattern, String dateString)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.parse(dateString);
	}

	/**
	 * @Description:将类型是yyyy-MM-dd的日期字符串转化成Date型数据
	 * @Create: 2013-1-14 下午11:59:34
	 * @author lys
	 * @update logs
	 * @param dateString
	 * @return
	 * @throws ParseException
	 */
	public static Date toDate(String dateString) throws ParseException {
		return toDate("yyyy-MM-dd", dateString);
	}
	/**
	 * @Description:将类型是yyyy-MM-dd的日期字符串转化成java.sql.Date型数据
	 * @Create: 2013-1-14 下午11:59:34
	 * @author lys
	 * @update logs
	 * @param dateString
	 * @return
	 * @throws ParseException
	 */
	public static java.sql.Date toSqlDate(String dateString)
			throws ParseException {
		return toSqlDate("yyyy-MM-dd", dateString);
	}

	/**
	 * @Description:将类型是pattern的日期字符串转化成java.sql.Date型数据
	 * @Create: 2013-1-14 下午11:59:34
	 * @author lys
	 * @update logs
	 * @param dateString
	 * @return
	 * @throws ParseException
	 */
	public static java.sql.Date toSqlDate(String pattern, String dateString)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = sdf.parse(dateString);
		return new java.sql.Date(date.getTime());
	}

	/**
	 * @Description: 取得当前时间的Timestamp
	 * @Created Time: 2013-4-16 下午4:40:59
	 * @Author lys
	 * @return
	 */
	public static Timestamp getNowTimestamp() {
		return new Timestamp(Calendar.getInstance().getTimeInMillis());
	}

	/**
	 * @Description: 取得当前时间的Timestamp,由于SqlServer 时间的精度为1/300秒 将 datetime 值舍入到
	 *               .000、.003、或 .007 秒的增量
	 *               http://www.fengfly.com/plus/view-172343-1.html
	 * @Created Time: 2013-4-16 下午4:40:59
	 * @Author lys
	 * @return
	 */
	public static Timestamp getNowTimestampSqlServer() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = Calendar.getInstance().getTime();
		try {
			return new Timestamp(df.parse(df.format(now)).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * @Description: 取得当前时间的Timestamp,由于SqlServer 时间的精度为1/300秒 将 datetime 值舍入到
	 *               .000、.003、或 .007 秒的增量
	 *               http://www.fengfly.com/plus/view-172343-1.html
	 * @Created Time: 2013-4-16 下午4:40:59
	 * @Author lys
	 * @return
	 */
	public static String getNowTimestampSqlServerStr() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = Calendar.getInstance().getTime();
		return df.format(now);
	}
	/**
	 * @description: 取得当前时间的yyyy-MM-dd HH:mm:ss
	 * @createTime: 2016年8月23日 下午3:59:34
	 * @author: lys
	 * @return
	 */
	public static String getNowTimestampStr() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = Calendar.getInstance().getTime();
		return df.format(now);
	}
	/**
	 * @description: 取得当前时间的yyyy-MM-dd HH:mm
	 * @createTime: 2016年8月23日 下午3:59:34
	 * @author: lys
	 * @return
	 */
	public static String getNowTimesminutStr() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = Calendar.getInstance().getTime();
		return df.format(now);
	}
	/**
	 * @description: 根据格式取得当前时间的         yyyy-MM-dd
	 * @createTime: 2017年9月6日 上午11:59:34
	 * @author: hhd
	 * @return
	 */
	public static String getNowTimestampStr(String format) {
		DateFormat df = new SimpleDateFormat(format);
		Date now = Calendar.getInstance().getTime();
		return df.format(now);
	}
	/**
	 * @Description: 将Date转化字符串
	 * @Create: 2015-1-5 下午4:06:25
	 * @author lys
	 * @update logs
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String toDateStr(Date date,String pattern){
		DateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}
	/**
	 * @Description: 计算n小时后的时间
	 * @Create: 2014-1-22 下午03:07:56
	 * @author jcf
	 * @update logs
	 * @param n
	 * @return
	 */
	public static Timestamp getNTimestampSqlServer(int n) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar=Calendar.getInstance();   
	    calendar.set(Calendar.HOUR_OF_DAY,calendar.get(Calendar.HOUR_OF_DAY)+n); 
	    Date now = calendar.getTime();
	    try {
	    	return new Timestamp(df.parse(df.format(now)).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @Description: 取得当前时间的Date
	 * @Created: 2013-8-18 下午5:46:52
	 * @Author lys
	 * @return
	 */
	public static Date getNowDate() {
		return Calendar.getInstance().getTime();
	}

	/**
	 * @Description: 取得当前时间的SqlDate
	 * @Created: 2013-8-18 下午5:46:52
	 * @Author lys
	 * @return
	 */
	public static java.sql.Date getNowSqlDate() {
		return new java.sql.Date(Calendar.getInstance().getTimeInMillis());
	}

	/**
	 * @Description: 日期加一天
	 * @Create: 2013-10-9 下午02:22:25
	 * @author jcf
	 * @update logs
	 * @param s
	 * @param n
	 * @return
	 */
	public static Date addDay(Date d, int n) {
		try {
			Calendar cd = Calendar.getInstance();
			cd.setTime(d);
			cd.add(Calendar.DATE, n);// 增加一天
			// cd.add(Calendar.MONTH, n);//增加一个月
			return cd.getTime();
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * @Description: 日期n秒
	 * @Create: 2015-6-1 下午01:22:15
	 * @author lhy
	 * @update logs
	 * @param d
	 * @param n
	 * @return
	 */
	public static Date addSecond(Date d, int n) {
		try {
			Calendar cd = Calendar.getInstance();
			cd.setTime(d);
			cd.add(Calendar.SECOND, n);
			return cd.getTime();
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * @Description: 比较两个字符串型的日期
	 * @Create: 2015-1-5 下午4:02:41
	 * @author lys
	 * @update logs
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int compareDate(String date1, String date2,String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception e) {
        	new RuntimeException(e.getMessage());
        }
        return 0;
    }
	/**
	 * @description 日期date1与date2比较
	 * @createTime 2015-5-30 上午9:38:21
	 * @author 以宋
	 * @updateLogs 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int compareDate(Date date1, Date date2) {
        try {
            if (date1.getTime() > date2.getTime()) {
                return 1;
            } else if (date1.getTime() < date2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception e) {
        	new RuntimeException(e.getMessage());
        }
        return 0;
    }
	/**
	 * @Description: 日期与当前日期相比
	 * @Create: 2015-1-5 下午4:11:18
	 * @author lys
	 * @update logs
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static int compareNow(String date,String pattern) {
		return compareDate(date,toDateStr(Calendar.getInstance().getTime(),pattern),pattern);
    }
	/**
	 * @Description: 与现在时间比较（学校时间（yyyy-MM-dd HH:mm））
	 * @Create: 2015-1-5 下午4:18:11
	 * @author lys
	 * @update logs
	 * @param date
	 * @return
	 */
	public static int compareNowInSchool(String date) {
		return compareNow(date,"yyyy-MM-dd HH:mm");
    }
	/**
	 * @description 比较当前日期
	 * @createTime 2015-5-30 上午9:37:54
	 * @author 以宋
	 * @updateLogs 
	 * @param date
	 * @return
	 */
	public static int compareNow(Date date) {
		return compareDate(date,new Date());
    }
	
	public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
	
	public static void main(String[] args) {
		System.out.println(getWeekOfDate(new Date()));
	}
}
