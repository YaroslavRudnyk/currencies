package com.example.currencies.di.module;

import android.os.Build;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import com.example.currencies.di.scope.AppScope;
import com.example.currencies.job.FetchCurrenciesWorker;
import dagger.Module;
import dagger.Provides;
import java.util.concurrent.TimeUnit;

@Module public class JobModule {

  @Provides @AppScope WorkManager provideWorkManager() {
    return WorkManager.getInstance();
  }

  @Provides @AppScope PeriodicWorkRequest provideFetchCurrenciesWorkRequest(
      Constraints constraints) {
    return new PeriodicWorkRequest.Builder(FetchCurrenciesWorker.class, 1, TimeUnit.HOURS)
        .addTag(FetchCurrenciesWorker.TAG)
        .setConstraints(constraints)
        .build();
  }

  @Provides @AppScope Constraints provideConstraints() {
    Constraints.Builder builder = new Constraints.Builder();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      builder.setRequiresDeviceIdle(false);
    }
    builder.setRequiresCharging(false)
        .setRequiredNetworkType(NetworkType.CONNECTED);
    return builder.build();
  }
}
