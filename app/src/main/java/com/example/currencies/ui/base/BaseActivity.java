package com.example.currencies.ui.base;

import android.os.Bundle;
import butterknife.Unbinder;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.example.currencies.App;

public abstract class BaseActivity extends MvpAppCompatActivity {

  protected Unbinder butterknifeUnbinder;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    App.getAppComponent().inject(this);
    attachContentViewLayoutId();
  }

  @Override protected void onDestroy() {
    butterknifeUnbinder.unbind();
    super.onDestroy();
  }

  protected abstract void attachContentViewLayoutId();
}
