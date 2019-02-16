package com.example.currencies.ui;

import com.arellomobile.mvp.InjectViewState;
import com.example.currencies.App;
import com.example.currencies.ui.base.BasePresenter;

@InjectViewState public class CurrencyDetailActivityPresenter
    extends BasePresenter<CurrencyDetailActivityView> {

  @Override protected void inject() {
    App.getAppComponent().inject(this);
  }

  void onHome() {
    getViewState().navigateToCurrenciesActivity();
  }

  void onReady() {
    getViewState().showDetailsFragment();
  }
}
