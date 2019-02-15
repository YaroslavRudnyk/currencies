package com.example.currencies.ui.base;

import butterknife.Unbinder;
import com.arellomobile.mvp.MvpAppCompatActivity;

public abstract class BaseActivity extends MvpAppCompatActivity {

  protected Unbinder butterknifeUnbinder;

  @Override protected void onDestroy() {
    butterknifeUnbinder.unbind();
    super.onDestroy();
  }
}
