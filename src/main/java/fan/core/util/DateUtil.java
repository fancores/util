package fan.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
/**
 * <p> ##################################################### </p>
 * <p> @描述：封装了与日期常用操作相关的工具类 </p>
 * <p> @作者：fancy </p>
 * <p> @邮箱：fancores@163.com </p>
 * <p> @日期：2014-05-20 </p>
 * <br> ##################################################### </p>
 */
public final class DateUtil {
	
	/** <p><b><em> 日期格式 </b></em></p> */
	public static final String DATE_PATTERN = "yyyy-MM-dd";
	/** <p><b><em> 日期时间格式 </b></em></p> */
	public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	private DateUtil(){
		
	}
	
	/** <p><b><em> 空间换时间, 确保 SimpleDateFormat 多线程安全 </b></em></p> */
	private static final ThreadLocal<SimpleDateFormat> THREADLOCAL = new ThreadLocal<SimpleDateFormat>(){
		
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat();
		}
		
	};
	
	/**
	 * <p><b><em> 获取 SimpleDateFormat 实例, 线程安全 </b></em></p>
	 */
	public static SimpleDateFormat getSimpleDateFormat(String pattern){
		SimpleDateFormat dateFormat = THREADLOCAL.get();
		dateFormat.applyPattern(pattern);
		return dateFormat;
	}
	
	/**
	 * <p><b><em> 获取当前日期 </b></em></p>
	 * <pre>
	 * >>> <b><em>e.g.</em></b>
	 * >>> DateUtil.getCurrentDate()
	 * >>> <b><em>output look like :</em></b>
	 * >>> 2014-05-20
	 * </pre>
	 */
	public static String getCurrentDate() {
		return getCurrentDateString(DATE_PATTERN);
	}
	
	/**
	 * <p><b><em> 获取当前日期时间 </b></em></p>
	 * <pre>
	 * >>> <b><em>e.g.</em></b>
	 * >>> DateUtil.getCurrentDateTime()
	 * >>> <b><em>output look like :</em></b>
	 * >>> 2014-05-20 11:02:24
	 * </pre>
	 */
	public static String getCurrentDateTime() {
		return getCurrentDateString(DATETIME_PATTERN);
	}
	
	/**
	 * <p><b><em> 获取日期串 </b></em></p>
	 * <pre>
	 * >>> <b><em>e.g.</em></b>
	 * >>> DateUtil.getCurrentDateString("yyyy-MM-dd HH:mm:ss:SSS")
	 * >>> <b><em>output look like :</em></b>
	 * >>> 2014-05-20 11:02:24:827
	 * </pre>
	 */
	public static String getCurrentDateString(String pattern) {
		return getSimpleDateFormat(pattern).format(new Date());
	}
	
	/**
	 * <p><b><em> 格式化日期 </b></em></p>
	 * <pre>
	 * >>> <b><em>e.g.</em></b>
	 * >>> DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss:SSS")
	 * >>> <b><em>output look like :</em></b>
	 * >>> 2014-05-20 11:05:14:757
	 * </pre>
	 */
	public static String formatDate(Date date, String pattern){
		return getSimpleDateFormat(pattern).format(date);
	}
	
	/**
	 * <p><b><em> 解析字符串为日期类型 </b></em></p>
	 * <pre>
	 * >>> <b><em>e.g.</em></b>
	 * >>> String dateString = "2014-05-20";
	 * >>> Date date = DateUtil.parseDate(dateString, DateUtil.DATE_PATTERN);
	 * </pre>
	 */
	public static Date parseDate(String date, String pattern){
		try {
			return getSimpleDateFormat(pattern).parse(date);
		} catch (ParseException e) {
			throw new ExecutetimeException(e);
		}
	}
	
	/**
	 * <p><b><em> 获取与当前时间相隔 interval 个单位的日期对象 </b></em></p>
	 * <pre>
	 * >>> <b><em>e.g.</em></b>
	 * >>> Date date = DateUtil.getDate(TimeUnit.HOURS, 2);
	 * >>> DateUtil.formatDate(date, DateUtil.DATETIME_PATTERN);
	 * >>> <b><em>output look like :</em></b>
	 * >>> 2014-05-20 13:55:47
	 * </pre>
	 */
	public static Date getDate(TimeUnit timeUnit, int interval) {
		return getDate(new Date(), timeUnit, interval);
	}
	
	/**
	 * <p><b><em> 获取与参数日期相隔 interval 个单位的日期对象 </b></em></p>
	 * <pre>
	 * >>> <b><em>e.g.</em></b>
	 * >>> Date date = DateUtil.getDate(new Date(), TimeUnit.HOURS, 2);
	 * >>> DateUtil.formatDate(date, DateUtil.DATETIME_PATTERN);
	 * >>> <b><em>output look like :</em></b>
	 * >>> 2014-05-20 13:55:47
	 * </pre>
	 */
	public static Date getDate(Date date, TimeUnit timeUnit, int interval) {
		int second = interval;
		switch (timeUnit) {
			case DAYS: 
				second = interval * 24 * 60 * 60; 
				break;
			case HOURS: 
				second = interval * 60 * 60; 
				break;
			case MINUTES:
				second = interval * 60;
				break;
			case MILLISECONDS: 
				second = interval / 1000; 
				break;
			case MICROSECONDS: 
				second = interval / 1000 / 1000; 
				break;
			case NANOSECONDS:
				second = interval / 1000 / 1000 / 1000; 
				break;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + second);
		return calendar.getTime();
	}
}