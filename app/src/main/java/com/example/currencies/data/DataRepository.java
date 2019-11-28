package com.example.currencies.data;

import com.example.currencies.data.db.entity.CurrencyEntity;
import com.example.currencies.data.db.entity.RateEntity;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import java.util.List;

public interface DataRepository {

  Completable fetchCurrencyRates();

  Completable fetchCurrencyRates(long time);

  Flowable<List<CurrencyEntity>> listenForCurrenciesUpdates();

  Flowable<RateEntity> listenForCurrencyRateUpdates(int currencyId, long rateDate);

  Single<RateEntity> getCurrencyRate(int currencyId, long rateTime);

  Completable setCurrencyFavorite(CurrencyEntity currency);
}
