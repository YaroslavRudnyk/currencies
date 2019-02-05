package com.example.currencies;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.currencies.dummy.DummyContent;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link CurrenciesActivity}
 * in two-pane mode (on tablets) or a {@link CurrencyDetailActivity}
 * on handsets.
 */
public class CurrencyDetailFragment extends Fragment {
  /**
   * The fragment argument representing the item ID that this fragment
   * represents.
   */
  public static final String ARG_ITEM_ID = "item_id";

  /**
   * The dummy content this fragment is presenting.
   */
  private DummyContent.DummyItem item;

  /**
   * Mandatory empty constructor for the fragment manager to instantiate the
   * fragment (e.g. upon screen orientation changes).
   */
  public CurrencyDetailFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (getArguments().containsKey(ARG_ITEM_ID)) {
      // Load the dummy content specified by the fragment
      // arguments. In a real-world scenario, use a Loader
      // to load content from a content provider.
      item = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

      Activity activity = this.getActivity();
      Toolbar appBarLayout = (Toolbar) activity.findViewById(R.id.toolbar);
      if (appBarLayout != null) {
        appBarLayout.setTitle(item.content);
      }
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_currency_detail, container, false);

    // Show the dummy content as text in a TextView.
    if (item != null) {
      ((TextView) rootView.findViewById(R.id.text_currency_id)).setText(item.id);
      ((TextView) rootView.findViewById(R.id.text_currency_rate)).setText(item.details);
    }

    return rootView;
  }
}
