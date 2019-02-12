package com.example.currencies.data.db.worker;

import android.util.Log;
import com.example.currencies.App;
import com.example.currencies.data.db.entity.CurrencyEntity;
import com.example.currencies.data.db.entity.DbEntity;
import com.example.currencies.data.db.table.CurrenciesTable;
import com.pushtorefresh.storio3.sqlite.StorIOSQLite;
import com.pushtorefresh.storio3.sqlite.operations.delete.DeleteResult;
import com.pushtorefresh.storio3.sqlite.operations.put.PutResult;
import com.pushtorefresh.storio3.sqlite.operations.put.PutResults;
import io.reactivex.Completable;
import io.reactivex.Single;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

public class CurrencyWorker implements EntityWorker {

  private static final String TAG = CurrencyWorker.class.getSimpleName();

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
        .asRxSingle()
        .doOnEvent((result, t) -> handleEntityPutEvent(entity, result, t))
        .ignoreElement();
  }

  @Override public Completable putEntities(List entities) {
    List<CurrencyEntity> currencyEntities;
    try {
      //noinspection unchecked
      currencyEntities = (List<CurrencyEntity>) entities;
    } catch (Exception e) {
      String exceptionMessage = "Failed to cast entities to List<CurrencyEntity> currencyEntities";
      Log.e(TAG, "DBG_DB: " + exceptionMessage);
      return Completable.error(new Throwable(exceptionMessage));
    }

    return storIOSQLite.put()
        .objects(currencyEntities)
        .useTransaction(true)
        .prepare()
        .asRxSingle()
        .doOnEvent(this::handleEntitiesPutEvent)
        .ignoreElement();
  }

  @Override public Completable deleteEntity(DbEntity entity) {
    return storIOSQLite.delete()
        .object(entity)
        .prepare()
        .asRxSingle()
        .doOnEvent((result, t) -> handleEntityDeleteEvent(entity, result, t))
        .ignoreElement();
  }

  ///////////////////////////////////////////////////////////////////////////
  // PRIVATE SECTION
  ///////////////////////////////////////////////////////////////////////////

  private void handleEntityPutEvent(DbEntity entity, PutResult result, Throwable t) {
    if (t != null) {
      Log.e(TAG, "DBG_DB: putEntity error: " + t.toString());
      return;
    }
    if (!result.wasInserted()) return;
    ((CurrencyEntity) entity).set_id(result.insertedId());
  }

  private void handleEntitiesPutEvent(PutResults<CurrencyEntity> results, Throwable t) {
    if (t != null) {
      Log.e(TAG, "DBG_DB: putEntities error: " + t.toString());
      return;
    }

    Map<CurrencyEntity, PutResult> putResultMap = results.results();
    Map.Entry<CurrencyEntity, PutResult> next;
    CurrencyEntity currencyEntity;
    PutResult putResult;
    Iterator<Map.Entry<CurrencyEntity, PutResult>> iterator = putResultMap.entrySet().iterator();

    //noinspection WhileLoopReplaceableByForEach
    while (iterator.hasNext()) {
      next = iterator.next();
      currencyEntity = next.getKey();
      putResult = next.getValue();
      if (!putResult.wasInserted()) continue;
      currencyEntity.set_id(putResult.insertedId());
    }
  }

  private void handleEntityDeleteEvent(DbEntity entity, DeleteResult result, Throwable t) {
    if (t != null) {
      Log.e(TAG, "DBG_DB: deleteEntity error: " + t.toString());
      return;
    }
    if (result.numberOfRowsDeleted() <= 1) return;
    ((CurrencyEntity) entity).set_id(null);
  }
}
