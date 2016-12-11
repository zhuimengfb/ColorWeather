package com.fbi.colorweather.utils;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 05/12/2016
 */

public class TemperatureUtils {

  public static String parseKToString(double k) {
    return String.valueOf(Math.round(k - 272.15));
  }
}
