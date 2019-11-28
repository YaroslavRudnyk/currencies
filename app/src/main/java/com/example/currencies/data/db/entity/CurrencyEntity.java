package com.example.currencies.data.db.entity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.example.currencies.data.db.table.CurrenciesTable;
import com.pushtorefresh.storio3.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio3.sqlite.annotations.StorIOSQLiteType;

@SuppressWarnings("unused") @StorIOSQLiteType(table = CurrenciesTable.TABLE)
public class CurrencyEntity implements DbEntity {

  public static final int EMPTY_IS_FAVORITE = -1;

  @StorIOSQLiteColumn(name = CurrenciesTable.COLUMN_ID, key = true) Long _id;
  @StorIOSQLiteColumn(name = CurrenciesTable.COLUMN_CURRENCY_ID) Integer currencyId;
  @StorIOSQLiteColumn(name = CurrenciesTable.COLUMN_NAME) String name;
  @StorIOSQLiteColumn(name = CurrenciesTable.COLUMN_CODE) String code;
  @StorIOSQLiteColumn(name = CurrenciesTable.COLUMN_IS_FAVORITE) Integer isFavorite =
      EMPTY_IS_FAVORITE;

  public CurrencyEntity() {
  }

  public CurrencyEntity(Integer currencyId, String name, String code) {
    this(null, currencyId, name, code, EMPTY_IS_FAVORITE);
  }

  public CurrencyEntity(Integer currencyId, String name, String code, Integer isFavorite) {
    this(null, currencyId, name, code, isFavorite);
  }

  public CurrencyEntity(Long _id, Integer currencyId, String name, String code,
      Integer isFavorite) {
    this._id = (_id != null && _id != 0) ? _id : null;
    this.currencyId = currencyId;
    this.name = name;
    this.code = code;
    this.isFavorite = isFavorite;
  }

  @Override public boolean equals(@Nullable Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;

    CurrencyEntity that = (CurrencyEntity) obj;

    if (_id != null ? !_id.equals(that._id) : that._id != null) return false;
    return currencyId != null ? currencyId.equals(that.currencyId) : that.currencyId == null;
  }

  @Override public int hashCode() {
    int result = _id != null ? _id.hashCode() : 0;
    result = 31 * result + (currencyId != null ? currencyId.hashCode() : 0);

    return result;
  }

  @NonNull @Override public String toString() {
    return "CurrencyEntity: {"
        + "_id= \'" + _id + "\'; "
        + "currencyId= \'" + currencyId + "\'; "
        + "name= \'" + name + "\'; "
        + "code= \'" + code + "\'; "
        + "isFavorite= \'" + isFavorite + "\'}";
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

  public Integer isFavorite() {
    return isFavorite;
  }

  public void setFavorite(Integer isFavorite) {
    this.isFavorite = isFavorite;
  }

  public void toggleIsFavorite() {
    isFavorite = (isFavorite > 0) ? 0 : 1;
  }

  @Override public boolean differs(DbEntity entity) {
    if (!(entity instanceof CurrencyEntity)) return true;
    CurrencyEntity that = (CurrencyEntity) entity;
    if (!this.equals(that)) return true;

    if (_id != null ? !_id.equals(that._id) : that._id != null) return true;
    if (currencyId != null ? !currencyId.equals(that.currencyId) : that.currencyId != null)
      return true;
    if (name != null ? !name.equals(that.name) : that.name != null) return true;
    if (code != null ? !code.equals(that.code) : that.code != null) return true;
    return isFavorite != null ? !isFavorite.equals(that.isFavorite) : that.isFavorite != null;
  }
}
