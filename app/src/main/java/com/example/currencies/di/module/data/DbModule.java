package com.example.currencies.di.module.data;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.currencies.data.db.DbHelper;
import com.example.currencies.data.db.DbManager;
import com.example.currencies.data.db.StorIoDbManager;
import com.example.currencies.data.db.entity.CurrencyEntity;
import com.example.currencies.data.db.entity.CurrencyEntitySQLiteTypeMapping;
import com.example.currencies.data.db.entity.RateEntity;
import com.example.currencies.data.db.entity.RateEntitySQLiteTypeMapping;
import com.example.currencies.data.db.worker.CurrencyWorker;
import com.example.currencies.data.db.worker.EntityWorker;
import com.example.currencies.data.db.worker.RateWorker;
import com.example.currencies.di.scope.AppScope;
import com.pushtorefresh.storio3.sqlite.StorIOSQLite;
import com.pushtorefresh.storio3.sqlite.impl.DefaultStorIOSQLite;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;

@Module public class DbModule {

  public static final String NAME_WORKER_CURRENCY = "name_worker_currency";
  public static final String NAME_WORKER_RATE = "name_worker_rate";

  @Provides @AppScope DbManager provideDbManager() {
    return new StorIoDbManager();
  }

  @Provides @AppScope SQLiteOpenHelper provideSqLiteOpenHelper(Context context) {
    return new DbHelper(context);
  }

  @Provides @AppScope StorIOSQLite provideStorIOSQLite(SQLiteOpenHelper sqliteOpenHelper) {
    return DefaultStorIOSQLite.builder().sqliteOpenHelper(sqliteOpenHelper)
        .addTypeMapping(CurrencyEntity.class, new CurrencyEntitySQLiteTypeMapping())
        .addTypeMapping(RateEntity.class, new RateEntitySQLiteTypeMapping())
        .build();
  }

  // Workers ...
  @Provides @AppScope @Named(NAME_WORKER_CURRENCY) EntityWorker provideCurrencyWorker() {
    return new CurrencyWorker();
  }

  @Provides @AppScope @Named(NAME_WORKER_RATE) RateWorker provideRateWorker() {
    return new RateWorker();
  }
  // ... Workers
}
