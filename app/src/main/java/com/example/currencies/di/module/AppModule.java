package com.example.currencies.di.module;

import android.content.Context;
import android.content.res.Resources;
import com.example.currencies.App;
import com.example.currencies.di.scope.AppScope;
import dagger.Module;
import dagger.Provides;

@Module public class AppModule {
  private final App application;

  public AppModule(App application) {
    this.application = application;
  }

  @Provides @AppScope App provideApp() {
    return application;
  }

  @Provides @AppScope Context provideAppContext() {
    return application;
  }

  @Provides @AppScope Resources provideResources() {
    return application.getResources();
  }

  public interface Exposes {
    App app();

    Context context();

    Resources resources();
  }
}
