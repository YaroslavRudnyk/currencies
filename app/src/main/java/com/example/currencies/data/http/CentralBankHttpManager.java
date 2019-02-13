package com.example.currencies.data.http;

import com.example.currencies.App;
import com.example.currencies.data.http.api.CentralBankRestApi;
import com.example.currencies.data.http.pojo.CurrencyPojo;
import com.example.currencies.util.DateUtil;
import io.reactivex.Single;
import java.util.List;
import javax.inject.Inject;

public class CentralBankHttpManager implements HttpManager {

  @Inject CentralBankRestApi api;

  public CentralBankHttpManager() {
    App.getAppComponent().inject(this);
  }

  @Override public Single<List<CurrencyPojo>> fetchCurrencies() {
    return api.getCurrentRates();
  }

  @Override public Single<List<CurrencyPojo>> fetchCurrencies(long time) {
    return api.getRatesOnDate(DateUtil.getDateRepresentation(time));
  }
}
