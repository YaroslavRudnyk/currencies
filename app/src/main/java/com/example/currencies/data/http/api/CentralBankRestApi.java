package com.example.currencies.data.http.api;

import com.example.currencies.data.http.pojo.CurrencyRatePojo;
import io.reactivex.Single;
import java.util.List;

public class CentralBankRestApi {

  private final CentralBankApi api;

  public CentralBankRestApi(CentralBankApi api) {this.api = api;}

  public Single<List<CurrencyRatePojo>> getCurrentRates() {
    return api.getCurrentRates();
  }

  public Single<List<CurrencyRatePojo>> getRatesOnDate(String date) {
    return api.getRatesOnDate(date);
  }
}
