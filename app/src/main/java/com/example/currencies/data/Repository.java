package com.example.currencies.data;

import com.example.currencies.App;
import com.example.currencies.data.db.DbManager;
import javax.inject.Inject;

public class Repository implements DataRepository {

  @Inject DbManager dbManager;

  public Repository() {
    App.getAppComponent().inject(this);
  }
}
