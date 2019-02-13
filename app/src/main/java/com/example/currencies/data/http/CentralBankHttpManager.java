package com.example.currencies.data.http;

import android.util.Log;
import com.example.currencies.App;
import com.example.currencies.data.http.api.CentralBankRestApi;
import com.example.currencies.data.http.pojo.CurrencyPojo;
import io.reactivex.Single;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.inject.Inject;

public class CentralBankHttpManager implements HttpManager {

  @Inject CentralBankRestApi api;

  public CentralBankHttpManager() {
    App.getAppComponent().inject(this);
  }

  @Override public Single<List<CurrencyPojo>> fetchCurrencies() {
    return api.getCurrentRates();
  }

  @Override public Single<List<CurrencyPojo>> fetchCurrencies(Date date) {
    SimpleDateFormat df =
        new SimpleDateFormat("yyyyMMdd", new Locale("uk") /*TODO: keep locale in App*/);

    String stringDate = df.format(date);

    Log.d("CentralBankHttpManager", "DBG_HTTP: fetchCurrencies(): tr date " + stringDate);

    return api.getRatesOnDate(stringDate);
  }
}
