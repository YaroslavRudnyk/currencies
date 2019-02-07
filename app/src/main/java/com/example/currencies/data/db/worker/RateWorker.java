package com.example.currencies.data.db.worker;

import com.example.currencies.App;
import com.example.currencies.data.db.entity.DbEntity;
import com.example.currencies.data.db.entity.RateEntity;
import com.example.currencies.data.db.table.RatesTable;
import com.pushtorefresh.storio3.sqlite.StorIOSQLite;
import io.reactivex.Completable;
import io.reactivex.Single;
import java.util.List;
import javax.inject.Inject;

public class RateWorker implements EntityWorker {

  @Inject StorIOSQLite storIOSQLite;

  public RateWorker() {
    App.getAppComponent().inject(this);
  }

  @Override public Single<List<RateEntity>> getAllEntities() {
    return storIOSQLite.get()
        .listOfObjects(RateEntity.class)
        .withQuery(RatesTable.QUERY_ALL)
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
