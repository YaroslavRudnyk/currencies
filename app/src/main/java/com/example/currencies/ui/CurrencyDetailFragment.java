package com.example.currencies.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.currencies.R;
import com.example.currencies.data.db.entity.RateEntity;
import com.example.currencies.ui.base.BaseFragment;
import com.example.currencies.util.DateUtil;
import java.util.Calendar;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link CurrenciesActivity}
 * in two-pane mode (on tablets) or a {@link CurrencyDetailActivity}
 * on handsets.
 */
public class CurrencyDetailFragment extends BaseFragment implements CurrencyDetailFragmentView {

  public static final String TAG = CurrencyDetailFragment.class.getSimpleName();
  /**
   * The fragment argument representing the Currency ID that this fragment
   * represents.
   */
  public static final String ARG_CURRENCY_ID = "currency_id";
  public static final String ARG_CURRENCY_NAME = "currency_name";

  @InjectPresenter CurrencyDetailFragmentPresenter presenter;

  @BindView(R.id.text_currency_id) TextView currencyIdText;
  @BindView(R.id.text_currency_rate) TextView currencyRateText;
  @BindView(R.id.text_exchange_date) TextView exchangeDateText;

  private int currencyId = -1;
  private String currencyName;

  private DatePickerDialog datePickerDialog;
  private DatePickerDialog.OnDateSetListener onDateSetListener;

  /**
   * Mandatory empty constructor for the fragment manager to instantiate the
   * fragment (e.g. upon screen orientation changes).
   */
  public CurrencyDetailFragment() {
    super(R.layout.fragment_currency_detail);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    readArguments();
    updateToolbar();
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    presenter.onCurrencyIdChange(currencyId);
  }

  // MVP ...
  @Override public void updateRate(RateEntity rateEntity) {
    currencyIdText.setText(String.valueOf(rateEntity.getCurrencyId()));
    currencyRateText.setText(String.valueOf(rateEntity.getExchangeRate()));
    exchangeDateText.setText(
        DateUtil.getDateRepresentation(DateUtil.DATE_FORMAT_PATTERN_FROM_STRING,
            rateEntity.getExchangeDate()));
  }

  @Override public void showHideDatePicker(boolean isShow) {
    if (isShow) showDatePickerDialog();
    else hideDatePickerDialog();
  }

  private void showDatePickerDialog() {
    if (datePickerDialog == null) createDatePickerDialog();
    datePickerDialog.show();
  }

  private void createDatePickerDialog() {
    Calendar calendar = DateUtil.getCalendar(System.currentTimeMillis());
    initOnDateSetListener();
    datePickerDialog =
        new DatePickerDialog(getContext(), onDateSetListener, calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
  }

  private void hideDatePickerDialog() {
    if (datePickerDialog == null) return;
    clearOnDateSetListener();
    datePickerDialog.dismiss();
  }

  private void initOnDateSetListener() {
    onDateSetListener = (view, year, month, dayOfMonth) -> {
      presenter.onRateDateChange(DateUtil.getDateInMilis(dayOfMonth, month, year));
    };
  }

  private void clearOnDateSetListener() {
    onDateSetListener = null;
  }
  // ... MVP

  // Butterknife ...
  @OnClick(R.id.text_exchange_date) void onDateClick() {
    presenter.onDateClick();
  }
  // ... Butterknife

  ///////////////////////////////////////////////////////////////////////////
  // PRIVATE SECTION
  ///////////////////////////////////////////////////////////////////////////

  private void readArguments() {
    if (getArguments() == null) return;

    if (getArguments().containsKey(ARG_CURRENCY_ID)) {
      currencyId = getArguments().getInt(ARG_CURRENCY_ID, -1);
    }
    if (getArguments().containsKey(ARG_CURRENCY_NAME)) {
      currencyName = getArguments().getString(ARG_CURRENCY_NAME);
    }
  }

  private void updateToolbar() {
    Activity activity = this.getActivity();
    Toolbar appBarLayout = (Toolbar) activity.findViewById(R.id.toolbar);
    if (appBarLayout == null || TextUtils.isEmpty(currencyName)) return;
    appBarLayout.setTitle(currencyName);
  }
}
