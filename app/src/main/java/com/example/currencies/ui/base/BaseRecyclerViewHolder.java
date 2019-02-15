package com.example.currencies.ui.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {

  private final Unbinder butterknifeUnbinder;

  protected OnCustomEventListener onCustomEventListener;

  public BaseRecyclerViewHolder(View itemView) {
    super(itemView);
    butterknifeUnbinder = ButterKnife.bind(this, itemView);
  }

  public void setOnCustomEventListener(
      OnCustomEventListener onCustomEventListener) {
    this.onCustomEventListener = onCustomEventListener;
  }

  public void unbindView() {
    onCustomEventListener = null;
    if (butterknifeUnbinder == null) return;
    butterknifeUnbinder.unbind();
  }

  public interface OnCustomEventListener {
    public static final int EVENT_TYPE_UNKNOWN = -100;

    void onCustomEvent(View itemView, int eventType, int subviewId);
  }
}
