package com.example.currencies.data.db.worker;

import com.example.currencies.data.db.entity.DbEntity;
import io.reactivex.Completable;
import io.reactivex.Single;
import java.util.List;

public interface EntityWorker<E extends DbEntity> {

  Single<List<E>> getAllEntities();

  Completable putEntity(DbEntity entity);

  Completable deleteEntity(DbEntity entity);
}
