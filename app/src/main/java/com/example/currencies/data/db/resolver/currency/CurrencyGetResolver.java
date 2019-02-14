package com.example.currencies.data.db.resolver.currency;

import android.database.Cursor;
import android.support.annotation.NonNull;
import com.example.currencies.data.db.entity.CurrencyEntity;
import com.example.currencies.data.db.table.CurrenciesTable;
import com.pushtorefresh.storio3.sqlite.StorIOSQLite;
import com.pushtorefresh.storio3.sqlite.operations.get.DefaultGetResolver;

public class CurrencyGetResolver extends DefaultGetResolver<CurrencyEntity> {
  @NonNull @Override
  public CurrencyEntity mapFromCursor(@NonNull StorIOSQLite storIOSQLite, @NonNull Cursor cursor) {
    CurrencyEntity object = new CurrencyEntity();

    if (!cursor.isNull(cursor.getColumnIndex(CurrenciesTable.COLUMN_ID))) {
      object.set_id(cursor.getLong(cursor.getColumnIndex(CurrenciesTable.COLUMN_ID)));
    }
    if (!cursor.isNull(cursor.getColumnIndex(CurrenciesTable.COLUMN_CURRENCY_ID))) {
      object.setCurrencyId(
          cursor.getInt(cursor.getColumnIndex(CurrenciesTable.COLUMN_CURRENCY_ID)));
    }
    object.setName(cursor.getString(cursor.getColumnIndex(CurrenciesTable.COLUMN_NAME)));
    object.setCode(cursor.getString(cursor.getColumnIndex(CurrenciesTable.COLUMN_CODE)));

    return object;
  }
}
