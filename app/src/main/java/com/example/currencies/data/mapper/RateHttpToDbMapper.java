package com.example.currencies.data.mapper;

import com.example.currencies.data.db.entity.RateEntity;
import com.example.currencies.data.http.pojo.CurrencyRatePojo;
import com.example.currencies.util.DateUtil;

public class RateHttpToDbMapper implements Mapper<CurrencyRatePojo, RateEntity> {
  @Override public RateEntity map(CurrencyRatePojo from) throws RuntimeException {
    return new RateEntity(from.getId(), DateUtil.parseDateString(from.getDate()), from.getRate());
  }
}
