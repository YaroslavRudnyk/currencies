package com.example.currencies.data.mapper;

import com.example.currencies.data.db.entity.CurrencyEntity;
import com.example.currencies.data.http.pojo.CurrencyRatePojo;

public class CurrencyHttpToDbMapper implements Mapper<CurrencyRatePojo, CurrencyEntity> {
  @Override public CurrencyEntity map(CurrencyRatePojo from) throws RuntimeException {
    return new CurrencyEntity(from.getId(), from.getName(), from.getCode());
  }
}
