package com.example.currencies;

import android.app.Application;
import android.os.Build;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import com.example.currencies.di.component.AppComponent;
import com.example.currencies.di.component.ComponentFactory;
import com.example.currencies.job.TestWorker;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

public class App extends Application {

  private static AppComponent appComponent;

  @Inject WorkManager workManager;
  //@Inject PeriodicWorkRequest workRequest;

  @Override public void onCreate() {
    super.onCreate();
    initComponents();
    scheduleJob();
  }

  public static AppComponent getAppComponent() {
    return appComponent;
  }

  ///////////////////////////////////////////////////////////////////////////
  // PRIVATE SECTION
  ///////////////////////////////////////////////////////////////////////////

  private void initComponents() {
    initDaggerComponents();
  }

  private void initDaggerComponents() {
    appComponent = ComponentFactory.createAppComponent(this);
    appComponent.inject(this);
  }

  private void scheduleJob() {
    List<WorkInfo> workInfos = workManager.getWorkInfosByTagLiveData(TestWorker.TAG)
        .getValue();
    if (workInfos != null && workInfos.size() > 0) return;

    Constraints.Builder builder = new Constraints.Builder();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      builder.setRequiresDeviceIdle(false);
    }
    Constraints constraints = builder.setRequiresCharging(false)
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build();

    PeriodicWorkRequest workRequest =
        new PeriodicWorkRequest.Builder(TestWorker.class, 10, TimeUnit.SECONDS)
            .addTag(TestWorker.TAG)
            .setConstraints(constraints)
            .build();

    workManager.enqueue(workRequest);

    /*List<WorkInfo> workInfos = workManager.getWorkInfosByTagLiveData(FetchCurrenciesWorker.TAG)
        .getValue();
    if (workInfos != null && workInfos.size() > 0) return;

    Constraints.Builder builder = new Constraints.Builder();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      builder.setRequiresDeviceIdle(false);
    }
    Constraints constraints = builder.setRequiresCharging(false)
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build();

    PeriodicWorkRequest workRequest = new PeriodicWorkRequest.Builder(FetchCurrenciesWorker.class, 10, TimeUnit.SECONDS)
        .addTag(FetchCurrenciesWorker.TAG)
        .setConstraints(constraints)
        .build();

    workManager.enqueue(workRequest);*/
  }
}
