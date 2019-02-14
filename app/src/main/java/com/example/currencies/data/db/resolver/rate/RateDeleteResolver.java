package com.example.currencies.data.db.resolver.rate;

import android.support.annotation.NonNull;
import com.example.currencies.data.db.entity.RateEntity;
import com.example.currencies.data.db.table.RatesTable;
import com.pushtorefresh.storio3.sqlite.operations.delete.DefaultDeleteResolver;
import com.pushtorefresh.storio3.sqlite.queries.DeleteQuery;

public class RateDeleteResolver extends DefaultDeleteResolver<RateEntity> {
  @NonNull @Override protected DeleteQuery mapToDeleteQuery(@NonNull RateEntity object) {
    return DeleteQuery.builder()
        .table(RatesTable.TABLE)
        .where(object.get_id() != null ? RatesTable.COLUMN_ID + " = ?"
            : RatesTable.COLUMN_CURRENCY_ID
                + " = ? AND "
                + RatesTable.COLUMN_EXCHANGE_DATE
                + " = ?")
        .whereArgs(object.get_id() != null ? object.get_id() : object.getCurrencyId(),
            object.getExchangeDate())
        .build();
  }
}
