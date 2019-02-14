package com.example.currencies.data.http.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused") public class CurrencyRatePojo {

  @SerializedName("r030")
  @Expose
  private int id;

  @SerializedName("txt")
  @Expose
  private String name;

  @SerializedName("rate")
  @Expose
  private float rate;

  @SerializedName("cc")
  @Expose
  private String code;

  @SerializedName("exchangedate")
  @Expose
  private String date;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public float getRate() {
    return rate;
  }

  public void setRate(float rate) {
    this.rate = rate;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }
}
