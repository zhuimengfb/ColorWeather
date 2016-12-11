package com.fbi.colorweather.presenter;

import android.location.Location;
import android.util.Log;

import com.fbi.colorweather.R;
import com.fbi.colorweather.entity.Weather;
import com.fbi.colorweather.entity.WeatherDailyInfo;
import com.fbi.colorweather.entity.WeatherInfo;
import com.fbi.colorweather.model.WeatherModel;
import com.fbi.colorweather.preference.UserPreference;
import com.fbi.colorweather.utils.Constants;
import com.fbi.colorweather.utils.TemperatureUtils;
import com.fbi.colorweather.utils.WeatherIconUtils;
import com.fbi.colorweather.views.WeatherView;

import java.util.Date;
import java.util.List;
import java.util.Random;

import rx.Subscriber;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 05/12/2016
 */

public class WeatherPresenter {

  private int[] colors = new int[]{R.color.background_1, R.color.background_2, R.color
      .background_3, R.color.background_4, R.color.background_5, R.color.background_6, R.color
      .background_7, R.color.background_8, R.color.background_9, R.color.background_10, R.color
      .background_11};
  private WeatherView weatherView;
  private WeatherModel weatherModel;

  public void bind(WeatherView weatherView) {
    weatherModel = new WeatherModel();
    this.weatherView = weatherView;
  }

  public void changeBackground() {
    weatherView.showBackground(colors[Math.abs(new Random(System.currentTimeMillis()).nextInt
        () % colors.length)]);
  }

  public void getLocalWeather() {
    weatherModel.getCurrentWeatherFromLocal(new Subscriber<WeatherInfo>() {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onNext(WeatherInfo weatherInfo) {
        if (weatherInfo != null) {
          updateView(weatherInfo);
          weatherView.showLastUpdateTime(UserPreference.getInstance().getLastUpdateTime());
          weatherView.showLocation(weatherInfo.getCityName());
        }
      }
    });
  }

  public void getCurrentWeatherByName() {
    weatherModel.getCurrentWeatherByCityName(Constants.DEFAULT_CITY, new Subscriber<WeatherInfo>() {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable e) {
        e.printStackTrace();
      }

      @Override
      public void onNext(WeatherInfo weatherInfo) {
        updateView(weatherInfo);
      }
    });
  }


  private void updateView(WeatherInfo weatherInfo) {
    if (weatherInfo != null && weatherInfo.getWeathers() != null && weatherInfo.getWeathers()
        .size() > 0) {
      Log.d("type", weatherInfo.getWeathers().get(0).getType());
      showWeatherIcon(weatherInfo.getWeathers().get(0));
      weatherView.showTemperature(TemperatureUtils.parseKToString(weatherInfo.getWeatherData
          ().getTemperature()));
      weatherView.showDescription(weatherInfo.getWeathers().get(0).getDescription());
      weatherView.showLocation(weatherInfo.getCityName());
    }
  }

  public void getCurrentLocationWeather(Location location) {
    weatherModel.getCurrentWeatherByLocation(location, new Subscriber<WeatherInfo>() {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onNext(WeatherInfo weatherInfo) {
        updateView(weatherInfo);
        weatherView.showLastUpdateTime(new Date().getTime());
      }
    });
  }

  public void getForecastWeather(Location location) {
    weatherModel.getForecastWeatherByLocation(location, new Subscriber<List<WeatherDailyInfo>>() {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onNext(List<WeatherDailyInfo> weatherInfos) {
        if (weatherInfos != null && weatherInfos.size() > 0) {
          weatherView.updateForecastWeather(weatherInfos);
        }
      }
    });
  }

  private void showWeatherIcon(Weather weather) {
    weatherView.showWeatherIcon(WeatherIconUtils.getResByType(weather));
  }

  public void getLocalForecast() {
    weatherModel.getForecastWeatherFromLocal(new Subscriber<List<WeatherDailyInfo>>() {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onNext(List<WeatherDailyInfo> weatherInfos) {
        if (weatherInfos != null && weatherInfos.size() > 0) {
          weatherView.updateForecastWeather(weatherInfos);
        }
      }
    });
  }
}
