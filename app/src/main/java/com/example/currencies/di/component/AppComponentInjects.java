package com.example.currencies.di.component;

import com.example.currencies.App;
import com.example.currencies.data.Repository;
import com.example.currencies.data.db.StorIoDbManager;
import com.example.currencies.data.db.worker.CurrencyWorker;
import com.example.currencies.data.db.worker.RateWorker;

public interface AppComponentInjects {

  // Applications
  void inject(App application);

  // Activities

  // Fragments

  // Presenters

  // Adapters

  // Mappers

  // Data managers
  void inject(Repository repository);

  void inject(StorIoDbManager dbManager);

  // Database workers

  void inject(CurrencyWorker worker);

  void inject(RateWorker worker);
}
