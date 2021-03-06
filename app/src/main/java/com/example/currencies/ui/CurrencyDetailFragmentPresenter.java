package com.example.currencies.ui;

import android.util.Log;
import com.arellomobile.mvp.InjectViewState;
import com.example.currencies.App;
import com.example.currencies.data.DataRepository;
import com.example.currencies.data.db.entity.RateEntity;
import com.example.currencies.ui.base.BasePresenter;
import com.example.currencies.util.DateUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.atomic.AtomicLong;
import javax.inject.Inject;

@InjectViewState public class CurrencyDetailFragmentPresenter extends
    BasePresenter<CurrencyDetailFragmentView> {

  @SuppressWarnings("WeakerAccess") public static final String TAG =
      CurrencyDetailFragmentPresenter.class.getSimpleName();

  @SuppressWarnings("WeakerAccess") @Inject DataRepository repository;

  private int currencyId;
  private AtomicLong rateDate = new AtomicLong(DateUtil.getCurrentDate());

  private Disposable rateChangeDisposable;

  @Override protected void inject() {
    App.getAppComponent().inject(this);
  }

  @Override protected void onFirstViewAttach() {
    super.onFirstViewAttach();

    listenForRateChange();
  }

  void onCurrencyIdChange(int currencyId) {
    this.currencyId = currencyId;
    listenForRateChange();
    getCurrencyRate();
  }

  void onRateDateChange(long rateDate) {
    this.rateDate.set(rateDate);
    listenForRateChange();
    getCurrencyRate();
    getViewState().showHideDatePicker(false);
  }

  void onDateClick() {
    getViewState().showHideDatePicker(true);
  }

  ///////////////////////////////////////////////////////////////////////////
  // PRIVATE SECTION
  ///////////////////////////////////////////////////////////////////////////

  // CurrencyRateChange ...
  private void listenForRateChange() {
    disposeAndRemoveDisposable(rateChangeDisposable);
    rateChangeDisposable = subscribeToCurrencyRateChange();
    addDisposable(rateChangeDisposable);
    //addDisposable(subscribeToCurrencyRateChange());
  }

  private Disposable subscribeToCurrencyRateChange() {
    return repository.listenForCurrencyRateUpdates(currencyId, rateDate.get())
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .filter(rate -> !rate.equals(RateEntity.EMPTY))
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::handleCurrencyRateChange, this::handleCurrencyRateChangeException);
  }

  private void handleCurrencyRateChange(RateEntity rateEntity) {
    if (rateEntity.equals(RateEntity.EMPTY)) return;
    getViewState().updateRate(rateEntity);
  }

  private void handleCurrencyRateChangeException(Throwable t) {
    Log.e(TAG, "DBG_PRESENTER: handleCurrencyRateChangeException " + t.toString());
    t.printStackTrace();
  }
  // ... CurrencyRateChange

  // GetCurrencyRate ...
  private void getCurrencyRate() {
    addDisposable(subscribeToGetCurrencyRate());
  }

  private Disposable subscribeToGetCurrencyRate() {
    return repository.getCurrencyRate(currencyId, rateDate.get())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::handleCurrencyRate, this::handleCurrencyRateException);
  }

  private void handleCurrencyRate(RateEntity rateEntity) {
    if (rateEntity.equals(RateEntity.EMPTY)) {
      fetchCurrencyRates();
      return;
    }

    setRateEntity(rateEntity);
  }

  private void handleCurrencyRateException(Throwable t) {
    Log.e(TAG, "DBG_PRESENTER: handleCurrencyRateException " + t.toString());
    t.printStackTrace();
  }
  // ... GetCurrencyRate

  // FetchCurrencyRate ...
  private void fetchCurrencyRates() {
    addDisposable(subscribeToFetchCurrencyRate());
  }

  private Disposable subscribeToFetchCurrencyRate() {
    return repository.fetchCurrencyRates(rateDate.get())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(() -> Log.i(TAG,
            "DBG_PRESENTER: fetchCurrencyRates(" + currencyId + ", " + rateDate + ") success"),
            t -> {
              Log.i(TAG, "DBG_PRESENTER: fetchCurrencyRates("
                  + currencyId
                  + ", "
                  + rateDate
                  + ") exception "
                  + t.toString());
              t.printStackTrace();
            });
  }
  // ... FetchCurrencyRate

  private void setRateEntity(RateEntity rateEntity) {
    getViewState().updateRate(rateEntity);
  }
}
