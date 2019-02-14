package com.example.currencies.data.db.resolver.rate;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;
import com.example.currencies.data.db.entity.RateEntity;
import com.example.currencies.data.db.table.RatesTable;
import com.pushtorefresh.storio3.sqlite.StorIOSQLite;
import com.pushtorefresh.storio3.sqlite.operations.put.DefaultPutResolver;
import com.pushtorefresh.storio3.sqlite.operations.put.PutResult;
import com.pushtorefresh.storio3.sqlite.queries.InsertQuery;
import com.pushtorefresh.storio3.sqlite.queries.Query;
import com.pushtorefresh.storio3.sqlite.queries.UpdateQuery;

import static com.pushtorefresh.storio3.internal.InternalQueries.nullableArrayOfStringsFromListOfStrings;
import static com.pushtorefresh.storio3.internal.InternalQueries.nullableString;

public class RatePutResolver extends DefaultPutResolver<RateEntity> {
  @NonNull @Override protected InsertQuery mapToInsertQuery(@NonNull RateEntity object) {
    return InsertQuery.builder()
        .table(RatesTable.TABLE)
        .build();
  }

  @NonNull @Override protected UpdateQuery mapToUpdateQuery(@NonNull RateEntity object) {
    if (object.get_id() != null)
      return UpdateQuery.builder()
          .table(RatesTable.TABLE)
          .where(RatesTable.COLUMN_ID + " = ?")
          .whereArgs(object.get_id())
          .build();
    else
      return UpdateQuery.builder()
          .table(RatesTable.TABLE)
          .where(RatesTable.COLUMN_CURRENCY_ID
              + " = ? AND "
              + RatesTable.COLUMN_EXCHANGE_DATE
              + " = ?")
          .whereArgs(object.getCurrencyId(), object.getExchangeDate())
          .build();
  }

  @NonNull @Override protected ContentValues mapToContentValues(@NonNull RateEntity object) {
    ContentValues contentValues = new ContentValues(4);

    contentValues.put(RatesTable.COLUMN_ID, object.get_id());
    contentValues.put(RatesTable.COLUMN_CURRENCY_ID, object.getCurrencyId());
    contentValues.put(RatesTable.COLUMN_EXCHANGE_DATE, object.getExchangeDate());
    contentValues.put(RatesTable.COLUMN_EXCHANGE_RATE, object.getExchangeRate());

    return contentValues;
  }

  @NonNull @Override
  public PutResult performPut(@NonNull StorIOSQLite storIOSQLite, @NonNull RateEntity object) {
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
          long existingId = cursor.getLong(cursor.getColumnIndex(RatesTable.COLUMN_ID));
          contentValues.put(RatesTable.COLUMN_ID, existingId);

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
