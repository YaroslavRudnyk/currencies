package com.example.currencies.data.db;

import com.example.currencies.App;
import com.pushtorefresh.storio3.sqlite.StorIOSQLite;
import javax.inject.Inject;

public class StorIoDbManager implements DbManager {

  @Inject StorIOSQLite storIOSQLite;

  public StorIoDbManager() {
    App.getAppComponent().inject(this);
  }
}
