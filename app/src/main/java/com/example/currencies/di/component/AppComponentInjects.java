package com.example.currencies.di.component;

import com.example.currencies.App;
import com.example.currencies.data.db.StorIoDbManager;

public interface AppComponentInjects {

  // Applications
  void inject(App application);

  // Activities

  // Fragments

  // Presenters

  // Adapters

  // Mappers

  // Data managers
  void inject(StorIoDbManager dbManager);
}
