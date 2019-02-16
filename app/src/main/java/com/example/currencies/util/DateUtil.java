package com.example.currencies.util;

import android.annotation.SuppressLint;
import android.util.Log;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@SuppressWarnings({ "unused", "WeakerAccess" }) public final class DateUtil {

  public static final String TAG = DateUtil.class.getSimpleName();

  public static final String DATE_FORMAT_PATTERN_FROM_STRING = "dd.MM.yyyy";
  public static final String DATE_FORMAT_PATTERN_TO_STRING = "yyyyMMdd";

  public static long parseDateString(String date) {
    try {
      long time = getDateFormat(DATE_FORMAT_PATTERN_FROM_STRING).parse(date).getTime();
      Log.d(TAG, "parseDateString got time: " + time + " from string " + date);
      return time;
    } catch (ParseException e) {
      Log.e(TAG, "parseDateString got exception: " + e.toString());
      e.printStackTrace();
      return -1;
    }
  }

  public static Date getDate(int date, int month, int year) {
    return getCalendar(date, month, year).getTime();
  }

  public static long getDateInMilis(int date, int month, int year) {
    return getCalendar(date, month, year).getTimeInMillis();
  }

  public static String getDateRepresentation(long time) {
    return getDateRepresentation(DATE_FORMAT_PATTERN_TO_STRING, time);
  }

  public static String getDateRepresentation(String format, long time) {
    return getDateFormat(format).format(getCalendar(time).getTime());
  }

  /**
   * Prepare Calendar instance with given date, month and year
   *
   * @param date date of calendar
   * @param month month index of calendar (0-11)
   * @param year year of calendar
   * @return Calendar instance
   */
  public static Calendar getCalendar(int date, int month, int year) {
    Calendar instance = GregorianCalendar.getInstance();
    instance.set(year, month, date);
    return instance;
  }

  public static Calendar getCalendar(long time) {
    Calendar instance = GregorianCalendar.getInstance();
    instance.setTimeInMillis(time);
    return instance;
  }

  @SuppressLint("SimpleDateFormat") public static DateFormat getDateFormat(String pattern) {
    return new SimpleDateFormat(pattern);
  }

  public static long getCurrentDate() {
    Calendar calendar = getCalendar(System.currentTimeMillis());
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);

    return calendar.getTimeInMillis();
  }
}
