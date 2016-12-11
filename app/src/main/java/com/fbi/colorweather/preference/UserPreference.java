package com.fbi.colorweather.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fbi.colorweather.MyApp;
import com.fbi.colorweather.entity.WeatherDailyInfo;
import com.fbi.colorweather.entity.WeatherInfo;
import com.fbi.colorweather.utils.TimeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 05/12/2016
 */

public class UserPreference {

  private static final String KEY_PREFERENCES = "user_preference";
  private static volatile UserPreference userPrederence;
  private static SharedPreferences sharedPreferences;

  private static final String KEY_CURRENT_WEATHER = "key_current_weather";
  private static final String KEY_LAST_UPDATE_TIME = "key_last_update_time";
  private static final String KEY_FORECAST_WEATHER = "key_forecast_weather";
  private static final String KEY_FIRST_LAUNCH = "key_first_launch";

  private UserPreference() {
  }

  public static UserPreference getInstance() {
    if (userPrederence == null) {
      synchronized (UserPreference.class) {
        if (userPrederence == null) {
          userPrederence = new UserPreference();
          sharedPreferences = MyApp.getContext().getSharedPreferences(KEY_PREFERENCES, Context
              .MODE_PRIVATE);
        }
      }
    }
    return userPrederence;
  }

  public void updateCurrentWeather(String weather) {
    sharedPreferences.edit().putString(KEY_CURRENT_WEATHER, weather).apply();
  }

  public void updateLastUpdateTime(long time) {
    sharedPreferences.edit().putLong(KEY_LAST_UPDATE_TIME, time).apply();
  }

  public void updateForecastWeather(String content) {
    sharedPreferences.edit().putString(KEY_FORECAST_WEATHER, content).apply();
  }

  public List<WeatherDailyInfo> getForecastWeather() {
    String content = sharedPreferences.getString(KEY_FORECAST_WEATHER, "");
    ObjectMapper mapper = new ObjectMapper();
    List<WeatherDailyInfo> weatherInfos = new ArrayList<>();
    try {
      weatherInfos = mapper.readValue(content, mapper.getTypeFactory().constructParametricType
          (ArrayList.class, WeatherDailyInfo.class));
      Iterator<WeatherDailyInfo> it = weatherInfos.iterator();
      while (it.hasNext()) {
        WeatherDailyInfo weatherInfo = it.next();
        if (TimeUtils.isDayBefore(weatherInfo.getTime())) {
          it.remove();
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return weatherInfos;
  }

  public WeatherInfo getCurrentWeather() {
    String content = sharedPreferences.getString(KEY_CURRENT_WEATHER, "");
    ObjectMapper mapper = new ObjectMapper();
    WeatherInfo weatherInfo = null;
    try {
      weatherInfo = mapper.readValue(content, WeatherInfo.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return weatherInfo;
  }

  public long getLastUpdateTime() {
    return sharedPreferences.getLong(KEY_LAST_UPDATE_TIME, 0);
  }
}
