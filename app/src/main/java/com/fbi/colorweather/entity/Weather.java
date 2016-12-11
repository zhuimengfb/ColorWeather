package com.fbi.colorweather.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 05/12/2016
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather implements Serializable {
  @JsonProperty("id")
  private int id;
  @JsonProperty("main")
  private String type;
  @JsonProperty("description")
  private String description;
  @JsonProperty("icon")
  private String icon;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }
}
