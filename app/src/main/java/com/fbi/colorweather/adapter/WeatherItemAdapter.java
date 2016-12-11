package com.fbi.colorweather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fbi.colorweather.R;
import com.fbi.colorweather.entity.WeatherDailyInfo;
import com.fbi.colorweather.utils.TemperatureUtils;
import com.fbi.colorweather.utils.TimeUtils;
import com.fbi.colorweather.utils.WeatherIconUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 06/12/2016
 */

public class WeatherItemAdapter extends RecyclerView.Adapter<WeatherItemAdapter.WeatherViewHolder> {

  private Context context;
  private List<WeatherDailyInfo> weatherInfos;

  public WeatherItemAdapter(Context context, List<WeatherDailyInfo> weatherInfos) {
    this.context = context;
    this.weatherInfos = weatherInfos;
  }

  @Override
  public WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new WeatherViewHolder(LayoutInflater.from(context).inflate(R.layout.item_weather_data,
        parent, false));
  }

  @Override
  public void onBindViewHolder(WeatherViewHolder holder, int position) {
    holder.bindData(position);
  }

  @Override
  public int getItemCount() {
    return weatherInfos.size();
  }

  class WeatherViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_time) TextView time;
    @BindView(R.id.iv_weather_icon) ImageView icon;
    @BindView(R.id.tv_weather_temp) TextView temp;

    public WeatherViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void bindData(int position) {
      WeatherDailyInfo weatherInfo = weatherInfos.get(position);
      time.setText(TimeUtils.getForecastDay(weatherInfo.getTime()));
      icon.setImageResource(WeatherIconUtils.getResByType(weatherInfo.getWeathers().get(0)));
      temp.setText(context.getString(R.string.max_min_temp, TemperatureUtils.parseKToString
          (weatherInfo.getWeatherData().getMinTemperature()), TemperatureUtils.parseKToString
          (weatherInfo.getWeatherData().getMaxTemperature())));
    }
  }
}
