package com.example.currencies.data.db;

import com.example.currencies.App;
import com.example.currencies.data.db.entity.CurrencyEntity;
import com.example.currencies.data.db.entity.RateEntity;
import com.example.currencies.data.db.worker.CurrencyRateWorker;
import com.example.currencies.data.db.worker.CurrencyWorker;
import com.example.currencies.data.db.worker.RateWorker;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

@SuppressWarnings("unchecked") public class StorIoDbManager implements DbManager {

  @SuppressWarnings("WeakerAccess") @Inject CurrencyWorker currencyWorker;
  @SuppressWarnings("WeakerAccess") @Inject RateWorker rateWorker;
  @SuppressWarnings("WeakerAccess") @Inject CurrencyRateWorker currencyRateWorker;

  public StorIoDbManager() {
    App.getAppComponent().inject(this);
  }

  @Override public Single<List<CurrencyEntity>> getAllCurrencies() {
    return currencyWorker.getAllEntities();
  }

  @Override public Completable putCurrency(CurrencyEntity entity) {
    return currencyWorker.putEntity(entity);
  }

  @Override public Completable putCurrencies(List<CurrencyEntity> entities) {
    return currencyWorker.putEntities(entities);
  }

  @Override public Completable deleteCurrency(CurrencyEntity entity) {
    return currencyWorker.deleteEntity(entity);
  }

  @Override public Flowable<List<CurrencyEntity>> listenForCurrenciesUpdates() {
    return currencyWorker.listenForUpdates();
  }

  @Override public Single<List<RateEntity>> getAllRates() {
    return rateWorker.getAllEntities();
  }

  @Override public Single<List<RateEntity>> getRatesOnDate(Long date) {
    return ((RateWorker) rateWorker).getEntitiesOnDate(date);
  }

  @Override
  public Single<List<RateEntity>> getRatesOnCurrencyIdOnDate(Integer currencyId, Long date) {
    return ((RateWorker) rateWorker).getEntitiesOnCurrencyIdOnDate(currencyId, date);
  }

  @Override public Single<RateEntity> getCurrencyRate(int currencyId, long rateTime) {
    return ((RateWorker) rateWorker).getEntityOnCurrencyIdOnDate(currencyId, rateTime);
  }

  @Override public Completable putRate(RateEntity entity) {
    return rateWorker.putEntity(entity);
  }

  @Override public Completable putRates(List<RateEntity> entities) {
    return rateWorker.putEntities(entities);
  }

  @Override public Completable deleteRate(RateEntity entity) {
    return rateWorker.deleteEntity(entity);
  }

  @Override public Flowable<List<RateEntity>> listenForCurrencyRateUpdates() {
    return rateWorker.listenForUpdates();
  }

  @Override
  public Flowable<RateEntity> listenForCurrencyRateUpdates(int currencyId, long rateDate) {
    return ((RateWorker) rateWorker).listenForUpdates(currencyId, rateDate);
  }

  @Override public Completable putCurrencyRates(Map<CurrencyEntity, RateEntity> entitiesMap) {
    return currencyRateWorker.putEntities(entitiesMap);
  }
}
