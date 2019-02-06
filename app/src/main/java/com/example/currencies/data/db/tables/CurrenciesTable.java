package com.example.currencies.data.db.tables;

import android.support.annotation.NonNull;
import com.pushtorefresh.storio3.sqlite.queries.Query;

public class CurrenciesTable {

  @NonNull public static final String TABLE = "Currencies";

  @NonNull public static final String INDEX_CURRENCY_ID = "idx_currency_id";

  @NonNull public static final String COLUMN_ID = "_id";
  @NonNull public static final String COLUMN_CURRENCY_ID = "currency_id";
  @NonNull public static final String COLUMN_NAME = "name";
  @NonNull public static final String COLUMN_CODE = "code";

  @NonNull public static final Query QUERY_ALL = Query.builder().table(TABLE).build();

  public CurrenciesTable() {
    throw new IllegalStateException("No instances allowed");
  }

  @NonNull public static String getCreateTableQuery() {
    return "CREATE TABLE IF NOT EXISTS " + TABLE
        + " ("
        + COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY, "
        + COLUMN_CURRENCY_ID + " INTEGER NOT NULL, "
        + COLUMN_NAME + " TEXT NULL, "
        + COLUMN_CODE + " TEXT NULL "
        + "); "
        + "CREATE UNIQUE INDEX IF NOT EXISTS " + INDEX_CURRENCY_ID
        + " ON " + TABLE + " (" + COLUMN_CURRENCY_ID + ");"
        ;
  }
}
