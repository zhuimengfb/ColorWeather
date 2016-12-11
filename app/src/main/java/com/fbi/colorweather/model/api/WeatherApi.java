package com.fbi.colorweather.model.api;

import com.fbi.colorweather.entity.WeatherForecast;
import com.fbi.colorweather.entity.WeatherInfo;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 05/12/2016
 */

public interface WeatherApi {

  @GET("weather")
  Observable<WeatherInfo> getCurrentWeatherByCityName(@Query("q") String cityName);

  @GET("weather")
  Observable<WeatherInfo> getCurrentWeatherByLocation(@Query("lat") double lat, @Query("lon")
      double lon);

  @GET("forecast/daily")
  Observable<WeatherForecast> getForecastWeatherByLocation(@Query("lat") double lat, @Query
      ("lon") double lon);
}
