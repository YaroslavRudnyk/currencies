package com.example.currencies.data.http.api;

import com.example.currencies.data.http.pojo.CurrencyPojo;
import io.reactivex.Single;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CentralBankApi {

  @GET("exchange?json")
  Single<List<CurrencyPojo>> getCurrentRates();

  @GET("exchange?json")
  Single<List<CurrencyPojo>> getRatesOnDate(@Query("date") String date);
}
