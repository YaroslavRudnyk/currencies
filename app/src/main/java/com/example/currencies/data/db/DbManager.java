package com.example.currencies.data.db;

import com.example.currencies.data.db.entity.CurrencyEntity;
import com.example.currencies.data.db.entity.RateEntity;
import io.reactivex.Completable;
import io.reactivex.Single;
import java.util.List;
import java.util.Map;

public interface DbManager {

  // Currencies ...
  Single<List<CurrencyEntity>> getAllCurrencies();

  Completable putCurrency(CurrencyEntity entity);

  Completable putCurrencies(List<CurrencyEntity> entities);

  Completable deleteCurrency(CurrencyEntity entity);
  // ... Currencies

  // Rates ...
  Single<List<RateEntity>> getAllRates();

  Single<List<RateEntity>> getRatesOnDate(Long date);

  Single<List<RateEntity>> getRatesOnCurrencyIdOnDate(Integer currencyId, Long date);

  Completable putRate(RateEntity entity);

  Completable putRates(List<RateEntity> entities);

  Completable deleteRate(RateEntity entity);
  // ... Rates

  // CurrencyRates ...
  Completable putCurrencyRates(Map<CurrencyEntity, RateEntity> currencyRates);
  // ... CurrencyRates
}
