package com.example.currencies.di.module.data;

import com.example.currencies.data.mapper.CurrencyHttpToDbMapper;
import com.example.currencies.data.mapper.RateHttpToDbMapper;
import com.example.currencies.di.scope.AppScope;
import dagger.Module;
import dagger.Provides;

@Module public class MapperModule {

  @Provides @AppScope CurrencyHttpToDbMapper provideCurrencyHttpToDbMapper() {
    return new CurrencyHttpToDbMapper();
  }

  @Provides @AppScope RateHttpToDbMapper provideRateHttpToDbMapper() {
    return new RateHttpToDbMapper();
  }
}
