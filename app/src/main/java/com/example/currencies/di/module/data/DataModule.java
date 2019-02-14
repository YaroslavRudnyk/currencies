package com.example.currencies.di.module.data;

import com.example.currencies.data.DataRepository;
import com.example.currencies.data.Repository;
import com.example.currencies.di.scope.AppScope;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;

@Module(includes = { DbModule.class, HttpModule.class, MapperModule.class })
public class DataModule {

  private static final String STRING_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

  @Provides @AppScope DataRepository provideDataRepository() {
    return new Repository();
  }

  @Provides @AppScope Gson provideGson() {
    return new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
        .setDateFormat(STRING_DATE_FORMAT)
        .serializeNulls()
        .disableHtmlEscaping()
        .create();
  }
}
