package com.fbi.colorweather;

import android.app.Application;
import android.content.Context;

import com.fbi.colorweather.utils.Constants;
import com.tencent.bugly.Bugly;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 06/12/2016
 */

public class MyApp extends Application {

  private static Context context;

  @Override
  public void onCreate() {
    super.onCreate();
    context = getApplicationContext();
    Bugly.init(getApplicationContext(), Constants.BUGLY_APP_ID, false);
  }

  public static Context getContext() {
    return context;
  }
}
