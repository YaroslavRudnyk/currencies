package com.example.currencies.di.component;

import com.example.currencies.App;

public class ComponentFactory {

  public ComponentFactory() {
  }

  public static AppComponent createAppComponent(final App app) {
    return AppComponent.Initializer.init(app);
  }
}
