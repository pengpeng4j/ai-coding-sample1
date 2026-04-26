package com.ly.travel.aicoding.common.support;

import com.ly.travel.aicoding.common.util.DateTimeUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * 日期相关的映射
 *
 * @author peng2.peng
 * @version 1.0.0
 */
public class DateRelatedMapper {

    private DateRelatedMapper() {}

    public static LocalDate mapOfLocalDate(String date) {
        return StringUtils.isNotEmpty(date) ? LocalDate.parse(date, DateTimeUtils.COMPATIBLE_DATE_FORMATTER) : null;
    }

    public static String mapOfLocalDate(LocalDate date) {
        return date != null ? date.format(DateTimeUtils.DEFAULT_DATE_FORMATTER) : null;
    }

    public static LocalDateTime mapOfLocalDateTime(String datetime) {
        return StringUtils.isNotEmpty(datetime) ? DateTimeUtils.parse2DateTime(datetime) : null;
    }

    public static String mapOfLocalDateTime(LocalDateTime datetime) {
        return datetime != null ? datetime.format(DateTimeUtils.DEFAULT_DATETIME_FORMATTER) : null;
    }

    public static LocalTime mapOfLocalTime(String time) {
        return StringUtils.isNotEmpty(time) ? LocalTime.parse(time, DateTimeUtils.DEFAULT_TIME_FORMATTER) : null;
    }

    public static String mapOfLocalTime(LocalTime time) {
        return time != null ? time.format(DateTimeUtils.DEFAULT_TIME_FORMATTER) : null;
    }

    public static Date mapOfStringDate(String date) {
        LocalDateTime dateTime = mapOfLocalDateTime(date);
        return dateTime != null ? DateTimeUtils.from(dateTime) : null;
    }

    public static String mapOfStringDate(Date date) {
        return date != null ? DateTimeUtils.format(date, DateTimeUtils.DEFAULT_DATETIME_PATTERN) : null;
    }

    public static Date mapOfLongDate(Long date) {
        return date != null ? new Date(date) : null;
    }

    public static Long mapOfLongDate(Date date) {
        return date != null ? date.getTime() : null;
    }

}
