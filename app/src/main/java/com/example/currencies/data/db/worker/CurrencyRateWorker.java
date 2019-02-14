package com.example.currencies.data.db.worker;

import com.example.currencies.App;
import com.example.currencies.data.db.entity.CurrencyEntity;
import com.example.currencies.data.db.entity.DbEntity;
import com.example.currencies.data.db.entity.RateEntity;
import com.pushtorefresh.storio3.sqlite.StorIOSQLite;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

public class CurrencyRateWorker {

  @Inject StorIOSQLite storIOSQLite;

  public CurrencyRateWorker() {
    App.getAppComponent().inject(this);
  }

  public Completable putEntities(Map<CurrencyEntity, RateEntity> entitiesMap) {
    return Single.just(entitiesMap)
        .map(this::mapEntitiesMapToList)
        .flatMap(this::putTheEntities)
        .ignoreElement();
  }

  private SingleSource<?> putTheEntities(List<DbEntity> putEntities) {
    return storIOSQLite.put()
        .objects(putEntities)
        .useTransaction(true)
        .prepare()
        .asRxSingle();
  }

  ///////////////////////////////////////////////////////////////////////////
  // PRIVATE SECTION
  ///////////////////////////////////////////////////////////////////////////

  private List<DbEntity> mapEntitiesMapToList(Map<CurrencyEntity, RateEntity> entitiesMap) {
    List<DbEntity> putEntities = new ArrayList<>(entitiesMap.size() * 2);
    putEntities.addAll(entitiesMap.keySet());
    putEntities.addAll(entitiesMap.values());
    return putEntities;
  }
}
