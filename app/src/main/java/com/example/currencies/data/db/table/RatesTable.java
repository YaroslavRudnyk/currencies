package com.example.currencies.data.db.table;

import android.support.annotation.NonNull;
import com.pushtorefresh.storio3.sqlite.queries.Query;

public class RatesTable {

  @NonNull public static final String TABLE = "Rates";

  @NonNull public static final String COLUMN_ID = "_id";
  @NonNull public static final String COLUMN_CURRENCY_ID = "currency_id";
  @NonNull public static final String COLUMN_EXCHANGE_DATE = "exchange_date";
  @NonNull public static final String COLUMN_EXCHANGE_RATE = "exchange_rate";

  @NonNull public static final Query QUERY_ALL = Query.builder().table(TABLE).build();

  public RatesTable() {
    throw new IllegalStateException("No instances allowed");
  }

  @NonNull public static String getCreateTableQuery() {
    return "CREATE TABLE IF NOT EXISTS " + TABLE
        + " ("
        + COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY, "
        + COLUMN_CURRENCY_ID + " INTEGER NOT NULL, "
        + COLUMN_EXCHANGE_DATE + " INTEGER NULL, "
        + COLUMN_EXCHANGE_RATE + " REAL NULL, "
        + "FOREIGN KEY (" + COLUMN_CURRENCY_ID + ")"
        + " REFERENCES " + CurrenciesTable.TABLE + "(" + CurrenciesTable.COLUMN_CURRENCY_ID + ")"
        + ");"
        ;
  }
}
