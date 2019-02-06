package com.example.currencies;

import android.app.Application;
import com.example.currencies.di.component.AppComponent;
import com.example.currencies.di.component.ComponentFactory;

public class App extends Application {

  private static AppComponent appComponent;

  @Override public void onCreate() {
    super.onCreate();
    initComponents();
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
}
