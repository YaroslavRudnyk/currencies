package com.example.currencies.di.module.data;

import com.example.currencies.data.DataRepository;
import com.example.currencies.data.Repository;
import com.example.currencies.di.scope.AppScope;
import dagger.Module;
import dagger.Provides;

@Module(includes = { DbModule.class }) public class DataModule {

  @Provides @AppScope DataRepository provideDataRepository() {
    return new Repository();
  }
}
