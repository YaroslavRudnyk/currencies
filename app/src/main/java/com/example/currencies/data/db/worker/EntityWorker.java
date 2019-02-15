package com.example.currencies.data.db.worker;

import com.example.currencies.data.db.entity.DbEntity;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import java.util.List;

public interface EntityWorker<E extends DbEntity> {

  Single<List<E>> getAllEntities();

  Completable putEntity(DbEntity entity);

  Completable putEntities(List<E> entities);

  Completable deleteEntity(DbEntity entity);

  Flowable<List<E>> listenForUpdates();
}
