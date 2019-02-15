package com.example.currencies.ui.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.arellomobile.mvp.MvpAppCompatFragment;

@SuppressLint("ValidFragment") public class BaseFragment extends MvpAppCompatFragment {

  Unbinder butterknifeUnbinder;

  private final int layoutId;

  @SuppressLint("ValidFragment") public BaseFragment(int layoutId) {
    this.layoutId = layoutId;
  }

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    final View fragmentView = inflater.inflate(layoutId, container, false);
    butterknifeUnbinder = ButterKnife.bind(this, fragmentView);
    return fragmentView;
  }
}
