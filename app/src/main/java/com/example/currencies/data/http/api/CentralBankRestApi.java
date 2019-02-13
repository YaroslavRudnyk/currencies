package com.example.currencies.data.http.api;

import com.example.currencies.data.http.pojo.CurrencyPojo;
import io.reactivex.Single;
import java.util.List;

public class CentralBankRestApi {

  private final CentralBankApi api;

  public CentralBankRestApi(CentralBankApi api) {this.api = api;}

  public Single<List<CurrencyPojo>> getCurrentRates() {
    return api.getCurrentRates();
  }

  public Single<List<CurrencyPojo>> getRatesOnDate(String date) {
    return api.getRatesOnDate(date);
  }
}
