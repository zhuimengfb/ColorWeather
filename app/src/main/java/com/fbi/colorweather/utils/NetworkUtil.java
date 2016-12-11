package com.fbi.colorweather.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.fbi.colorweather.MyApp;
import com.fbi.colorweather.R;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 9/5/16
 */
public class NetworkUtil {


  public static boolean isNetworkReachable() {
    ConnectivityManager cm = (ConnectivityManager) MyApp.getContext().getSystemService
        (Context.CONNECTIVITY_SERVICE);
    NetworkInfo ni = cm.getActiveNetworkInfo();
    return ni != null && ni.isConnectedOrConnecting();
  }

  public boolean isNetworkAvailable(View view, View.OnClickListener onClickListener) {
    if (isNetworkReachable()) {
      return true;
    } else {
      Snackbar.make(view, MyApp.getContext().getString(R.string.network_error), Snackbar
          .LENGTH_INDEFINITE).setAction(R.string.refresh, onClickListener).show();
      return false;
    }
  }

}
