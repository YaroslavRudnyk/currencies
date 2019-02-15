package com.example.currencies.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import com.example.currencies.App;
import com.example.currencies.R;
import com.example.currencies.data.db.entity.CurrencyEntity;
import com.example.currencies.ui.base.BaseRecyclerViewHolder;
import java.util.ArrayList;
import java.util.List;

public class CurrenciesAdapter extends RecyclerView.Adapter<CurrenciesAdapter.CurrencyViewHolder> {

  @SuppressWarnings("WeakerAccess") public static final String TAG =
      CurrenciesAdapter.class.getSimpleName();

  private List<CurrencyEntity> items = new ArrayList<>();
  private ItemListener itemListener;

  public CurrenciesAdapter() {
    super();
    App.getAppComponent().inject(this);
  }

  @NonNull @Override
  public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_currency, parent, false);

    return new CurrencyViewHolder(view);
  }

  @Override public void onBindViewHolder(@NonNull CurrencyViewHolder holder, int position) {
    holder.itemView.setOnClickListener(null);
    final CurrencyEntity entity = items.get(position);
    if (entity == null) {
      Log.w(TAG, "Failed to get entity at position " + position);
      return;
    }

    holder.itemView.setOnClickListener(v -> {
          if (itemListener == null) return;
          itemListener.onClick(entity);
        }
    );
    updateViewHolder(holder, entity);
  }

  @Override public int getItemCount() {
    return items.size();
  }

  public void setItemListener(ItemListener itemListener) {
    this.itemListener = itemListener;
  }

  public void addItem(CurrencyEntity entity) {
    innerAddItem(entity);
  }

  public void addItems(List<CurrencyEntity> entities) {
    innerAddItems(entities);
  }

  public void removeItem(CurrencyEntity entity) {
    int indexOf = items.indexOf(entity);
    if (indexOf < 0) return;
    items.remove(entity);
    notifyItemRemoved(indexOf);
  }

  public void clearItems() {
    items.clear();
    notifyDataSetChanged();
  }

  ///////////////////////////////////////////////////////////////////////////
  // PRIVATE SECTION
  ///////////////////////////////////////////////////////////////////////////

  private void innerAddItem(CurrencyEntity entity) {
    int indexOf = items.indexOf(entity);
    if (indexOf >= 0) {
      innerUpdateItem(indexOf, entity);
      return;
    }

    items.add(entity);
    notifyItemInserted(items.size() - 1);
  }

  private void innerAddItems(List<CurrencyEntity> entities) {

    SparseArrayCompat<CurrencyEntity> toUpdate = new SparseArrayCompat<>();
    List<CurrencyEntity> toAdd = new ArrayList<>();
    int indexOf;

    for (CurrencyEntity entity : entities) {
      indexOf = items.indexOf(entity);
      if (indexOf < 0) toAdd.add(entity);
      else if (items.get(indexOf).differs(entity)) toUpdate.put(indexOf, entity);
    }

    if (toAdd.size() > 0) {
      items.addAll(toAdd);
      notifyItemRangeInserted(items.size() - toAdd.size() - 1, items.size() - 1);
    }

    if (toUpdate.size() > 0) {
      for (int i = 0; i < toUpdate.size(); i++) {
        //innerUpdateItem(toUpdate.keyAt(i), toUpdate.valueAt(i));
        items.set(toUpdate.keyAt(i), toUpdate.valueAt(i));
      }
      notifyDataSetChanged();
    }
  }

  private void innerUpdateItem(int index, CurrencyEntity entity) {
    items.set(index, entity);
    notifyItemChanged(index);
  }

  private void updateViewHolder(CurrencyViewHolder holder, CurrencyEntity entity) {
    holder.currencyNameText.setText(entity.getName());
    holder.currencyCodeText.setText(entity.getCode());
  }

  ///////////////////////////////////////////////////////////////////////////
  // INNER CLASSES
  ///////////////////////////////////////////////////////////////////////////
  static class CurrencyViewHolder extends BaseRecyclerViewHolder {

    @BindView(R.id.text_currency_name) TextView currencyNameText;
    @BindView(R.id.text_currency_code) TextView currencyCodeText;

    CurrencyViewHolder(View itemView) {
      super(itemView);
    }
  }

  public interface ItemListener {
    void onClick(CurrencyEntity entity);
  }
}
