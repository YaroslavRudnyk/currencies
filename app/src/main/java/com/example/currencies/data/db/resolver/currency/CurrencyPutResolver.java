package com.example.currencies.data.db.resolver.currency;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;
import com.example.currencies.data.db.entity.CurrencyEntity;
import com.example.currencies.data.db.table.CurrenciesTable;
import com.pushtorefresh.storio3.sqlite.StorIOSQLite;
import com.pushtorefresh.storio3.sqlite.operations.put.DefaultPutResolver;
import com.pushtorefresh.storio3.sqlite.operations.put.PutResult;
import com.pushtorefresh.storio3.sqlite.queries.InsertQuery;
import com.pushtorefresh.storio3.sqlite.queries.Query;
import com.pushtorefresh.storio3.sqlite.queries.UpdateQuery;

import static com.pushtorefresh.storio3.internal.InternalQueries.nullableArrayOfStringsFromListOfStrings;
import static com.pushtorefresh.storio3.internal.InternalQueries.nullableString;

public class CurrencyPutResolver extends DefaultPutResolver<CurrencyEntity> {
  @NonNull @Override protected InsertQuery mapToInsertQuery(@NonNull CurrencyEntity object) {
    return InsertQuery.builder()
        .table(CurrenciesTable.TABLE)
        .build();
  }

  @NonNull @Override protected UpdateQuery mapToUpdateQuery(@NonNull CurrencyEntity object) {
    if (object.get_id() != null)
      return UpdateQuery.builder()
          .table(CurrenciesTable.TABLE)
          .where(
              CurrenciesTable.COLUMN_ID + " = ? AND " + CurrenciesTable.COLUMN_CURRENCY_ID + " = ?")
          .whereArgs(object.get_id(), object.getCurrencyId())
          .build();
    else
      return UpdateQuery.builder()
          .table(CurrenciesTable.TABLE)
          .where(CurrenciesTable.COLUMN_CURRENCY_ID + " = ?")
          .whereArgs(object.getCurrencyId())
          .build();
  }

  @NonNull @Override protected ContentValues mapToContentValues(@NonNull CurrencyEntity object) {
    ContentValues contentValues = new ContentValues(4);

    contentValues.put(CurrenciesTable.COLUMN_ID, object.get_id());
    contentValues.put(CurrenciesTable.COLUMN_CURRENCY_ID, object.getCurrencyId());
    contentValues.put(CurrenciesTable.COLUMN_NAME, object.getName());
    contentValues.put(CurrenciesTable.COLUMN_CODE, object.getCode());

    return contentValues;
  }

  @NonNull @Override
  public PutResult performPut(@NonNull StorIOSQLite storIOSQLite, @NonNull CurrencyEntity object) {
    final UpdateQuery updateQuery = mapToUpdateQuery(object);
    final StorIOSQLite.LowLevel lowLevel = storIOSQLite.lowLevel();

    // for data consistency in concurrent environment, encapsulate Put Operation into transaction
    lowLevel.beginTransaction();

    try {
      final Cursor cursor = lowLevel.query(Query.builder()
          .table(updateQuery.table())
          .where(nullableString(updateQuery.where()))
          .whereArgs((Object[]) nullableArrayOfStringsFromListOfStrings(updateQuery.whereArgs()))
          .build());

      final PutResult putResult;

      try {
        final ContentValues contentValues = mapToContentValues(object);

        if (cursor.getCount() == 0) {
          final InsertQuery insertQuery = mapToInsertQuery(object);
          final long insertedId = lowLevel.insert(insertQuery, contentValues);
          putResult =
              PutResult.newInsertResult(insertedId, insertQuery.table(), insertQuery.affectsTags());
        }
        else {

          cursor.moveToFirst();
          long existingId = cursor.getLong(cursor.getColumnIndex(CurrenciesTable.COLUMN_ID));
          contentValues.put(CurrenciesTable.COLUMN_ID, existingId);

          final int numberOfRowsUpdated = lowLevel.update(updateQuery, contentValues);
          putResult = PutResult.newUpdateResult(numberOfRowsUpdated, updateQuery.table(),
              updateQuery.affectsTags());
        }
      } finally {
        cursor.close();
      }

      // everything okay
      lowLevel.setTransactionSuccessful();

      return putResult;
    } finally {
      // in case of bad situations, db won't be affected
      lowLevel.endTransaction();
    }
  }
}
