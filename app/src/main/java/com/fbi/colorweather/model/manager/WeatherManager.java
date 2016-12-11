package com.fbi.colorweather.model.manager;

import android.location.Location;

import com.fbi.colorweather.entity.WeatherForecast;
import com.fbi.colorweather.entity.WeatherInfo;
import com.fbi.colorweather.model.api.WeatherApi;

import rx.Observable;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 05/12/2016
 */

public class WeatherManager {

  private static WeatherApi weatherApi = BaseManager.getApiService(WeatherApi.class);

  public static Observable<WeatherInfo> getCurrentWeatherByCityName(String name) {
    return weatherApi.getCurrentWeatherByCityName(name);
  }

  public static Observable<WeatherInfo> getCurrentWeatherByLocation(Location location) {
    return weatherApi.getCurrentWeatherByLocation(location.getLatitude(),location.getLongitude());
  }

  public static Observable<WeatherForecast> getForecastWeatherByLocation(Location location) {
    return weatherApi.getForecastWeatherByLocation(location.getLatitude(), location.getLongitude());
  }
}
