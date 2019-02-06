package com.example.currencies.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.currencies.R;
import com.example.currencies.dummy.DummyContent;
import java.util.List;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link CurrencyDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class CurrenciesActivity extends AppCompatActivity {

  /**
   * Whether or not the activity is in two-pane mode, i.e. running on a tablet
   * device.
   */
  private boolean isTwoPane;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_currencies);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    toolbar.setTitle(getTitle());

    if (findViewById(R.id.container_currency_detail) != null) {
      // The detail container view will be present only in the
      // large-screen layouts (res/values-w900dp).
      // If this view is present, then the
      // activity should be in two-pane mode.
      isTwoPane = true;
    }

    View recyclerView = findViewById(R.id.include_currencies);
    assert recyclerView != null;
    setupRecyclerView((RecyclerView) recyclerView);
  }

  private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
    recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, DummyContent.ITEMS, isTwoPane));
  }

  public static class SimpleItemRecyclerViewAdapter
      extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

    private final CurrenciesActivity mParentActivity;
    private final List<DummyContent.DummyItem> mValues;
    private final boolean isTwoPane;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        DummyContent.DummyItem item = (DummyContent.DummyItem) view.getTag();
        if (isTwoPane) {
          Bundle arguments = new Bundle();
          arguments.putString(CurrencyDetailFragment.ARG_ITEM_ID, item.id);
          CurrencyDetailFragment fragment = new CurrencyDetailFragment();
          fragment.setArguments(arguments);
          mParentActivity.getSupportFragmentManager().beginTransaction()
              .replace(R.id.container_currency_detail, fragment)
              .commit();
        }
        else {
          Context context = view.getContext();
          Intent intent = new Intent(context, CurrencyDetailActivity.class);
          intent.putExtra(CurrencyDetailFragment.ARG_ITEM_ID, item.id);

          context.startActivity(intent);
        }
      }
    };

    SimpleItemRecyclerViewAdapter(CurrenciesActivity parent,
        List<DummyContent.DummyItem> items,
        boolean twoPane) {
      mValues = items;
      mParentActivity = parent;
      isTwoPane = twoPane;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext())
          .inflate(R.layout.item_currency, parent, false);
      return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
      holder.currencyNameText.setText(mValues.get(position).content);
      holder.currencyCodeText.setText(mValues.get(position).id);

      holder.itemView.setTag(mValues.get(position));
      holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
      return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
      final TextView currencyNameText;
      final TextView currencyCodeText;

      ViewHolder(View view) {
        super(view);
        currencyNameText = (TextView) view.findViewById(R.id.text_currency_name);
        currencyCodeText = (TextView) view.findViewById(R.id.text_currency_code);
      }
    }
  }
}
