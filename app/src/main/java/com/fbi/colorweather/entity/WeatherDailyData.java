package com.fbi.colorweather.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 06/12/2016
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDailyData {

  @JsonProperty("day")
  private double temperature;
  @JsonProperty("max")
  private double maxTemperature;
  @JsonProperty("min")
  private double minTemperature;
  @JsonProperty("pressure")
  private int pressure;


  public double getTemperature() {
    return temperature;
  }

  public void setTemperature(double temperature) {
    this.temperature = temperature;
  }

  public double getMaxTemperature() {
    return maxTemperature;
  }

  public void setMaxTemperature(double maxTemperature) {
    this.maxTemperature = maxTemperature;
  }

  public double getMinTemperature() {
    return minTemperature;
  }

  public void setMinTemperature(double minTemperature) {
    this.minTemperature = minTemperature;
  }

  public int getPressure() {
    return pressure;
  }

  public void setPressure(int pressure) {
    this.pressure = pressure;
  }
}
