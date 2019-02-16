package com.example.currencies.ui;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.currencies.data.db.entity.RateEntity;

@StateStrategyType(AddToEndSingleStrategy.class) public interface CurrencyDetailFragmentView
    extends MvpView {
  void updateRate(RateEntity rateEntity);
}
