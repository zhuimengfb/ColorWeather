package com.fbi.colorweather;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fbi.colorweather.adapter.WeatherItemAdapter;
import com.fbi.colorweather.entity.WeatherDailyInfo;
import com.fbi.colorweather.preference.UserPreference;
import com.fbi.colorweather.presenter.WeatherPresenter;
import com.fbi.colorweather.service.LocationService;
import com.fbi.colorweather.utils.NetworkUtil;
import com.fbi.colorweather.utils.TimeUtils;
import com.fbi.colorweather.views.WeatherView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements WeatherView {

  @BindView(R.id.iv_weather_icon) ImageView ivWeatherIcon;
  @BindView(R.id.activity_main) RelativeLayout activityMain;
  @BindView(R.id.tv_weather_temp) TextView tvWeatherTemp;
  @BindView(R.id.tv_last_update) TextView tvLastUpdate;
  @BindView(R.id.recycler_view) RecyclerView recyclerView;
  @BindView(R.id.tv_description) TextView tvDescription;
  @BindView(R.id.tv_location) TextView tvLocation;
  private WeatherItemAdapter adapter;
  private List<WeatherDailyInfo> weatherInfoList = new ArrayList<>();
  private WeatherPresenter weatherPresenter = new WeatherPresenter();
  private MyReceiver myReceiver;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    weatherPresenter.bind(this);
    initReceiver();
    initView();
    initData();
  }

  private void initView() {
    adapter = new WeatherItemAdapter(this, weatherInfoList);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    recyclerView.setAdapter(adapter);
  }

  private void initData() {
    weatherPresenter.changeBackground();
    if (UserPreference.getInstance().getLastUpdateTime() > 0) {
      weatherPresenter.getLocalWeather();
      weatherPresenter.getLocalForecast();
    } else {
      weatherPresenter.getCurrentWeatherByName();
    }
  }

  private void initReceiver() {
    myReceiver = new MyReceiver();
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("update_location");
    registerReceiver(myReceiver, intentFilter);
  }

  private void initPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
          PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest
          .permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission
            .ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
      } else {
        startRefreshData();
      }
    } else {
      startRefreshData();
    }
  }

  private void startRefreshData() {
    if (new NetworkUtil().isNetworkAvailable(tvDescription, new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        LocationService.startLocationService(MainActivity.this);
      }
    })) {
      LocationService.startLocationService(this);
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull
      int[] grantResults) {
    if (requestCode == 100) {
      if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        startRefreshData();
      }
    }
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
  }

  @Override
  protected void onResume() {
    super.onResume();
    initPermission();
  }

  @Override
  public void showDescription(String description) {
    tvDescription.setText(description);
  }

  @Override
  public void showLocation(String location) {
    tvLocation.setText(location);
  }

  @Override
  public void showWeatherIcon(int res) {
    ivWeatherIcon.setImageResource(res);
  }

  @Override
  public void showBackground(int colorRes) {
    activityMain.setBackgroundResource(colorRes);
  }

  @Override
  public void showTemperature(String temp) {
    tvWeatherTemp.setText(getString(R.string.weather_temp_now, temp));
  }

  @Override
  public void showLastUpdateTime(long time) {
    tvLastUpdate.setText(TimeUtils.convertTime(time));
  }

  @Override
  public void updateForecastWeather(List<WeatherDailyInfo> weatherInfoList) {
    this.weatherInfoList.clear();
    this.weatherInfoList.addAll(weatherInfoList);
    adapter.notifyDataSetChanged();
  }

  class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
      if ("update_location".equals(intent.getAction())) {
        Location location = intent.getParcelableExtra("location");
        weatherPresenter.getCurrentLocationWeather(location);
        weatherPresenter.getForecastWeather(location);
      }
    }
  }

  @Override
  protected void onDestroy() {
    unregisterReceiver(myReceiver);
    LocationService.stopLocationService(this);
    super.onDestroy();
  }
}
