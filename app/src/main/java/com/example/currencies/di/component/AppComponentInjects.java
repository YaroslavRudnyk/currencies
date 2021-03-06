package com.example.currencies.di.component;

import com.example.currencies.App;
import com.example.currencies.data.Repository;
import com.example.currencies.data.db.StorIoDbManager;
import com.example.currencies.data.db.worker.CurrencyRateWorker;
import com.example.currencies.data.db.worker.CurrencyWorker;
import com.example.currencies.data.db.worker.RateWorker;
import com.example.currencies.data.http.CentralBankHttpManager;
import com.example.currencies.job.FetchCurrenciesWorker;
import com.example.currencies.ui.CurrenciesActivityPresenter;
import com.example.currencies.ui.CurrencyDetailActivityPresenter;
import com.example.currencies.ui.CurrencyDetailFragmentPresenter;
import com.example.currencies.ui.adapter.CurrenciesAdapter;
import com.example.currencies.ui.base.BaseActivity;
import com.example.currencies.ui.base.BaseFragment;

public interface AppComponentInjects {

  // Applications
  void inject(App application);

  // Activities
  void inject(BaseActivity activity);

  // Fragments
  void inject(BaseFragment fragment);

  // Presenters
  void inject(CurrenciesActivityPresenter presenter);

  void inject(CurrencyDetailFragmentPresenter presenter);

  void inject(CurrencyDetailActivityPresenter presenter);

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

  // Job workers
  void inject(FetchCurrenciesWorker worker);
}
