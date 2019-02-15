package com.example.currencies.data;

import com.example.currencies.App;
import com.example.currencies.data.db.DbManager;
import com.example.currencies.data.db.entity.CurrencyEntity;
import com.example.currencies.data.db.entity.RateEntity;
import com.example.currencies.data.http.HttpManager;
import com.example.currencies.data.http.pojo.CurrencyRatePojo;
import com.example.currencies.data.mapper.CurrencyHttpToDbMapper;
import com.example.currencies.data.mapper.RateHttpToDbMapper;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

public class Repository implements DataRepository {

  @Inject DbManager dbManager;
  @Inject HttpManager httpManager;

  @SuppressWarnings("WeakerAccess") @Inject CurrencyHttpToDbMapper currencyHttpToDbMapper;
  @SuppressWarnings("WeakerAccess") @Inject RateHttpToDbMapper rateHttpToDbMapper;

  public Repository() {
    App.getAppComponent().inject(this);
  }

  @Override public Completable fetchCurrencyRates() {
    return cacheCurrencyRatesInDb(httpManager.fetchCurrencyRates());
  }

  @Override public Completable fetchCurrencyRates(long time) {
    return cacheCurrencyRatesInDb(httpManager.fetchCurrencyRates(time));
  }

  @Override public Flowable<List<CurrencyEntity>> listenForCurrenciesUpdates() {
    return dbManager.listenForCurrenciesUpdates();
  }

  ///////////////////////////////////////////////////////////////////////////
  // PRIVATE SECTION
  ///////////////////////////////////////////////////////////////////////////

  private Completable cacheCurrencyRatesInDb(Single<List<CurrencyRatePojo>> currencyRatePojos) {
    return currencyRatePojos
        .flatMap(this::mapCurrencyRatePojosToEntityMap)
        .flatMapCompletable(currencyRates -> dbManager.putCurrencyRates(currencyRates));
  }

  private Single<Map<CurrencyEntity, RateEntity>> mapCurrencyRatePojosToEntityMap(
      List<CurrencyRatePojo> pojos) {
    return Flowable.fromIterable(pojos)
        .toMap(pojo -> currencyHttpToDbMapper.map(pojo),
            currencyRatePojo -> rateHttpToDbMapper.map(currencyRatePojo));
  }
}
