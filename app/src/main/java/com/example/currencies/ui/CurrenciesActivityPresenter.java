package com.example.currencies.ui;

import android.util.Log;
import com.arellomobile.mvp.InjectViewState;
import com.example.currencies.App;
import com.example.currencies.data.DataRepository;
import com.example.currencies.data.db.entity.CurrencyEntity;
import com.example.currencies.ui.base.BasePresenter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import javax.inject.Inject;

@InjectViewState public class CurrenciesActivityPresenter
    extends BasePresenter<CurrenciesActivityView> {

  @SuppressWarnings("WeakerAccess") public static final String TAG =
      CurrenciesActivityPresenter.class.getSimpleName();

  @SuppressWarnings("WeakerAccess") @Inject protected DataRepository dataRepository;

  @Override protected void onFirstViewAttach() {
    super.onFirstViewAttach();

    addDisposable(subscribeToCurrenciesChanges());
  }

  @Override protected void inject() {
    App.getAppComponent().inject(this);
  }

  void onCurrencyClick(CurrencyEntity entity) {
    getViewState().showCurrencyDetails(entity);
  }

  void onFavoriteCurrencyClick(CurrencyEntity entity) {
    CurrencyEntity newEntity =
        new CurrencyEntity(entity.get_id(), entity.getCurrencyId(), entity.getName(),
            entity.getCode(), entity.isFavorite());
    newEntity.toggleIsFavorite();
    addDisposable(subscribeToToggleCurrencyFavorite(newEntity));
  }

  ///////////////////////////////////////////////////////////////////////////
  // PRIVATE SECTION
  ///////////////////////////////////////////////////////////////////////////

  private Disposable subscribeToCurrenciesChanges() {
    return dataRepository.listenForCurrenciesUpdates()
        .subscribeOn(Schedulers.io())
        .doOnNext(currencies -> Log.i(TAG, "DBG_UI: got currencies update: " + currencies.size()))
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::handleCurrenciesChanges, this::handleCurrenciesChangesException);
  }

  private void handleCurrenciesChanges(List<CurrencyEntity> currencyEntities) {
    if (currencyEntities.size() == 0) {
      addDisposable(fetchCurrencyRates());
      return;
    }
    getViewState().addCurrencies(currencyEntities);
  }

  private void handleCurrenciesChangesException(Throwable t) {
    Log.e(TAG, "DBG_UI: handleCurrenciesChangesException: " + t.toString());
    t.printStackTrace();
  }
  // ...

  // fetchCurrencyRates ...
  private Disposable fetchCurrencyRates() {
    return dataRepository.fetchCurrencyRates()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(() -> Log.d(TAG, "DBG_UI: fetchCurrencyRates success"),
            t -> {
              Log.e(TAG, "DBG_UI: fetchCurrencyRates exception: " + t.toString());
              t.printStackTrace();
            });
  }
  // ... fetchCurrencyRates

  // toggleFavoriteCurrency ...
  private Disposable subscribeToToggleCurrencyFavorite(CurrencyEntity entity) {
    return dataRepository.setCurrencyFavorite(entity)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(() -> Log.d(TAG, "DBG_UI: toggleCurrencyFavorite success"),
            t -> {
              Log.e(TAG, "DBG_UI: toggleCurrencyFavorite exception: " + t.toString());
              t.printStackTrace();
            });
  }
  // ... toggleFavoriteCurrency
}
