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
    addDisposable(fetchCurrencyRates());
  }

  @Override protected void inject() {
    App.getAppComponent().inject(this);
  }

  void onCurrencyClick(CurrencyEntity entity, boolean isTwoPane) {
    // TODO

    /*if (isTwoPane) {
      Bundle arguments = new Bundle();
      arguments.putString(CurrencyDetailFragment.ARG_ITEM_ID, item.id);
      CurrencyDetailFragment fragment = new CurrencyDetailFragment();
      fragment.setArguments(arguments);
      mParentActivity.getSupportFragmentManager().beginTransaction()
          .replace(R.id.container_currency_detail, fragment)
          .commit();
    }
    else {
      Context context = view.getContext();
      Intent intent = new Intent(context, CurrencyDetailActivity.class);
      intent.putExtra(CurrencyDetailFragment.ARG_ITEM_ID, item.id);

      context.startActivity(intent);
    }*/
  }

  ///////////////////////////////////////////////////////////////////////////
  // PRIVATE SECTION
  ///////////////////////////////////////////////////////////////////////////

  //
  private Disposable subscribeToCurrenciesChanges() {
    return dataRepository.listenForCurrenciesUpdates()
        .subscribeOn(Schedulers.io())
        .doOnNext(currencies -> Log.i(TAG, "DBG_UI: got currencies update: " + currencies.size()))
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::handleCurrenciesChanges, this::handleCurrenciesChangesException);
  }

  private void handleCurrenciesChanges(List<CurrencyEntity> currencyEntities) {
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
}
