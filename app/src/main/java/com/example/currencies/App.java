package com.example.currencies;

import android.app.Application;

public class App extends Application {

  @Override public void onCreate() {
    super.onCreate();
    initComponents();
  }

  ///////////////////////////////////////////////////////////////////////////
  // PRIVATE SECTION
  ///////////////////////////////////////////////////////////////////////////

  private void initComponents() {
    initDaggerComponents();
  }

  private void initDaggerComponents() {

  }
}
