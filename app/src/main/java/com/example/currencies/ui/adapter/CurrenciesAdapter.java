package com.example.currencies.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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

  private SortedList<CurrencyEntity>
      items = new SortedList<CurrencyEntity>(CurrencyEntity.class,
      new SortedList.Callback<CurrencyEntity>() {
        @Override public int compare(CurrencyEntity o1, CurrencyEntity o2) {
          if (o1.isFavorite() > o2.isFavorite()) return -1;
          if (o1.isFavorite() < o2.isFavorite()) return 1;
          if (o1.getCurrencyId() > o2.getCurrencyId()) return -1;
          if (o1.getCurrencyId() < o2.getCurrencyId()) return 1;
          return 0;
        }

        @Override public void onChanged(int position, int count) {
          notifyItemChanged(position);
        }

        @Override
        public boolean areContentsTheSame(CurrencyEntity oldItem, CurrencyEntity newItem) {
          return !oldItem.differs(newItem);
        }

        @Override public boolean areItemsTheSame(CurrencyEntity item1, CurrencyEntity item2) {
          return item1.equals(item2);
        }

        @Override public void onInserted(int position, int count) {
          notifyItemInserted(position);
        }

        @Override public void onRemoved(int position, int count) {
          notifyItemRemoved(position);
        }

        @Override public void onMoved(int fromPosition, int toPosition) {
          notifyItemMoved(fromPosition, toPosition);
        }
      });

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

    holder.currencyFavorite.setOnClickListener(v -> {
          if (itemListener == null)
            return;
          itemListener.onFavoriteClick(entity);
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
    items.beginBatchedUpdates();
    items.remove(entity);
    items.endBatchedUpdates();
  }

  public void clearItems() {
    items.beginBatchedUpdates();
    items.clear();
    items.endBatchedUpdates();
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

    items.beginBatchedUpdates();
    items.add(entity);
    items.endBatchedUpdates();
  }

  private void innerAddItems(List<CurrencyEntity> entities) {

    SparseArrayCompat<CurrencyEntity> toUpdate = new SparseArrayCompat<>();
    List<CurrencyEntity> toAdd = new ArrayList<>();
    int indexOf;

    items.beginBatchedUpdates();
    for (CurrencyEntity entity : entities) {
      indexOf = indexOfItem(entity);
      if (indexOf < 0) toAdd.add(entity);
      else if (items.get(indexOf).differs(entity)) toUpdate.put(indexOf, entity);
    }

    if (toAdd.size() > 0) {
      items.addAll(toAdd);
    }

    if (toUpdate.size() > 0) {
      for (int i = 0; i < toUpdate.size(); i++) {
        items.updateItemAt(toUpdate.keyAt(i), toUpdate.valueAt(i));
        items.recalculatePositionOfItemAt(toUpdate.keyAt(i));
      }
    }
    items.endBatchedUpdates();
  }

  private void innerUpdateItem(int index, CurrencyEntity entity) {
    items.updateItemAt(index, entity);
    items.recalculatePositionOfItemAt(index);
  }

  private void updateViewHolder(CurrencyViewHolder holder, CurrencyEntity entity) {
    holder.currencyNameText.setText(entity.getName());
    holder.currencyCodeText.setText(entity.getCode());
    holder.currencyFavorite.setSelected(entity.isFavorite() == 1);
  }

  private int indexOfItem(CurrencyEntity entity) {
    for (int i = 0; i < items.size(); i++) {
      if (items.get(i).equals(entity))
        return i;
    }
    return -1;
  }

  ///////////////////////////////////////////////////////////////////////////
  // INNER CLASSES
  ///////////////////////////////////////////////////////////////////////////
  static class CurrencyViewHolder extends BaseRecyclerViewHolder {

    @BindView(R.id.text_currency_name) TextView currencyNameText;
    @BindView(R.id.text_currency_code) TextView currencyCodeText;
    @BindView(R.id.btn_favorite) ImageButton currencyFavorite;

    CurrencyViewHolder(View itemView) {
      super(itemView);
    }
  }

  public interface ItemListener {
    void onClick(CurrencyEntity entity);

    void onFavoriteClick(CurrencyEntity entity);
  }
}
