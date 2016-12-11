package com.fbi.colorweather.service;

import android.Manifest;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.fbi.colorweather.MyApp;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 06/12/2016
 */

public class LocationService extends Service {
  private LocationManager locationManager;

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    if (isOpen(MyApp.getContext())) {
      getGPSConfi();
    } else {
      openGPS(MyApp.getContext());
    }
    return super.onStartCommand(intent, flags, startId);
  }

  private boolean isOpen(Context context) {
    locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    return gps || network;
  }

  private void openGPS(Context context) {
    Intent GPSIntent = new Intent();
    GPSIntent.setClassName("com.android.settings", "com.android.settings.widget" +
        ".SettingsAppWidgetProvide");
    GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
    GPSIntent.setData(Uri.parse("custom:3"));
    try {
      PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
    } catch (PendingIntent.CanceledException e) {
      e.printStackTrace();
    }
  }

  public void getGPSConfi() {
    if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
      locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0,
          locationListener);
    } else {
      locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0,
          locationListener);
    }

  }

  private LocationListener locationListener = new LocationListener() {
    @Override
    public void onLocationChanged(Location location) {
      if (location != null) {
        sendLocationBroadcast(location);
        locationManager.removeUpdates(this);
      } else {
        Log.d("", "未获取到经纬度数据");
      }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
  };

  private void sendLocationBroadcast(Location location) {
    Intent intent = new Intent();
    intent.setAction("update_location");
    intent.putExtra("location", location);
    sendBroadcast(intent);
  }

  public static void startLocationService(Context context) {
    Intent intent = new Intent();
    intent.setClass(context, LocationService.class);
    context.startService(intent);
  }
  public static void stopLocationService(Context context) {
    Intent intent = new Intent();
    intent.setClass(context, LocationService.class);
    context.stopService(intent);
  }

}
