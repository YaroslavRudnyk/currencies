package com.example.currencies.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import butterknife.ButterKnife;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.currencies.R;
import com.example.currencies.ui.base.BaseActivity;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on portrait screen. On landscape screen,
 * item details are presented side-by-side with a list of items
 * in a {@link CurrenciesActivity}.
 */
public class CurrencyDetailActivity extends BaseActivity implements CurrencyDetailActivityView {

  @InjectPresenter CurrencyDetailActivityPresenter presenter;

  private int currencyId;
  private String currencyName;

  @Override protected void attachContentViewLayoutId() {
    setContentView(R.layout.activity_currency_detail);
    butterknifeUnbinder = ButterKnife.bind(this);

    readExtras();
    setupToolbar();
    presenter.onReady();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == android.R.id.home) {
      presenter.onHome();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  // MVP ...
  @Override public void navigateToCurrenciesActivity() {
    navigateUpTo(new Intent(this, CurrenciesActivity.class));
  }

  @Override public void showDetailsFragment() {
    Bundle arguments = new Bundle();
    arguments.putInt(CurrencyDetailFragment.ARG_CURRENCY_ID, currencyId);
    arguments.putString(CurrencyDetailFragment.ARG_CURRENCY_NAME, currencyName);

    CurrencyDetailFragment fragment = getFragment(CurrencyDetailFragment.class);
    if (fragment == null) fragment = new CurrencyDetailFragment();
    fragment.setArguments(arguments);

    getSupportFragmentManager().beginTransaction()
        .replace(R.id.container_currency_detail, fragment, CurrencyDetailFragment.TAG)
        .commit();
  }
  // ... MVP

  ///////////////////////////////////////////////////////////////////////////
  // PRIVATE SECTION
  ///////////////////////////////////////////////////////////////////////////

  private void setupToolbar() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
    setSupportActionBar(toolbar);

    // Show the Up button in the action bar.
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }

    if (actionBar == null || TextUtils.isEmpty(currencyName)) return;
    actionBar.setTitle(currencyName);
  }

  private void readExtras() {
    currencyId = getIntent().getIntExtra(CurrencyDetailFragment.ARG_CURRENCY_ID, -1);
    currencyName = getIntent().getStringExtra(CurrencyDetailFragment.ARG_CURRENCY_NAME);
  }
}
