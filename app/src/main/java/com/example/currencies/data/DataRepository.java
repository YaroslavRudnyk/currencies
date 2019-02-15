package com.example.currencies.data;

import com.example.currencies.data.db.entity.CurrencyEntity;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import java.util.List;

public interface DataRepository {

  Completable fetchCurrencyRates();

  Completable fetchCurrencyRates(long time);

  Flowable<List<CurrencyEntity>> listenForCurrenciesUpdates();
}
