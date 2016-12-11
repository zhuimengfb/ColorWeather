package com.fbi.colorweather.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 06/12/2016
 */

public class TimeUtils {

  private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;

  public static String convertTime(long time) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd HH:mm", Locale.getDefault());
    return simpleDateFormat.format(new Date(time));
  }

  public static boolean isDayBefore(long time) {
    return new Date().getTime() - time * 1000 >= PERIOD_DAY;
  }

  public static String getForecastDay(long time) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd", Locale.getDefault());
    return simpleDateFormat.format(time * 1000);
  }
}
