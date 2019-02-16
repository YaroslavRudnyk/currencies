package com.example.currencies.ui;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class) public interface CurrencyDetailActivityView
    extends MvpView {
  @StateStrategyType(SkipStrategy.class) void navigateToCurrenciesActivity();

  void showDetailsFragment();
}
