package com.example.currencies.data.db.worker;

import com.example.currencies.App;
import com.example.currencies.data.db.entity.CurrencyEntity;
import com.example.currencies.data.db.entity.DbEntity;
import com.example.currencies.data.db.table.CurrenciesTable;
import com.pushtorefresh.storio3.sqlite.StorIOSQLite;
import io.reactivex.Completable;
import io.reactivex.Single;
import java.util.List;
import javax.inject.Inject;

public class CurrencyWorker implements EntityWorker {

  @Inject StorIOSQLite storIOSQLite;

  public CurrencyWorker() {
    App.getAppComponent().inject(this);
  }

  @Override public Single<List<CurrencyEntity>> getAllEntities() {
    return storIOSQLite.get()
        .listOfObjects(CurrencyEntity.class)
        .withQuery(CurrenciesTable.QUERY_ALL)
        .prepare()
        .asRxSingle();
  }

  @Override public Completable putEntity(DbEntity entity) {
    return storIOSQLite.put()
        .object(entity)
        .prepare()
        .asRxCompletable();
  }

  @Override public Completable deleteEntity(DbEntity entity) {
    return storIOSQLite.delete()
        .object(entity)
        .prepare()
        .asRxCompletable();
  }
}
