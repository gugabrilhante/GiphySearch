package br.brilhante.gustavo.giphysearch.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import br.brilhante.gustavo.giphysearch.R;
import br.brilhante.gustavo.giphysearch.activity.base.MenuNavigationActivity;
import br.brilhante.gustavo.giphysearch.component.GustavoActionBar;
import br.brilhante.gustavo.giphysearch.fragment.SearchFragment;
import br.brilhante.gustavo.giphysearch.fragment.TrendingFragment;
import br.brilhante.gustavo.giphysearch.model.data;
import butterknife.ButterKnife;

public class MainActivity extends MenuNavigationActivity implements TrendingFragment.TrendingFragmentListener, SearchFragment.SearchFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onTrendingGiffClicked(data data) {
        super.setSelectedUrl(data.getImages().getDownsizedMedium().getUrl());
        super.callNavication(GustavoActionBar.LEFT_SELECTED, GustavoActionBar.MIDDLE_SELECTED);

    }

    @Override
    public void onSearchGiffClicked(data data) {
        super.setSelectedUrl(data.getImages().getDownsizedMedium().getUrl());
        super.callNavication(GustavoActionBar.RIGHT_SELECTED, GustavoActionBar.MIDDLE_SELECTED);
    }

    public static void hideSoftKeyboard(Activity activity) {
        if (activity != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (activity.getCurrentFocus() != null && inputManager != null) {
                inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
                inputManager.hideSoftInputFromInputMethod(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }
}
