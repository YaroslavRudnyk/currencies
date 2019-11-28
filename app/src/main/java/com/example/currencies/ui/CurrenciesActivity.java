package com.example.currencies.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.currencies.R;
import com.example.currencies.data.db.entity.CurrencyEntity;
import com.example.currencies.ui.adapter.CurrenciesAdapter;
import com.example.currencies.ui.base.BaseActivity;
import java.util.List;

public class CurrenciesActivity extends BaseActivity implements CurrenciesActivityView {

  public static final String TAG = CurrenciesActivity.class.getSimpleName();

  @InjectPresenter CurrenciesActivityPresenter presenter;

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.include_currencies) RecyclerView currenciesRecyclerView;

  private CurrenciesAdapter currenciesAdapter = new CurrenciesAdapter();
  private boolean isTwoPane;

  @Override protected void attachContentViewLayoutId() {
    setContentView(R.layout.activity_currencies);
    butterknifeUnbinder = ButterKnife.bind(this);

    setupUI();
  }

  // MVP ...
  @Override public void addCurrencies(List<CurrencyEntity> currencyEntities) {
    Log.d(TAG, "DBG_UI: addCurrencies(" + currencyEntities.size() + ")");
    currenciesAdapter.addItems(currencyEntities);
  }

  @Override public void showCurrencyDetails(CurrencyEntity currency) {
    if (isTwoPane) {
      Bundle arguments = new Bundle();
      arguments.putInt(CurrencyDetailFragment.ARG_CURRENCY_ID, currency.getCurrencyId());
      arguments.putString(CurrencyDetailFragment.ARG_CURRENCY_NAME, currency.getName());

      CurrencyDetailFragment fragment = getFragment(CurrencyDetailFragment.class);
      if (fragment == null) fragment = new CurrencyDetailFragment();
      fragment.setArguments(arguments);
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.container_currency_detail, fragment, CurrencyDetailFragment.TAG)
          .commit();
    }
    else {
      Intent intent = new Intent(this, CurrencyDetailActivity.class);
      intent.putExtra(CurrencyDetailFragment.ARG_CURRENCY_ID, currency.getCurrencyId());
      intent.putExtra(CurrencyDetailFragment.ARG_CURRENCY_NAME, currency.getName());

      startActivity(intent);
    }
  }
  // ... MVP

  ///////////////////////////////////////////////////////////////////////////
  // PRIVATE SECTION
  ///////////////////////////////////////////////////////////////////////////

  // UI ...
  private void setupUI() {
    setupAdapter();
    setupRecyclerView();
    setupToolbar();
    setTwoPane();
  }

  /**
   * The detail container view will be present only in the landscape layouts (res/layout-land).
   * If this view is present, then the activity should be in two-pane mode.
   */
  private void setTwoPane() {
    if (findViewById(R.id.container_currency_detail) != null) {
      isTwoPane = true;
    }
  }

  private void setupToolbar() {
    setSupportActionBar(toolbar);
    toolbar.setTitle(getTitle());
  }

  private void setupAdapter() {
    currenciesAdapter.setItemListener(new CurrenciesAdapter.ItemListener() {
      @Override public void onClick(CurrencyEntity entity) {
        presenter.onCurrencyClick(entity);
      }

      @Override public void onFavoriteClick(CurrencyEntity entity) {
        presenter.onFavoriteCurrencyClick(entity);
      }
    });
  }

  private void setupRecyclerView() {
    currenciesRecyclerView.setAdapter(currenciesAdapter);
    currenciesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    Log.d(TAG, "DBG_UI: setupRecyclerView() ended");
  }
  // ... UI
}
