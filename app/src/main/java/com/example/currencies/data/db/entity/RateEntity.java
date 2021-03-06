package com.example.currencies.data.db.entity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.example.currencies.data.db.table.RatesTable;
import com.pushtorefresh.storio3.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio3.sqlite.annotations.StorIOSQLiteType;

@SuppressWarnings("unused") @StorIOSQLiteType(table = RatesTable.TABLE) public class RateEntity
    implements DbEntity {

  public static final RateEntity EMPTY = new RateEntity(-1, -1L, -1f);

  @StorIOSQLiteColumn(name = RatesTable.COLUMN_ID, key = true) Long _id;
  @StorIOSQLiteColumn(name = RatesTable.COLUMN_CURRENCY_ID) Integer currencyId;
  @StorIOSQLiteColumn(name = RatesTable.COLUMN_EXCHANGE_DATE) Long exchangeDate;
  @StorIOSQLiteColumn(name = RatesTable.COLUMN_EXCHANGE_RATE) Float exchangeRate;

  public RateEntity() {
  }

  public RateEntity(Integer currencyId, Long exchangeDate, Float exchangeRate) {
    this(null, currencyId, exchangeDate, exchangeRate);
  }

  public RateEntity(Long _id, Integer currencyId, Long exchangeDate, Float exchangeRate) {
    this._id = (_id != null && _id != 0) ? _id : null;
    this.currencyId = currencyId;
    this.exchangeDate = exchangeDate;
    this.exchangeRate = exchangeRate;
  }

  @Override public boolean equals(@Nullable Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;

    RateEntity that = (RateEntity) obj;

    if (_id != null ? !_id.equals(that._id) : that._id != null) return false;
    if (currencyId != null ? !currencyId.equals(that.currencyId) : that.currencyId != null)
      return false;
    if (exchangeDate != null ? !exchangeDate.equals(that.exchangeDate) : that.exchangeDate != null)
      return false;
    return exchangeRate != null ? exchangeRate.equals(that.exchangeRate)
        : that.exchangeRate == null;
  }

  @Override public int hashCode() {
    int result = _id != null ? _id.hashCode() : 0;
    result = 31 * result + (currencyId != null ? currencyId.hashCode() : 0);
    result = 31 * result + (exchangeDate != null ? exchangeDate.hashCode() : 0);
    result = 31 * result + (exchangeRate != null ? exchangeRate.hashCode() : 0);

    return result;
  }

  @NonNull @Override public String toString() {
    return "RateEntity: {"
        + "_id= \'" + _id + "\'; "
        + "currencyId= \'" + currencyId + "\'; "
        + "exchangeDate= \'" + exchangeDate + "\'; "
        + "exchangeRate= \'" + exchangeRate + "\'}";
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

  public Long getExchangeDate() {
    return exchangeDate;
  }

  public void setExchangeDate(Long exchangeDate) {
    this.exchangeDate = exchangeDate;
  }

  public Float getExchangeRate() {
    return exchangeRate;
  }

  public void setExchangeRate(Float exchangeRate) {
    this.exchangeRate = exchangeRate;
  }

  @Override public boolean differs(DbEntity entity) {
    if (!(entity instanceof RateEntity)) return true;
    RateEntity that = (RateEntity) entity;
    if (!this.equals(that)) return true;

    if (_id != null ? !_id.equals(that._id) : that._id != null) return true;
    if (currencyId != null ? !currencyId.equals(that.currencyId) : that.currencyId != null)
      return true;
    if (exchangeDate != null ? !exchangeDate.equals(that.exchangeDate) : that.exchangeDate != null)
      return true;
    return exchangeRate != null ? !exchangeRate.equals(that.exchangeRate)
        : that.exchangeRate != null;
  }
}
