package com.example.currencies.data;

import io.reactivex.Completable;

public interface DataRepository {

  Completable fetchCurrencyRates();

  Completable fetchCurrencyRates(long time);
}
