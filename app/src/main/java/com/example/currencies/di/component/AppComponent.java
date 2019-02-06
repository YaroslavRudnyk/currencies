package com.example.currencies.di.component;

import com.example.currencies.App;
import com.example.currencies.di.module.AppModule;
import com.example.currencies.di.module.DataModule;
import com.example.currencies.di.scope.AppScope;
import dagger.Component;

@AppScope
@Component(modules = { AppModule.class, DataModule.class })
public interface AppComponent extends AppComponentInjects, AppComponentExposes {

  final class Initializer {

    private Initializer() {
    }

    static public AppComponent init(final App app) {
      return DaggerAppComponent.builder().
          appModule(new AppModule(app))
          .build();
    }
  }
}
