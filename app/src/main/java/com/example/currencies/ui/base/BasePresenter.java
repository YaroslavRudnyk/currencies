package com.example.currencies.ui.base;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BasePresenter<V extends MvpView> extends MvpPresenter<V> {

  private CompositeDisposable compositeDisposable = new CompositeDisposable();

  public BasePresenter() {
    inject();
  }

  protected void addDisposable(Disposable d) {
    compositeDisposable.add(d);
  }

  protected void disposeAndRemoveDisposable(Disposable d) {
    if (d == null) return;
    if (!d.isDisposed()) d.dispose();
    compositeDisposable.delete(d);
  }

  @Override public void onDestroy() {
    compositeDisposable.dispose();
    super.onDestroy();
  }

  protected abstract void inject();
}
