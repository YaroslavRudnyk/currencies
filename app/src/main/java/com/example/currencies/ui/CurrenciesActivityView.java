package com.example.currencies.ui;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.currencies.data.db.entity.CurrencyEntity;
import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class) public interface CurrenciesActivityView
    extends MvpView {

  void addCurrencies(List<CurrencyEntity> currencyEntities);

  void showCurrencyDetails(CurrencyEntity currency);
}
