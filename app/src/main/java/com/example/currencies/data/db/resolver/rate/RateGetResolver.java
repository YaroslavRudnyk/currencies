package com.example.currencies.data.db.resolver.rate;

import android.database.Cursor;
import android.support.annotation.NonNull;
import com.example.currencies.data.db.entity.RateEntity;
import com.example.currencies.data.db.table.RatesTable;
import com.pushtorefresh.storio3.sqlite.StorIOSQLite;
import com.pushtorefresh.storio3.sqlite.operations.get.DefaultGetResolver;

public class RateGetResolver extends DefaultGetResolver<RateEntity> {
  @NonNull @Override
  public RateEntity mapFromCursor(@NonNull StorIOSQLite storIOSQLite, @NonNull Cursor cursor) {
    RateEntity object = new RateEntity();

    if (!cursor.isNull(cursor.getColumnIndex(RatesTable.COLUMN_ID))) {
      object.set_id(cursor.getLong(cursor.getColumnIndex(RatesTable.COLUMN_ID)));
    }
    if (!cursor.isNull(cursor.getColumnIndex(RatesTable.COLUMN_CURRENCY_ID))) {
      object.setCurrencyId(cursor.getInt(cursor.getColumnIndex(RatesTable.COLUMN_CURRENCY_ID)));
    }
    if (!cursor.isNull(cursor.getColumnIndex(RatesTable.COLUMN_EXCHANGE_DATE))) {
      object.setExchangeDate(
          cursor.getLong(cursor.getColumnIndex(RatesTable.COLUMN_EXCHANGE_DATE)));
    }
    if (!cursor.isNull(cursor.getColumnIndex(RatesTable.COLUMN_EXCHANGE_RATE))) {
      object.setExchangeRate(
          cursor.getFloat(cursor.getColumnIndex(RatesTable.COLUMN_EXCHANGE_RATE)));
    }

    return object;
  }
}
