package com.example.currencies.data.http;

import com.example.currencies.data.http.pojo.CurrencyPojo;
import io.reactivex.Single;
import java.util.List;

public interface HttpManager {

  Single<List<CurrencyPojo>> fetchCurrencies();

  Single<List<CurrencyPojo>> fetchCurrencies(long time);
}
