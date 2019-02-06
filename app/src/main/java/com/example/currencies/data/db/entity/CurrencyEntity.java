package com.example.currencies.data.db.entity;

import android.support.annotation.Nullable;
import com.example.currencies.data.db.tables.CurrenciesTable;
import com.pushtorefresh.storio3.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio3.sqlite.annotations.StorIOSQLiteType;

@StorIOSQLiteType(table = CurrenciesTable.TABLE) public class CurrencyEntity implements DbEntity {

  @StorIOSQLiteColumn(name = CurrenciesTable.COLUMN_ID, key = true) Long _id;
  @StorIOSQLiteColumn(name = CurrenciesTable.COLUMN_CURRENCY_ID) Integer currencyId;
  @StorIOSQLiteColumn(name = CurrenciesTable.COLUMN_NAME) String name;
  @StorIOSQLiteColumn(name = CurrenciesTable.COLUMN_CODE) String code;

  public CurrencyEntity() {
  }

  public CurrencyEntity(Integer currencyId, String name, String code) {
    this(null, currencyId, name, code);
  }

  public CurrencyEntity(Long _id, Integer currencyId, String name, String code) {
    this._id = _id != 0 ? _id : null;
    this.currencyId = currencyId;
    this.name = name;
    this.code = code;
  }

  @Override public boolean equals(@Nullable Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;

    CurrencyEntity that = (CurrencyEntity) obj;

    if (_id != null ? !_id.equals(that._id) : that._id != null) return false;
    if (currencyId != null ? !currencyId.equals(that.currencyId) : that.currencyId != null)
      return false;
    if (name != null ? !name.equals(that.name) : that.name != null) return false;
    return code != null ? code.equals(that.code) : that.code == null;
  }

  @Override public int hashCode() {
    int result = _id != null ? _id.hashCode() : 0;
    result = 31 * result + (currencyId != null ? currencyId.hashCode() : 0);
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (code != null ? code.hashCode() : 0);

    return result;
  }

  public Long get_id() {
    return _id;
  }

  public void set_id(Long _id) {
    this._id = _id;
  }

  public Integer getCurrencyId() {
    return currencyId;
  }

  public void setCurrencyId(Integer currencyId) {
    this.currencyId = currencyId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }
}
