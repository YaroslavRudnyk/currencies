package com.example.currencies.data.db.resolver.currency;

import android.support.annotation.NonNull;
import com.example.currencies.data.db.entity.CurrencyEntity;
import com.example.currencies.data.db.table.CurrenciesTable;
import com.pushtorefresh.storio3.sqlite.operations.delete.DefaultDeleteResolver;
import com.pushtorefresh.storio3.sqlite.queries.DeleteQuery;

public class CurrencyDeleteResolver extends DefaultDeleteResolver<CurrencyEntity> {
  @NonNull @Override protected DeleteQuery mapToDeleteQuery(@NonNull CurrencyEntity object) {
    return DeleteQuery.builder()
        .table(CurrenciesTable.TABLE)
        .where((object.get_id() != null ? CurrenciesTable.COLUMN_ID
            : CurrenciesTable.COLUMN_CURRENCY_ID) + " = ?")
        .whereArgs(object.get_id() != null ? object.get_id() : object.getCurrencyId())
        .build();
  }
}
