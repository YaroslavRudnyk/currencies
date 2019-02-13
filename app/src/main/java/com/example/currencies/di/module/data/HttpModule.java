package com.example.currencies.di.module.data;

import android.util.Log;
import com.example.currencies.BuildConfig;
import com.example.currencies.data.http.CentralBankHttpManager;
import com.example.currencies.data.http.HttpManager;
import com.example.currencies.data.http.api.CentralBankApi;
import com.example.currencies.data.http.api.CentralBankRestApi;
import com.example.currencies.di.scope.AppScope;
import com.google.gson.Gson;
import dagger.Module;
import dagger.Provides;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module public class HttpModule {

  private static final String BASE_API_URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/";

  private static final String HTTP_TAG = "OkHttp";
  private static final long OK_HTTP_READ_TIMEOUT = 30;

  @Provides @AppScope HttpManager provideCentralBankHttpManager() {
    return new CentralBankHttpManager();
  }

  // Bank ...
  @Provides @AppScope CentralBankRestApi provideCentralBankRestApi(CentralBankApi centralBankApi) {
    return new CentralBankRestApi(centralBankApi);
  }

  @Provides @AppScope CentralBankApi provideCentralBankApi(Retrofit retrofit) {
    return retrofit.create(CentralBankApi.class);
  }
  // ... Bank

  // Retrofit ...
  @Provides
  @AppScope
  Retrofit provideRetrofit(Converter.Factory converterFactory, OkHttpClient okClient) {
    return new Retrofit.Builder()
        .baseUrl(BASE_API_URL)
        .client(okClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(converterFactory)
        .build();
  }

  @Provides
  @AppScope
  Converter.Factory provideConverterFactory(Gson gson) {
    return GsonConverterFactory.create(gson);
  }

  @Provides
  @AppScope
  OkHttpClient provideOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor) {
    return new OkHttpClient.Builder()
        .readTimeout(OK_HTTP_READ_TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .build();
  }

  @Provides
  @AppScope
  HttpLoggingInterceptor provideHttpLoggingInterceptor() {
    HttpLoggingInterceptor interceptor =
        new HttpLoggingInterceptor(message -> Log.d(HTTP_TAG, message));
    interceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY
        : HttpLoggingInterceptor.Level.NONE);
    return interceptor;
  }
  // ... Retrofit
}
