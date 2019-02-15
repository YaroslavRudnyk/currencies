package com.example.currencies.di.component;

import com.example.currencies.App;
import com.example.currencies.data.Repository;
import com.example.currencies.data.db.StorIoDbManager;
import com.example.currencies.data.db.worker.CurrencyRateWorker;
import com.example.currencies.data.db.worker.CurrencyWorker;
import com.example.currencies.data.db.worker.RateWorker;
import com.example.currencies.data.http.CentralBankHttpManager;
import com.example.currencies.ui.CurrenciesActivityPresenter;
import com.example.currencies.ui.adapter.CurrenciesAdapter;
import com.example.currencies.ui.base.BaseActivity;

public interface AppComponentInjects {

  // Applications
  void inject(App application);

  // Activities

  void inject(BaseActivity activity);

  // Fragments

  // Presenters

  void inject(CurrenciesActivityPresenter presenter);

  // Adapters

  void inject(CurrenciesAdapter adapter);

  // Mappers

  // Data managers
  void inject(Repository repository);

  void inject(StorIoDbManager manager);

  void inject(CentralBankHttpManager manager);

  // Database workers

  void inject(CurrencyWorker worker);

  void inject(RateWorker worker);

  void inject(CurrencyRateWorker worker);
}
