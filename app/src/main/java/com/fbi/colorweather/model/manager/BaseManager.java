package com.fbi.colorweather.model.manager;

import com.fbi.colorweather.utils.Constants;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 05/12/2016
 */

public class BaseManager {

  private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
      .addInterceptor(new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {
          return chain.proceed(addParam(chain.request()));
        }
      }).build();

  private static Request addParam(Request oldRequest) {
    HttpUrl.Builder builder = oldRequest.url()
        .newBuilder()
        .setEncodedQueryParameter("appid", Constants.APP_ID);
    return oldRequest.newBuilder()
        .method(oldRequest.method(), oldRequest.body())
        .url(builder.build())
        .build();
  }

  private static Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.SERVER_URL)
      .client(okHttpClient)
      .addConverterFactory(JacksonConverterFactory.create())
      .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
      .build();

  public static <T> T getApiService(Class<T> clazz) {
    return retrofit.create(clazz);
  }
}
