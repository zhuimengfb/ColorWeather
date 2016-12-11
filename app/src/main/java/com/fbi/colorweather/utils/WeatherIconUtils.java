package com.fbi.colorweather.utils;

import com.fbi.colorweather.R;
import com.fbi.colorweather.entity.Weather;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 06/12/2016
 */

public class WeatherIconUtils {

  public static int getResByType(Weather weather) {
    String description = weather.getDescription();
    switch (description) {
      case "overcast clouds":
        return R.mipmap.overcast_cloud;
      case "scattered clouds":
        return R.mipmap.scattered_cloud;
      case "broken clouds":
        return R.mipmap.cloud;
      case "few clouds":
        return R.mipmap.few_cloud;
      case "mist":
        return R.mipmap.mist;
      case "haze":
        return R.mipmap.haze;
      case "clear sky":
        return R.mipmap.sun;
      case "light rain":
        return R.mipmap.light_rain;
      case "moderate rain":
        return R.mipmap.rain;
      case "heavy intensity rain":
        return R.mipmap.heavy_rain;
      case "thunderstorm with light rain":
        return R.mipmap.thunderstorm_light_rain;
      case "thunderstorm with rain":
        return R.mipmap.thunderstorm_rain;
      default:
        return R.mipmap.cloud;
    }
  }
}
