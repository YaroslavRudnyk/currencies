package com.example.currencies.data.db.worker;

import android.util.Log;
import com.example.currencies.App;
import com.example.currencies.data.db.entity.DbEntity;
import com.example.currencies.data.db.entity.RateEntity;
import com.example.currencies.data.db.table.RatesTable;
import com.pushtorefresh.storio3.Optional;
import com.pushtorefresh.storio3.sqlite.StorIOSQLite;
import com.pushtorefresh.storio3.sqlite.operations.delete.DeleteResult;
import com.pushtorefresh.storio3.sqlite.operations.put.PutResult;
import com.pushtorefresh.storio3.sqlite.operations.put.PutResults;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

public class RateWorker implements EntityWorker {

  private static final String TAG = RateWorker.class.getSimpleName();

  @SuppressWarnings("WeakerAccess") @Inject StorIOSQLite storIOSQLite;

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
        .asRxSingle()
        .doOnEvent(((result, t) -> handleEntityPutEvent(entity, result, t)))
        .ignoreElement();
  }

  @Override public Completable putEntities(List entities) {
    List<RateEntity> rateEntities;
    try {
      //noinspection unchecked
      rateEntities = (List<RateEntity>) entities;
    } catch (Exception e) {
      String exceptionMessage = "Failed to cast entities to List<RateEntity> rateEntities";
      Log.e(TAG, "DBG_DB: " + exceptionMessage);
      return Completable.error(new Throwable(exceptionMessage));
    }

    return storIOSQLite.put()
        .objects(rateEntities)
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
        .doOnEvent(((result, t) -> handleEntityDeleteEvent(entity, result, t)))
        .ignoreElement();
  }

  @Override public Flowable<List<RateEntity>> listenForUpdates() {
    return storIOSQLite.get()
        .listOfObjects(RateEntity.class)
        .withQuery(RatesTable.QUERY_ALL)
        .prepare()
        .asRxFlowable(BackpressureStrategy.LATEST);
  }

  public Flowable<RateEntity> listenForUpdates(int currencyId, long rateDate) {
    return storIOSQLite.get()
        .object(RateEntity.class)
        .withQuery(RatesTable.getRateOnCurrencyIdOnDateQuery(currencyId, rateDate))
        .prepare()
        .asRxFlowable(BackpressureStrategy.LATEST)
        .map(this::mapOptional);
  }

  public Single<List<RateEntity>> getEntitiesOnDate(Long date) {
    return storIOSQLite.get()
        .listOfObjects(RateEntity.class)
        .withQuery(RatesTable.getRatesOnDateQuery(date))
        .prepare()
        .asRxSingle();
  }

  public Single<List<RateEntity>> getEntitiesOnCurrencyIdOnDate(Integer currencyId, Long date) {
    return storIOSQLite.get()
        .listOfObjects(RateEntity.class)
        .withQuery(RatesTable.getRatesOnCurrencyIdOnDateQuery(currencyId, date))
        .prepare()
        .asRxSingle();
  }

  public Single<RateEntity> getEntityOnCurrencyIdOnDate(int currencyId, long date) {
    return storIOSQLite.get()
        .object(RateEntity.class)
        .withQuery(RatesTable.getRateOnCurrencyIdOnDateQuery(currencyId, date))
        .prepare()
        .asRxSingle()
        .map(this::mapOptional);
  }

  ///////////////////////////////////////////////////////////////////////////
  // PRIVATE SECTION
  ///////////////////////////////////////////////////////////////////////////

  private RateEntity mapOptional(Optional<RateEntity> optional) {
    if (optional == null || optional.orNull() == null) return RateEntity.EMPTY;
    else return optional.get();
  }

  private void handleEntityPutEvent(DbEntity entity, PutResult result, Throwable t) {
    if (t != null) {
      Log.e(TAG, "DBG_DB: putEntity error: " + t.toString());
      return;
    }
    if (!result.wasInserted()) return;
    ((RateEntity) entity).set_id(result.insertedId());
  }

  private void handleEntitiesPutEvent(PutResults<RateEntity> results, Throwable t) {
    if (t != null) {
      Log.e(TAG, "DBG_DB: putEntities error: " + t.toString());
      return;
    }

    Map<RateEntity, PutResult> putResultMap = results.results();
    Map.Entry<RateEntity, PutResult> next;
    RateEntity rateEntity;
    PutResult putResult;
    Iterator<Map.Entry<RateEntity, PutResult>> iterator = putResultMap.entrySet().iterator();

    //noinspection WhileLoopReplaceableByForEach
    while (iterator.hasNext()) {
      next = iterator.next();
      rateEntity = next.getKey();
      putResult = next.getValue();
      if (!putResult.wasInserted()) continue;
      rateEntity.set_id(putResult.insertedId());
    }
  }

  private void handleEntityDeleteEvent(DbEntity entity, DeleteResult result, Throwable t) {
    if (t != null) {
      Log.e(TAG, "DBG_DB: deleteEntity error: " + t.toString());
      return;
    }
    if (result.numberOfRowsDeleted() <= 1) return;
    ((RateEntity) entity).set_id(null);
  }
}
