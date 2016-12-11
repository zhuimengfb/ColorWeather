package com.fbi.colorweather.views;

import com.fbi.colorweather.entity.WeatherDailyInfo;

import java.util.List;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 05/12/2016
 */

public interface WeatherView {

  void showDescription(String description);

  void showLocation(String location);

  void showWeatherIcon(int res);

  void showBackground(int colorRes);

  void showTemperature(String temp);

  void showLastUpdateTime(long time);

  void updateForecastWeather(List<WeatherDailyInfo> weatherInfoList);
}
