package com.example.currencies.job;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import androidx.work.RxWorker;
import androidx.work.WorkerParameters;
import com.example.currencies.App;
import com.example.currencies.data.DataRepository;
import io.reactivex.Single;
import javax.inject.Inject;

public class FetchCurrenciesWorker extends RxWorker {

  public static final String TAG = FetchCurrenciesWorker.class.getSimpleName();

  @SuppressWarnings("WeakerAccess") @Inject DataRepository repository;

  public FetchCurrenciesWorker(@NonNull Context context,
      @NonNull WorkerParameters workerParams) {
    super(context, workerParams);
    App.getAppComponent().inject(this);
  }

  @Override public Single<Result> createWork() {
    return repository.fetchCurrencyRates()
        .doOnError(this::logException)
        .doOnComplete(() -> Log.i(TAG, "DBG_JOB: handle FetchCurrenciesWorker success"))
        .toSingleDefault(Result.success())
        .onErrorReturnItem(Result.failure());
  }

  private void logException(Throwable t) {
    Log.e(TAG, "DBG_JOB: handle FetchCurrenciesWorker exception: " + t.toString());
    t.printStackTrace();
  }
}
