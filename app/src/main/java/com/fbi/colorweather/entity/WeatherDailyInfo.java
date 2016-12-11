package com.fbi.colorweather.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 06/12/2016
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDailyInfo {

  @JsonProperty("weather")
  private List<Weather> weathers;
  @JsonProperty("temp")
  private WeatherDailyData weatherData;
  @JsonProperty("dt")
  private long time;

  public List<Weather> getWeathers() {
    return weathers;
  }

  public void setWeathers(List<Weather> weathers) {
    this.weathers = weathers;
  }

  public WeatherDailyData getWeatherData() {
    return weatherData;
  }

  public void setWeatherData(WeatherDailyData weatherData) {
    this.weatherData = weatherData;
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }
}
