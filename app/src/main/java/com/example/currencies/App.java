package com.example.currencies;

import android.app.Application;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import com.example.currencies.di.component.AppComponent;
import com.example.currencies.di.component.ComponentFactory;
import com.example.currencies.job.FetchCurrenciesWorker;
import javax.inject.Inject;

public class App extends Application {

  private static AppComponent appComponent;

  @Inject WorkManager workManager;
  @Inject PeriodicWorkRequest workRequest;

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
    workManager.cancelAllWorkByTag(FetchCurrenciesWorker.TAG);
    workManager.enqueue(workRequest);
  }
}
