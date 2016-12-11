package com.fbi.colorweather.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 06/12/2016
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherForecast implements Serializable {

  @JsonProperty("list")
  private List<WeatherDailyInfo> weatherInfos;

  public List<WeatherDailyInfo> getWeatherInfos() {
    return weatherInfos;
  }

  public void setWeatherInfos(List<WeatherDailyInfo> weatherInfos) {
    this.weatherInfos = weatherInfos;
  }
}
