package com.example.currencies.data.http;

import com.example.currencies.data.http.pojo.CurrencyRatePojo;
import io.reactivex.Single;
import java.util.List;

public interface HttpManager {

  Single<List<CurrencyRatePojo>> fetchCurrencyRates();

  Single<List<CurrencyRatePojo>> fetchCurrencyRates(long time);
}
