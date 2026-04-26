package com.ly.travel.aicoding.common.util;

import org.springframework.util.Assert;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.TimeZone;

/**
 * 基于JDK8时间框架的日期时间处理工具类
 *
 * @author peng2.peng
 * @version 1.0.0
 */
public class DateTimeUtils {

	/**
	 * 默认时区：GMT+8(东八区时间)，即北京时间
	 */
	public static final TimeZone DEFAULT_TIME_ZONE = TimeZone.getTimeZone("GMT+8");

	/**
	 * 默认时区：UTC+8(东八区时间)，即北京时间
	 */
	public static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("UTC+8");

	/**
	 * 默认的日期格式: yyyy-MM-dd
	 */
	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

	/**
	 * 默认的日期格式: yyyy-MM-dd
	 */
	public static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";

	/**
	 * 默认的日期时间格式: yyyy-MM-dd HH:mm:ss
	 */
	public static final String DEFAULT_DATETIME_PATTERN = DEFAULT_DATE_PATTERN + " " + DEFAULT_TIME_PATTERN;

	/**
	 * 短日期时间格式: yyyyMMddHHmmss
	 */
	public static final String SHORT_DATETIME_PATTERN = "yyyyMMddHHmmss";

	/**
	 * 默认的JAVA8日期DateTimeFormatter
	 */
	public static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN);

	/**
	 * 默认的JAVA8时间DateTimeFormatter
	 */
	public static final DateTimeFormatter DEFAULT_TIME_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_TIME_PATTERN);

	/**
	 * 默认的JAVA8日期时间DateTimeFormatter
	 */
	public static final DateTimeFormatter DEFAULT_DATETIME_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_DATETIME_PATTERN);

	/**
	 * 多种格式兼容的日期DateTimeFormatter
	 * 兼容：1995-08-24, 1995/08/24
	 */
	public static final DateTimeFormatter COMPATIBLE_DATE_FORMATTER = new DateTimeFormatterBuilder()
			.appendPattern("[yyyy-MM-dd][yyyy/MM/dd]")
			.toFormatter();

	/**
	 * 多种格式兼容的日期时间DateTimeFormatter
	 * 兼容：1995-08-24 12:34:56，1995-08-24 12:34:56.0，1995-08-24 12:34:56.123，1995-08-24 12:34:56.123Z，1995-08-24T12:34:56，1995-08-24T12:34:56.123,1995-08-24T12:34:56.123Z
	 *      1995/08/24 12:34:56，1995/08/24 12:34:56.0，1995/08/24 12:34:56.123，1995/08/24 12:34:56.123Z，1995/08/24T12:34:56，1995/08/24T12:34:56.123,1995/08/24T12:34:56.123Z
	 */
	public static final DateTimeFormatter COMPATIBLE_DATETIME_FORMATTER = new DateTimeFormatterBuilder()
			.optionalStart()
			.optionalStart()
			.appendPattern(DEFAULT_DATE_PATTERN)
			.optionalEnd()
			.optionalStart()
			.appendPattern("yyyy/MM/dd")
			.optionalEnd()
			.optionalStart()
			.appendLiteral(" ")
			.optionalEnd()
			.optionalStart()
			.appendLiteral("T")
			.optionalEnd()
			.appendPattern(DEFAULT_TIME_PATTERN)
			.optionalStart()
			.appendLiteral('.')
			.appendValue(ChronoField.MILLI_OF_SECOND, 1, 3, SignStyle.NORMAL)
			.optionalEnd()
			.optionalStart()
			.appendLiteral('Z')
			.optionalEnd()
			.optionalEnd()
			.toFormatter();

	/**
	 * 默认的纪元日期
	 */
	public static final String DEFAULT_ERA_DATE_STR = "1900-01-01";

	/**
	 * 默认的纪元日期时间
	 */
	public static final String DEFAULT_ERA_DATETIME_STR = "1900-01-01 00:00:00";

	/**
	 * 默认的纪元日期时间
	 */
	public static final LocalDateTime DEFAULT_ERA_DATETIME_OBJ = parse2DateTime(DEFAULT_ERA_DATETIME_STR);

	/**
	 * 默认的纪元日期时间
	 */
	public static final Date DEFAULT_ERA_DATE_OBJ = parse2Date(DEFAULT_ERA_DATETIME_STR);

	protected DateTimeUtils() {}

	/**
	 * 将指定的Date砍掉指定的部分
	 *
	 * @param date
	 * @param unit
	 * @return
	 */
	public static Date truncateTo(Date date, ChronoUnit unit) {
		if(date != null) {
			return Date.from(Instant.ofEpochMilli(date.getTime()).truncatedTo(unit));
		}
		return null;
	}

	/**
	 * 将指定的LocalDateTime砍掉指定的部分
	 *
	 * @param dateTime
	 * @param unit
	 * @return
	 */
	public static LocalDateTime truncateTo(LocalDateTime dateTime, ChronoUnit unit) {
		if(dateTime != null) {
			return dateTime.truncatedTo(unit);
		}
		return null;
	}

	/**
	 * <p>将@{code java.util.Date}转换为@{code java.time.LocalDateTime}
	 * 
	 * @param date
	 * @return
	 */
	public static LocalDateTime from(Date date){
		checkDate(date);
		return LocalDateTime.ofInstant(date.toInstant(), DEFAULT_ZONE_ID);
	}
	
	/**
	 * <p>将@{code java.util.Date}转换为@{code java.time.LocalDateTime}
	 * 
	 * @param dateTime
	 * @return
	 */
	public static Date from(LocalDateTime dateTime){
		checkDateTime(dateTime);
		return Date.from(dateTime.atZone(DEFAULT_ZONE_ID).toInstant());
	}
	
	/**
	 * <p>将@{code java.time.LocalDateTime}以指定的日期格式格式化为字符串</p>
	 * 
	 * @param dateTime
	 * @param pattern
	 * @return
	 */
	public static String format(LocalDateTime dateTime, String pattern){
		checkDateTime(dateTime);
		checkPattern(pattern);
		return dateTime.format(DateTimeFormatter.ofPattern(pattern));
	}
	
	/**
	 * <p>将@{code java.util.Date}以指定的日期格式格式化为字符串</p>
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date, String pattern){
		checkDate(date);
		checkPattern(pattern);
		return from(date).format(DateTimeFormatter.ofPattern(pattern));
	}
	
	/**
	 * <p>以指定的日期格式格式化当前时间</p>
	 * 
	 * @param pattern
	 * @return
	 */
	public static String formatNow(String pattern){
		checkPattern(pattern);
		return LocalDateTime.now(DEFAULT_ZONE_ID).format(DateTimeFormatter.ofPattern(pattern));
	}
	
	/**
	 * <p>以默认的日期格式(yyyy-MM-dd HH:mm:ss)格式化当前时间</p>
	 * 
	 * @return
	 */
	public static String formatNow(){
		return formatNow(DEFAULT_DATETIME_PATTERN);
	}

	/**
	 * <p>将字符串格式的日期转换为@{java.time.LocalDateTime}</p>
	 * 
	 * @param dateTime		- 日期字符串形式的值
	 * @param pattern		- 针对dateTimeText的日期格式
	 * @return
	 */
	public static LocalDateTime parse2DateTime(String dateTime, String pattern){
		Assert.hasText(dateTime, "Parameter 'dateTime' can not be empty!");
		checkPattern(pattern);
		return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(pattern));
	}

	/**
	 * <p>将字符串格式的日期转换为@{java.time.LocalDateTime}</p>
	 * 兼容多种常见日期时间格式
	 *
	 * @param dateTime		- 日期字符串形式的值
	 * @return
	 */
	public static LocalDateTime parse2DateTime(String dateTime){
		Assert.hasText(dateTime, "Parameter 'dateTime' can not be empty!");
		return LocalDateTime.parse(dateTime, COMPATIBLE_DATETIME_FORMATTER);
	}

	/**
	 * <p>将字符串格式的日期转换为@{code java.util.Date}</p>
	 *
	 * @param dateTime			- 日期字符串形式的值
	 * @return
	 */
	public static Date parse2Date(String dateTime){
		return from(parse2DateTime(dateTime));
	}

	/**
	 * <p>将字符串格式的日期转换为@{code java.util.Date}</p>
	 * 
	 * @param dateTime			- 日期字符串形式的值
	 * @param pattern			- 针对dateTimeText的日期格式
	 * @return
	 */
	public static Date parse2Date(String dateTime, String pattern){
		return from(parse2DateTime(dateTime, pattern));
	}
	
	/**
	 * 检测dateTime的日期格式是否是pattern
	 * @param dateTime
	 * @param pattern
	 * @return
	 */
	public static boolean matchesDatePattern(String dateTime, String pattern) {
		if(dateTime != null){
			try {
				LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(pattern));
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}
	
	/**
	 * 标准化dateTimeText，将其他格式的日期格式统一标准化为yyyy-MM-dd 或 yyyy-MM-dd HH:mm:ss
	 * @param dateTime
	 * @return
	 */
	public static String normalizeDateTime(String dateTime) {
		if(dateTime != null) {
			dateTime = dateTime.replace("T", " ");
			dateTime = dateTime.replace("/", "-");
			dateTime = dateTime.replace("年", "-");
			dateTime = dateTime.replace("月", "-");
			dateTime = dateTime.replace("日", "");
			dateTime = dateTime.replace("时", ":");
			dateTime = dateTime.replace("分", ":");
			dateTime = dateTime.replace("秒", "");
		}
		return dateTime;
	}
	
	/**
	 * 毫秒时间戳转LocalDateTime
	 * @param timestamp
	 * @return
	 */
	public static LocalDateTime ofEpochMilli(long timestamp) {
	    return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), DEFAULT_ZONE_ID);
	}
	
	/**
	 * LocalDateTime转毫秒时间戳
	 * @param dateTime
	 * @return
	 */
	public static Long toEpochMilli(LocalDateTime dateTime) {
		return dateTime.atZone(DEFAULT_ZONE_ID).toInstant().toEpochMilli();
	}

	/**
	 * 获取指定日期时间的当天起始时间(例如：yyyy-MM-dd 00:00:00)
	 *
	 * @param dateTime
	 * @return
	 */
	public static LocalDateTime atStartOfDay(LocalDateTime dateTime) {
		return dateTime.truncatedTo(ChronoUnit.DAYS);
	}

	/**
	 * 获取指定日期时间的当天起始时间(例如：yyyy-MM-dd 00:00:00)
	 *
	 * @param dateTime
	 * @return
	 */
	public static LocalDateTime atStartOfDay(LocalDate dateTime) {
		return dateTime.atTime(LocalTime.MIN);
	}

	/**
	 * 获取指定日期时间的当天起始时间(例如：yyyy-MM-dd 00:00:00)
	 *
	 * @param dateTime
	 * @return
	 */
	public static LocalDateTime atStartOfDay(Date dateTime) {
		return atStartOfDay(from(dateTime));
	}

	/**
	 * 获取今天起始时间(例如：yyyy-MM-dd 00:00:00)
	 *
	 * @return
	 */
	public static LocalDateTime atStartOfToday() {
		return atStartOfDay(LocalDateTime.now());
	}

	/**
	 * 获取指定日期时间的当天结束时间(例如：yyyy-MM-dd 23:59:59.999999999)
	 *
	 * @param dateTime
	 * @return
	 */
	public static LocalDateTime atEndOfDay(LocalDateTime dateTime) {
		return dateTime.toLocalDate().atTime(LocalTime.MAX);
	}

	/**
	 * 获取指定日期时间的当天结束时间(例如：yyyy-MM-dd 23:59:59.999999999)
	 *
	 * @param dateTime
	 * @return
	 */
	public static LocalDateTime atEndOfDay(LocalDate dateTime) {
		return dateTime.atTime(LocalTime.MAX);
	}

	/**
	 * 获取指定日期时间的当天结束时间(例如：yyyy-MM-dd 23:59:59.999999999)
	 *
	 * @param dateTime
	 * @return
	 */
	public static LocalDateTime atEndOfDay(Date dateTime) {
		return atEndOfDay(from(dateTime));
	}

	/**
	 * 获取今天结束时间(例如：yyyy-MM-dd 23:59:59.999999999)
	 *
	 * @return
	 */
	public static LocalDateTime atEndOfToday() {
		return atEndOfDay(LocalDateTime.now());
	}

	/**
	 * 获取指定日期时间的当月起始时间(例如：yyyy-MM-01 00:00:00)
	 *
	 * @param dateTime
	 * @return
	 */
	public static LocalDateTime atStartOfMonth(LocalDateTime dateTime) {
		return atStartOfMonth(dateTime.toLocalDate());
	}

	/**
	 * 获取指定日期时间的当月起始时间(例如：yyyy-MM-01 00:00:00)
	 *
	 * @param dateTime
	 * @return
	 */
	public static LocalDateTime atStartOfMonth(LocalDate dateTime) {
		return dateTime.with(TemporalAdjusters.firstDayOfMonth()).atTime(LocalTime.MIN);
	}

	/**
	 * 获取指定日期时间的当月起始时间(例如：yyyy-MM-01 00:00:00)
	 *
	 * @param dateTime
	 * @return
	 */
	public static LocalDateTime atStartOfMonth(Date dateTime) {
		return atStartOfMonth(from(dateTime));
	}

	/**
	 * 获取指定日期时间的当月结束时间(例如：yyyy-MM-30 23:59:59.999999999)
	 *
	 * @param dateTime
	 * @return
	 */
	public static LocalDateTime atEndOfMonth(LocalDateTime dateTime) {
		return atEndOfMonth(dateTime.toLocalDate());
	}

	/**
	 * 获取指定日期时间的当月结束时间(例如：yyyy-MM-30 23:59:59.999999999)
	 *
	 * @param dateTime
	 * @return
	 */
	public static LocalDateTime atEndOfMonth(LocalDate dateTime) {
		return dateTime.with(TemporalAdjusters.lastDayOfMonth()).atTime(LocalTime.MAX);
	}

	/**
	 * 获取指定日期时间的当月结束时间(例如：yyyy-MM-30 23:59:59.999999999)
	 *
	 * @param dateTime
	 * @return
	 */
	public static LocalDateTime atEndOfMonth(Date dateTime) {
		return atEndOfMonth(from(dateTime));
	}

	/**
	 * 获取指定日期时间的当周起始时间(例如：yyyy-MM-dd 00:00:00)
	 *
	 * @param dateTime
	 * @return
	 */
	public static LocalDateTime atStartOfWeek(LocalDateTime dateTime) {
		return atStartOfWeek(dateTime.toLocalDate());
	}

	/**
	 * 获取指定日期时间的当周起始时间(例如：yyyy-MM-dd 00:00:00)
	 *
	 * @param dateTime
	 * @return
	 */
	public static LocalDateTime atStartOfWeek(LocalDate dateTime) {
		return dateTime.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).atTime(LocalTime.MIN);
	}

	/**
	 * 获取指定日期时间的当周起始时间(例如：yyyy-MM-dd 00:00:00)
	 *
	 * @param dateTime
	 * @return
	 */
	public static LocalDateTime atStartOfWeek(Date dateTime) {
		return atStartOfWeek(from(dateTime));
	}

	/**
	 * 获取指定日期时间的当周结束时间(例如：yyyy-MM-dd 23:59:59.999999999)
	 *
	 * @param dateTime
	 * @return
	 */
	public static LocalDateTime atEndOfWeek(LocalDateTime dateTime) {
		return atEndOfWeek(dateTime.toLocalDate());
	}

	/**
	 * 获取指定日期时间的当周结束时间(例如：yyyy-MM-dd 23:59:59.999999999)
	 *
	 * @param dateTime
	 * @return
	 */
	public static LocalDateTime atEndOfWeek(LocalDate dateTime) {
		return dateTime.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).atTime(LocalTime.MAX);
	}

	/**
	 * 获取指定日期时间的当周结束时间(例如：yyyy-MM-dd 23:59:59.999999999)
	 *
	 * @param dateTime
	 * @return
	 */
	public static LocalDateTime atEndOfWeek(Date dateTime) {
		return atEndOfWeek(from(dateTime));
	}

	/**
	 * 返回指定日期时间与当前时间的间隔描述
	 * 返回规则：
	 * 1. 如果指定日期时间与当前时间间隔小于1分钟，则返回"刚刚"
	 * 2. 如果指定日期时间与当前时间间隔小于1小时，则返回"X分钟前"或"X分钟后"
	 * 3. 如果指定日期时间与当前时间间隔小于1天，则返回"X小时前"或"X小时后"
	 * 4. 如果指定日期时间与当前时间间隔小于1周，则返回"X天前"或"X天后"
	 * 5. 如果指定日期时间与当前时间间隔小于1个月，则返回"X周前"或"X周后"
	 * 6. 如果指定日期时间与当前时间间隔小于1年，则返回"X个月前"或"X个月后"
	 * 7. 否则返回指定日期时间的字符串表示
	 *
	 * @param dateTime - 指定日期时间
	 * @return - 间隔描述
	 */
	public static String getDurationOfNow(Date dateTime) {
		checkDate(dateTime);
		LocalDateTime target = from(dateTime);
		LocalDateTime now = LocalDateTime.now(DEFAULT_ZONE_ID);
		long seconds = ChronoUnit.SECONDS.between(target, now);
		boolean isPast = seconds >= 0;
		long absSec = Math.abs(seconds);
		if (absSec < 60) {
			return "刚刚";
		}
		long absMin = absSec / 60;
		if (absMin < 60) {
			return absMin + (isPast ? "分钟前" : "分钟后");
		}
		long absHour = absMin / 60;
		if (absHour < 24) {
			return absHour + (isPast ? "小时前" : "小时后");
		}
		long absDay = absHour / 24;
		if (absDay < 7) {
			return absDay + (isPast ? "天前" : "天后");
		}
		long absWeek = absDay / 7;
		if (absWeek < 4) {
			return absWeek + (isPast ? "周前" : "周后");
		}
		long absMonth = Math.abs(ChronoUnit.MONTHS.between(target, now));
		if (absMonth < 12) {
			return absMonth + (isPast ? "个月前" : "个月后");
		}
		return format(dateTime, DEFAULT_DATETIME_PATTERN);
	}

	private static void checkDate(Date date) {
		Assert.notNull(date, "Parameter 'date' can not be null!");
	}

	private static void checkDateTime(LocalDateTime dateTime) {
		Assert.notNull(dateTime, "Parameter 'dateTime' can not be null!");
	}

	private static void checkPattern(String pattern) {
		Assert.hasText(pattern, "Parameter 'pattern' can not be empty!");
	}

}