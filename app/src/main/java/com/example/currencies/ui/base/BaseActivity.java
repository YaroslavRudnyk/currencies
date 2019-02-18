package com.example.currencies.ui.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

  protected <T extends Fragment> T getFragment(Class<T> type) {
    //noinspection unchecked
    return (T) getSupportFragmentManager().findFragmentByTag(type.getSimpleName());
  }

  protected abstract void attachContentViewLayoutId();
}
