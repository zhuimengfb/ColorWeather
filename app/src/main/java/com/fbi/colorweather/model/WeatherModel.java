package com.fbi.colorweather.model;

import android.location.Location;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fbi.colorweather.entity.WeatherDailyInfo;
import com.fbi.colorweather.entity.WeatherForecast;
import com.fbi.colorweather.entity.WeatherInfo;
import com.fbi.colorweather.model.manager.WeatherManager;
import com.fbi.colorweather.preference.UserPreference;

import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 05/12/2016
 */

public class WeatherModel {

  public Subscription getCurrentWeatherByCityName(String name, Subscriber<WeatherInfo> subscriber) {
    return WeatherManager.getCurrentWeatherByCityName(name)
        .doOnNext(new Action1<WeatherInfo>() {
          @Override
          public void call(WeatherInfo weatherInfo) {
            saveWeather(weatherInfo);
          }
        })
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber);
  }

  private void saveWeather(WeatherInfo weatherInfo) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      UserPreference.getInstance().updateCurrentWeather(objectMapper.writeValueAsString
          (weatherInfo));
      UserPreference.getInstance().updateLastUpdateTime(new Date().getTime());
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }

  public Subscription getCurrentWeatherByLocation(Location location, Subscriber<WeatherInfo>
      subscriber) {
    return WeatherManager.getCurrentWeatherByLocation(location)
        .doOnNext(new Action1<WeatherInfo>() {
          @Override
          public void call(WeatherInfo weatherInfo) {
            saveWeather(weatherInfo);
            Log.d("time", "" + weatherInfo.getTime());
          }
        })
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber);
  }

  public Subscription getForecastWeatherByLocation(Location location,
                                                   Subscriber<List<WeatherDailyInfo>> subscriber) {
    return WeatherManager.getForecastWeatherByLocation(location)
        .map(new Func1<WeatherForecast, List<WeatherDailyInfo>>() {
          @Override
          public List<WeatherDailyInfo> call(WeatherForecast weatherForecast) {
            return weatherForecast.getWeatherInfos();
          }
        }).doOnNext(new Action1<List<WeatherDailyInfo>>() {
          @Override
          public void call(List<WeatherDailyInfo> weatherInfos) {
            ObjectMapper mapper = new ObjectMapper();
            try {
              UserPreference.getInstance().updateForecastWeather(mapper.writeValueAsString
                  (weatherInfos));
              for (WeatherDailyInfo weatherInfo : weatherInfos) {
                Log.d("type", weatherInfo.getWeathers().get(0).getType());
                Log.d("time", "" + weatherInfo.getTime());
              }
            } catch (JsonProcessingException e) {
              e.printStackTrace();
            }
          }
        }).subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber);
  }

  public Subscription getForecastWeatherFromLocal(Subscriber<List<WeatherDailyInfo>> subscriber) {
    return Observable.just(UserPreference.getInstance().getForecastWeather())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber);
  }

  public Subscription getCurrentWeatherFromLocal(Subscriber<WeatherInfo> subscriber) {
    return Observable.just(UserPreference.getInstance().getCurrentWeather())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber);
  }
}
