package com.fbi.colorweather.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 05/12/2016
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherInfo implements Serializable {

  @JsonProperty("coord")
  private CoordinateInfo coordinateInfo;
  @JsonProperty("weather")
  private List<Weather> weathers;
  @JsonProperty("main")
  private WeatherData weatherData;
  @JsonProperty("dt")
  private long time;
  @JsonProperty("name")
  private String cityName = "";
  @JsonProperty("id")
  private long cityId;


  public CoordinateInfo getCoordinateInfo() {
    return coordinateInfo;
  }

  public void setCoordinateInfo(CoordinateInfo coordinateInfo) {
    this.coordinateInfo = coordinateInfo;
  }

  public List<Weather> getWeathers() {
    return weathers;
  }

  public void setWeathers(List<Weather> weathers) {
    this.weathers = weathers;
  }

  public WeatherData getWeatherData() {
    return weatherData;
  }

  public void setWeatherData(WeatherData weatherData) {
    this.weatherData = weatherData;
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

  public String getCityName() {
    return cityName;
  }

  public void setCityName(String cityName) {
    this.cityName = cityName;
  }

  public long getCityId() {
    return cityId;
  }

  public void setCityId(long cityId) {
    this.cityId = cityId;
  }
}
