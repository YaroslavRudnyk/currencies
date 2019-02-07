package com.example.currencies.data.db;

import com.example.currencies.App;
import com.example.currencies.data.db.worker.EntityWorker;
import com.example.currencies.data.db.worker.RateWorker;
import com.pushtorefresh.storio3.sqlite.StorIOSQLite;
import javax.inject.Inject;
import javax.inject.Named;

import static com.example.currencies.di.module.data.DbModule.NAME_WORKER_CURRENCY;
import static com.example.currencies.di.module.data.DbModule.NAME_WORKER_RATE;

public class StorIoDbManager implements DbManager {

  @Inject StorIOSQLite storIOSQLite;
  @Inject @Named(NAME_WORKER_CURRENCY) EntityWorker currencyWorker;
  @Inject @Named(NAME_WORKER_RATE) RateWorker rateWorker;

  public StorIoDbManager() {
    App.getAppComponent().inject(this);
  }
}
